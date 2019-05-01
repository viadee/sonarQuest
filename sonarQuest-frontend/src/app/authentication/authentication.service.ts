import { WebsocketService } from 'app/services/websocket.service';
import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable, Subscriber} from 'rxjs';
import {shareReplay} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {LocalStorageService} from './local-storage.service';
import {Token} from './Token';

import { Router } from '@angular/router';
import { UserService } from 'app/services/user.service';

@Injectable()
export class AuthenticationService {

  listener: Subscriber<boolean>[] = [];

  constructor(private http: HttpClient,
              private router: Router,
              private storageService: LocalStorageService,
              private webSocketService: WebsocketService,
              private userService: UserService) {
  }

  public login(username, password): void {
    console.log('Trying login for user ' + username);
    new Observable<void>(observer => {
      const body = JSON.stringify({username, password});
      const url = `${environment.endpoint}/login`;
      this.http.post(url, body, {
        headers: new HttpHeaders().set('Content-Type', 'application/json'),
        responseType: 'json',
        observe: 'response'
      }).pipe(shareReplay()).subscribe((response) => {
        if (response.body && response.status === 200) {
          const token = response.body as Token;
          this.storageService.saveJWTToken(token);
          this.onLogin();
          observer.next();
          observer.complete();

        }
      }, err => {
        observer.error(err);
      });
    }).subscribe();
  }

  public logout(): void {
    this.storageService.removeJWTToken();
    this.onLogout();
  }

  public isLoggedIn(): boolean {
    const token = this.storageService.getJWTToken();

    if (!token) {
      return false;
    }

    return token.hasExpired();
  }

  public onLoginLogout(): Observable<boolean> {
    return new Observable<boolean>(
      oberserver => {
        this.listener.push(oberserver);
      }
    );
  }

  private onLogin(): void {
    this.listener.forEach(l => l.next(true));
    this.router.navigate(['/myAvatar'], {skipLocationChange: false});
    this.webSocketService.initializeWebSocketConnection();
    this.userService.loadUser();
  }

  private onLogout(): void {
    this.listener.forEach(l => l.next(false));
    this.webSocketService.closeWebSocket();
  }

}
