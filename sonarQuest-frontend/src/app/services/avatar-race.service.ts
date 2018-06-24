import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {AvatarRace} from '../Interfaces/AvatarRace';

@Injectable()
export class AvatarRaceService {
  constructor(public httpClient: HttpClient) {
  }

  public getRaces(): Promise<AvatarRace[]> {
    const url = `${environment.endpoint}/avatarRace`;
    return this.httpClient.get <AvatarRace[]>(url).toPromise();
  }
}
