import { Observable, ReplaySubject, Subject, Subscriber } from 'rxjs';
import { Injectable } from '@angular/core';
import { User } from '../Interfaces/User';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { tap } from 'rxjs/operators';
import * as moment from 'moment';
import { Timestamp } from 'rxjs/internal/operators/timestamp';

@Injectable()
export class UserService {

  private userSubject: Subject<User> = new ReplaySubject(1);
  user$ = this.userSubject.asObservable();

  constructor(private httpClient: HttpClient
  ) { }

  public loadUser(): void {
    const url = `${environment.endpoint}/user`;
    this.httpClient.get<User>(url).subscribe(user => {
      this.userSubject.next(user);
    });
  }

  public updateLastTavernVisit(): Promise<any> {
    const url = `${environment.endpoint}/user/updateLastTavernVisit`;
    const date = new Date();
    return this.httpClient.post(url, date.getTime()).toPromise();
  }

  public getUsers(): Observable<User[]> {
    const url = `${environment.endpoint}/user/all`;
    return this.httpClient.get<User[]>(url).pipe(tap((users: User[]) => {
      users.forEach(user => {
        user.lastLogin = user.lastLogin ? moment(new Date(user.lastLogin)).format('DD.MM.YYYY HH:mm:ss') : null
       // user.lastTavernVisit = user.lastTavernVisit ? moment(new Date(user.lastTavernVisit)).format('DD.MM.YYYY HH:mm:ss') : null
      })
    }));
  }

  public getImage(): Observable<Blob> {
    const url = `${environment.endpoint}/user/avatar`;
    return this.httpClient.get(url, { responseType: 'blob' });
  }

  public getImageForUser(user: User): Observable<Blob> {
    const url = `${environment.endpoint}/user/${user.id}/avatar`;
    return this.httpClient.get(url, { responseType: 'blob' });
  }
  public getImageForUserId(userId: Number): Observable<Blob> {
    const url = `${environment.endpoint}/user/${userId}/avatar`;
    return this.httpClient.get(url, { responseType: 'blob' });
  }

  public updateUser(user: User): Promise<User> {
    const url = `${environment.endpoint}/user`;
    user.lastLogin = null;
    return this.httpClient.post<User>(url, user).toPromise();
  }

  public deleteUser(user: User): Promise<any> {
    const url = `${environment.endpoint}/user/${user.id}`;
    return this.httpClient.delete(url).toPromise();
  }

}
