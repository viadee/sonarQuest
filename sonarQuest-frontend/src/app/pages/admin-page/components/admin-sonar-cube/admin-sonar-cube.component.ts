import {WorldService} from '../../../../services/world.service';
import {Component, OnInit} from '@angular/core';
import {SonarCubeService} from '../../../../services/sonar-cube.service';
import {SonarCubeConfig} from '../../../../Interfaces/SonarCubeConfig';
import {MatSnackBar} from '@angular/material';
import {LoadingService} from '../../../../services/loading.service';
import {TranslateService} from "@ngx-translate/core";


@Component({
  selector: 'sq-admin-sonar-cube',
  templateUrl: './admin-sonar-cube.component.html',
  styleUrls: ['./admin-sonar-cube.component.css']
})
export class AdminSonarCubeComponent implements OnInit {

  configName: string;

  sonarQubeUrl: string;

  httpBasicAuthUsername: string;

  httpBasicAuthPassword: string;

  sonarConfig: SonarCubeConfig;

  constructor(private sonarCubeService: SonarCubeService,
              private worldService: WorldService,
              private snackBar: MatSnackBar,
              private loadingService: LoadingService,
              private translate: TranslateService) {
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
    this.httpBasicAuthUsername = this.sonarConfig.httpBasicAuthUsername;
    this.httpBasicAuthPassword = this.sonarConfig.httpBasicAuthPassword;
  }

  checkSonarCubeUrl() {
    const loading = this.loadingService.getLoadingSpinner();
    let message: string;
    this.sonarCubeService.checkSonarQubeURL({
      name: this.configName,
      sonarServerUrl: this.sonarQubeUrl,
      httpBasicAuthPassword: this.httpBasicAuthPassword,
      httpBasicAuthUsername: this.httpBasicAuthUsername
    })
      .then(available => {
        if (available) {

          this.translate.get("ADMIN_PAGE.SONAR_CUBE_SERVER_CHECK_SUCCESS").subscribe((translatedMessage) => {
            message = translatedMessage;
            loading.close();
            this.snackBar.open(message, null, {duration: 2500});
          });
        } else {
          this.translate.get("ADMIN_PAGE.SONAR_CUBE_SERVER_CHECK_ERROR").subscribe((translatedMessage) => {
            message = translatedMessage;
            loading.close();
            this.snackBar.open(message, null, {duration: 2500});
          });
        }
      }).catch(() => {
      loading.close();
      this.translate.get("ADMIN_PAGE.SONAR_CUBE_SERVER_CHECK_ERROR").subscribe((translatedMessage) => {
        message = translatedMessage;
        loading.close();
        this.snackBar.open(message, null, {duration: 2500});
      });
    });
  }

  save() {
    const loading = this.loadingService.getLoadingSpinner();
    const config: SonarCubeConfig = {
      name: this.configName,
      sonarServerUrl: this.sonarQubeUrl,
      httpBasicAuthUsername: this.httpBasicAuthUsername,
      httpBasicAuthPassword: this.httpBasicAuthPassword
    };
    this.sonarCubeService.checkSonarQubeURL(config).then((available) => {
      if (!available) {
        return Promise.reject(new Error('Url not available'));
      } else {
        return this.sonarCubeService.saveConfig(config);
      }
    }).then(() => {
      this.worldService.worldChanged();
      loading.close();
    }).catch(() => {
      loading.close();
      const message = this.translate.get("SONAR_CUBE_SERVER_CHECK_ERROR");
      this.snackBar.open(message, null, {duration: 2500});
    })
  }

}
