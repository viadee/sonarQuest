import {Injectable} from '@angular/core';
import {World} from '../Interfaces/World';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {GitServer} from '../Interfaces/GitServer';

@Injectable()
export class GitServerService {
  constructor(private http: HttpClient) {
  }

  public getServerForWorld(world: World): Promise<GitServer> {
    return this.http.get<GitServer>(`${environment.endpoint}/gitserver/world/${world.id}`).toPromise().then((server) => {
      if (server == null) {
        server = {id: undefined, url: '', world: world}
      }
      return server;
    });
  }

  public updateGitServer(server: GitServer): Promise<GitServer> {
    console.log(server);
    return this.http.post<GitServer>(`${environment.endpoint}/gitserver`, server).toPromise();
  }

  public deleteGitServer(server: GitServer): Promise<GitServer> {
    return this.http.delete<GitServer>(`${environment.endpoint}/gitserver/${server.id}`).toPromise();
  }
}
