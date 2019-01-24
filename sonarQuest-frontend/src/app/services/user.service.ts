import { ReplaySubject } from 'rxjs/ReplaySubject';
import {Injectable} from '@angular/core';
import {User} from '../Interfaces/User';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs/Observable';
import {AuthenticationService} from '../login/authentication.service';
import {Subscriber} from 'rxjs/Subscriber';
import { Subject } from 'rxjs';

@Injectable()
export class UserService {

  private user: User;

  private userSubject: Subject<User> = new ReplaySubject(1);
  user$ = this.userSubject.asObservable();

  private listener: Subscriber<boolean>[] = [];

  constructor(private httpClient: HttpClient,
              private authenticationService: AuthenticationService) {
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
      this.userSubject.next(user);
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

  public getImageForUser(user: User): Observable<Blob> {
    const url = `${environment.endpoint}/user/${user.id}/avatar`;
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

}
