import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs/Observable';
import {SonarCubeConfig} from '../Interfaces/SonarCubeConfig';
import {AppComponent} from '../app.component';
import {WorldService} from './world.service';

@Injectable()
export class SonarCubeService {

  constructor(private http: HttpClient) {
  }

  public getConfigs(): Observable<SonarCubeConfig[]> {
    const url = `${environment.endpoint}/sonarconfig`;
    console.log('Ich rufe auf ' + url);
    return this.http.get<SonarCubeConfig[]>(url);
  }

  public getConfig(name: String): Observable<SonarCubeConfig> {
    const url = `${environment.endpoint}/sonarconfig/${name}`;
    return this.http.get<SonarCubeConfig>(url);
  }

  public saveConfig(config: SonarCubeConfig) {
    const url = `${environment.endpoint}/sonarconfig`;
    console.log('Post an ' + url);
    return this.http.post(url, config).subscribe();
  }

  public getIssueLink(key: string, worldName: string): Observable<string> {
    return this.getConfig(worldName).map(config => this.createIssueLink(key, config));
  }

  private createIssueLink(key: string, config: SonarCubeConfig): string {
    return config.sonarServerUrl + '/project/issues?id=' + config.sonarProject + '&open=' + key;
  }
}
