import {Injectable, OnInit} from '@angular/core';
import {User} from '../Interfaces/User';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs/Observable';
import {Http, Response, ResponseContentType} from '@angular/http';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {Subject} from 'rxjs/Subject';
import {AuthenticationService} from '../login/authentication.service';
import {Subscriber} from 'rxjs/Subscriber';

@Injectable()
export class UserService {

  private user: User;

  listener: Subscriber<boolean>[] = [];

  constructor(private httpClient: HttpClient, private authenticationService: AuthenticationService) {
    authenticationService.onLoginLogout().subscribe(() => {
      if (authenticationService.isLoggedIn()) {
        this.loadUser();
      }
    });
  }

  public onUserChange(): Observable<boolean> {
    return new Observable<boolean>(
      observer => {
        this.listener.push(observer);
      }
    );
  }

  private userLoaded(): void {
    this.listener.forEach(l => l.next(true));
  }

  public loadUser(): void {
    const url = `${environment.endpoint}/user`;
    this.httpClient.get<User>(url).subscribe(user => {
      this.user = user;
      this.userLoaded();
    }, error1 => {
      this.user = null;
      this.userLoaded();
    });
  }

  public getUser(): User {
    return this.user;
  }

  public getUsers(): Observable<User[]> {
    const url = `${environment.endpoint}/user/all`;
    return this.httpClient.get <User[]>(url);
  }

  public getImage(): Observable<Blob> {
    const url = `${environment.endpoint}/user/avatar`;
    return this.httpClient.get(url, {responseType: 'blob'});
  }

  public updateUser(user: User): Promise<User> {
    const url = `${environment.endpoint}/user`;
    return this.httpClient.post<User>(url, user).toPromise();
  }

  public deleteUser(user: User): Promise<any> {
    const url = `${environment.endpoint}/user/${user.id}`;
    return this.httpClient.delete(url).toPromise();
  }

  public getLevel(xp: number): number {
    return this.calculateLevel(xp, 1);
  }

  private calculateLevel(xp: number, level: number): number {
    const step = 10;
    let xpForNextLevel = 0;

    for (let i = 1; i <= level; i++) {
      xpForNextLevel = xpForNextLevel + step;
    }

    // Termination condition: Level 200 or when XP is smaller than the required XP to the higher level
    if (level === 200 || (xp < xpForNextLevel)) {
      return level
    } else {
      return this.calculateLevel(xp, level + 1)
    }
  }
}
