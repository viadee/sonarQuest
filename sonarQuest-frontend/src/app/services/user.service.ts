import {Injectable} from '@angular/core';
import {User} from '../Interfaces/User';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable, Subscriber} from 'rxjs';
import {AuthenticationService} from '../login/authentication.service';
import {map, tap} from 'rxjs/operators';

@Injectable()
export class UserService {

  private user: User;

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
    return this.httpClient.get <User[]>(url).pipe(tap((users: User[]) => {
      users.forEach(user =>
        user.lastLogin = user.lastLogin ? new Date(user.lastLogin).toTimeString() : null
      )
    }));
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
