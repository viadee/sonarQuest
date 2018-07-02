import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Role} from '../Interfaces/Role';

@Injectable()
export class RoleService {
  constructor(public httpClient: HttpClient) {
  }

  public getRoles(): Promise<Role[]> {
    const url = `${environment.endpoint}/role`;
    return this.httpClient.get <Role[]>(url).toPromise();
  }
}
