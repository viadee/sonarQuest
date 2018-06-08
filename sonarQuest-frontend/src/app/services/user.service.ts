import {Injectable} from '@angular/core';
import {User} from '../Interfaces/User';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs/Observable';
import {Http, Response, ResponseContentType} from '@angular/http';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class UserService {

  private userSubject: Subject<User> = new ReplaySubject(1);
  public avatar$ = this.userSubject.asObservable();

  constructor(private httpClient: HttpClient,
              private http: Http) {
  }

  public getUser(): Observable<User> {
    const url = `${environment.endpoint}/user`;
    this.httpClient.get<User>(url).subscribe(
      user => this.userSubject.next(user),
      err => this.userSubject.error(err));

    return this.userSubject;
  }

  public getImage(): Observable<Blob> {
    const url = `${environment.endpoint}/user/avator`;
    return this.http
      .get(url, {responseType: ResponseContentType.Blob})
      .map((res: Response) => res.blob());
  }

  public getDevelopers(): Observable<User[]> {
    const url = `${environment.endpoint}/user/all`;
    return this.httpClient.get <User[]>(url);
  }

  public updateUser(user: User): Promise<User> {
    console.log(user)
    const url = `${environment.endpoint}/user`;
    return this.httpClient.post<User>(url, user).toPromise();
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

    //Termination condition: Level 200 or when XP is smaller than the required XP to the higher level
    if (level === 200 || (xp < xpForNextLevel)) {
      return level
    } else {
      return this.calculateLevel(xp, level + 1)
    }
  }
}
