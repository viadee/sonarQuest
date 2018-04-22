import {Component, OnInit} from '@angular/core';
import {SonarCubeService} from '../../../../services/sonar-cube.service';
import {SonarCubeConfig} from '../../../../Interfaces/SonarCubeConfig';
import {FormBuilder, FormGroup} from '@angular/forms';
import {MatSnackBar} from '@angular/material';

@Component({
  selector: 'app-admin-sonar-cube',
  templateUrl: './admin-sonar-cube.component.html',
  styleUrls: ['./admin-sonar-cube.component.css']
})
export class AdminSonarCubeComponent implements OnInit {

  configName: string;

  sonarCubeUrl: string;

  projectName: string;

  sonarConfig: SonarCubeConfig;

  constructor(private sonarCubeService: SonarCubeService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.sonarCubeService.getConfigs().subscribe(configs => {
        console.log('Zurück kam folgendes: ' + configs);
        this.sonarConfig = configs[0];
        if (this.sonarConfig) {
          this.aktualisiereFormGroup();
        }
      }
    );
  }

  private aktualisiereFormGroup() {
    this.configName = this.sonarConfig.name;
    this.sonarCubeUrl = this.sonarConfig.sonarServerUrl;
    this.projectName = this.sonarConfig.sonarProject;
  }

  checkSonarCubeUrl() {
    // TODO
    const message = 'Sonar URL is reachable';
    this.snackBar.open(message, null, {duration: 2500});
  }

  checkProjectname() {
    this.sonarCubeService.getConfigs().subscribe(configs => {
      let enthalten = false;
      for (const config of configs) {
        if (config.sonarProject === this.projectName) {
          enthalten = true;
          break;
        }
      }
      const message: string = enthalten ? 'Sonar Project does exist' : 'Sonar Project does not exists';
      this.snackBar.open(message, null, {duration: 2500});
    });

  }

  save() {
    const config: SonarCubeConfig = {name: this.configName, sonarServerUrl: this.sonarCubeUrl, sonarProject: this.projectName};
    console.log('saving' + config + config.name + config.sonarServerUrl + config.sonarProject);
    this.sonarCubeService.saveConfig(config);
  }

}
