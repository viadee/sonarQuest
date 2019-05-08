import {Injectable, NgModule} from '@angular/core';
import {SonarQubeService} from "./sonar-qube.service";
import {SonarQubeConfig} from "../Interfaces/SonarQubeConfig";
import {World} from "../Interfaces/World";

@Injectable()
export class SonarQubeServiceMock {

  public getConfig(): Promise<SonarQubeConfig> {
    return new Promise<SonarQubeConfig>( () => {});
  }

  public saveConfig(config: SonarQubeConfig): Promise<SonarQubeConfig> {
    return new Promise<SonarQubeConfig>( () => {});
  }

  public getIssueLink(key: string, world: World): Promise<string> {
    return this.getConfig().then(config => this.createIssueLink(key, world, config));
  }

  private createIssueLink(key: string, world: World, config: SonarQubeConfig): string {
    return config.sonarServerUrl + '/project/issues?id=' + world.project + '&issues=' + key + '&open=' + key;
  }

  public checkSonarQubeURL(sonarQubeConfig: SonarQubeConfig): Promise<boolean>{
    return new Promise<boolean>( () => true);
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: SonarQubeService, useClass: SonarQubeServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class SonarQubeServiceTestingModule {

}
