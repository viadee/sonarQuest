import {Injectable, NgModule} from '@angular/core';
import {SonarCubeService} from "./sonar-cube.service";
import {SonarCubeConfig} from "../Interfaces/SonarCubeConfig";
import {World} from "../Interfaces/World";

@Injectable()
export class SonarCubeServiceMock {

  public getConfig(): Promise<SonarCubeConfig> {
    return new Promise<SonarCubeConfig>( () => {});
  }

  public saveConfig(config: SonarCubeConfig): Promise<SonarCubeConfig> {
    return new Promise<SonarCubeConfig>( () => {});
  }

  public getIssueLink(key: string, world: World): Promise<string> {
    return this.getConfig().then(config => this.createIssueLink(key, world, config));
  }

  private createIssueLink(key: string, world: World, config: SonarCubeConfig): string {
    return config.sonarServerUrl + '/project/issues?id=' + world.project + '&issues=' + key + '&open=' + key;
  }

  public checkSonarQubeURL(sonarQubeConfig: SonarCubeConfig): Promise<boolean>{
    return new Promise<boolean>( () => true);
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: SonarCubeService, useClass: SonarCubeServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class SonarCubeServiceTestingModule {

}
