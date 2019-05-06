import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {SonarQubeConfig} from '../Interfaces/SonarQubeConfig';
import {World} from '../Interfaces/World';

@Injectable()
export class SonarQubeService {

  constructor(private http: HttpClient) {
  }

  public getConfig(): Promise<SonarQubeConfig> {
    const url = `${environment.endpoint}/sonarconfig`;
    return this.http.get<SonarQubeConfig>(url).toPromise();
  }

  public saveConfig(config: SonarQubeConfig): Promise<SonarQubeConfig> {
    const url = `${environment.endpoint}/sonarconfig`;
    return this.http.post<SonarQubeConfig>(url, config).toPromise();
  }

  public getIssueLink(key: string, world: World): Promise<string> {
    return this.getConfig().then(config => this.createIssueLink(key, world, config));
  }

  private createIssueLink(key: string, world: World, config: SonarQubeConfig): string {
    return config.sonarServerUrl + '/project/issues?id=' + world.project + '&issues=' + key + '&open=' + key;
  }

  public checkSonarQubeURL(sonarQubeConfig: SonarQubeConfig): Promise<boolean>{
    const url = `${environment.endpoint}/sonarconfig/checkSonarQubeUrl`;
    return this.http.post<boolean>(url, sonarQubeConfig).toPromise();
  }




}
