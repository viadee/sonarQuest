import {Injectable} from '@angular/core';
import {User} from '../Interfaces/User';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';

@Injectable()
export class UserService {

  constructor(private http: HttpClient){
  }

  public getUser(): Observable<User> {
    const url = `${environment.endpoint}/user`;
    return this.http.get<User>(url);
  }
}
