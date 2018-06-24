import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {AvatarClass} from '../Interfaces/AvatarClass';

@Injectable()
export class AvatarClassService {
  constructor(public httpClient: HttpClient) {
  }

  public getClasses(): Promise<AvatarClass[]> {
    const url = `${environment.endpoint}/avatarClass`;
    return this.httpClient.get <AvatarClass[]>(url).toPromise();
  }
}
