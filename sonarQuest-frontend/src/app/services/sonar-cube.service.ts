import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { SonarCubeConfig } from '../Interfaces/SonarCubeConfig';
import { World } from '../Interfaces/World';
import { SonarRule } from 'app/Interfaces/SonarRule';

@Injectable()
export class SonarCubeService {

  constructor(private http: HttpClient) {
  }

  public getConfig(): Promise<SonarCubeConfig> {
    const url = `${environment.endpoint}/sonarconfig`;
    return this.http.get<SonarCubeConfig>(url).toPromise();
  }

  public saveConfig(config: SonarCubeConfig): Promise<SonarCubeConfig> {
    const url = `${environment.endpoint}/sonarconfig`;
    return this.http.post<SonarCubeConfig>(url, config).toPromise();
  }

  public getIssueLink(key: string, world: World): Promise<string> {
    return this.getConfig().then(config => this.createIssueLink(key, world, config));
  }

  private createRuleLink(key: String, config: SonarCubeConfig): string {
    if (config.sonarServerUrl.indexOf('sonarcloud.io') !== -1) {
      return config.sonarServerUrl + '/organizations/default/rules?open=' + key + '&rule_key=' + key;
    }
    return config.sonarServerUrl + '/coding_rules?open=' +key;
  }

  public getRuleLink(key: string): Promise<string> {
    return this.getConfig().then(config => this.createRuleLink(key, config));
  }

  private createIssueLink(key: string, world: World, config: SonarCubeConfig): string {
    console.log(config.sonarServerUrl);
    return config.sonarServerUrl + '/project/issues?id=' + world.project + '&issues=' + key + '&open=' + key
  }

  public checkSonarQubeURL(sonarQubeConfig: SonarCubeConfig): Promise<boolean> {
    const url = `${environment.endpoint}/sonarconfig/checkSonarQubeUrl`;
    return this.http.post<boolean>(url, sonarQubeConfig).toPromise();
  }

}
