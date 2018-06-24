import { WorldService } from './../../../../services/world.service';
import {Component, OnInit} from '@angular/core';
import {SonarCubeService} from '../../../../services/sonar-cube.service';
import {SonarCubeConfig} from '../../../../Interfaces/SonarCubeConfig';
import {MatDialog, MatDialogRef, MatSnackBar} from '@angular/material';
import {LoadingComponent} from '../../../../components/loading/loading.component';
import {LoadingService} from '../../../../services/loading.service';
import {reject} from 'q';


@Component({
  selector: 'app-admin-sonar-cube',
  templateUrl: './admin-sonar-cube.component.html',
  styleUrls: ['./admin-sonar-cube.component.css']
})
export class AdminSonarCubeComponent implements OnInit {

  configName: string;

  sonarQubeUrl: string;

  sonarConfig: SonarCubeConfig;

  constructor(private sonarCubeService: SonarCubeService,
              private worldService: WorldService,
              private snackBar: MatSnackBar,
              private loadingService: LoadingService) {
  }

  ngOnInit() {
    this.sonarCubeService.getConfig().then(config => {
        this.sonarConfig = config;
        if (this.sonarConfig) {
          this.aktualisiereFormGroup();
        }
      }
    );
  }

  private aktualisiereFormGroup() {
    this.configName = this.sonarConfig.name;
    this.sonarQubeUrl = this.sonarConfig.sonarServerUrl;
  }

  checkSonarCubeUrl() {
    const loading = this.loadingService.getLoadingSpinner();
    let message: string;
    this.sonarCubeService.checkSonarQubeURL({name: this.configName, sonarServerUrl: this.sonarQubeUrl})
      .then(available => {
        if (available){
          message = 'Sonar Server is reachable';
      } else {
          message = 'Sonar Server is not reachable';
      }
        loading.close();
        this.snackBar.open(message, null, {duration: 2500});
    }).catch(() => {
        loading.close();
        message = 'Sonar Server is not reachable';
        this.snackBar.open(message, null, {duration: 2500});
    });
  }

  save() {
    const loading = this.loadingService.getLoadingSpinner();
    const config: SonarCubeConfig = {name: this.configName, sonarServerUrl: this.sonarQubeUrl};
    this.sonarCubeService.checkSonarQubeURL(config).then((available) => {
      if (!available) {
        return Promise.reject(new Error('Url not available'));
      } else {
        return this.sonarCubeService.saveConfig(config);
      }
    }).then(() => {
      this.worldService.worldChanged();
      loading.close();
    }).catch((error) => {
      loading.close();
      const message = 'Sonar Server is not reachable';
      this.snackBar.open(message, null, {duration: 2500});
    })
  }

}
