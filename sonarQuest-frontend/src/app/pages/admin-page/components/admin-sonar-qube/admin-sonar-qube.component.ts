import {WorldService} from '../../../../services/world.service';
import {Component, OnInit} from '@angular/core';
import {SonarQubeService} from '../../../../services/sonar-qube.service';
import {SonarQubeConfig} from '../../../../Interfaces/SonarQubeConfig';
import {MatSnackBar} from '@angular/material';
import {LoadingService} from '../../../../services/loading.service';
import {TranslateService} from "@ngx-translate/core";


@Component({
  selector: 'sq-admin-sonar-qube',
  templateUrl: './admin-sonar-qube.component.html',
  styleUrls: ['./admin-sonar-qube.component.css']
})
export class AdminSonarQubeComponent implements OnInit {

  configName: string;

  sonarQubeUrl: string;

  httpBasicAuthUsername: string;

  httpBasicAuthPassword: string;

  sonarConfig: SonarQubeConfig;

  constructor(private sonarQubeService: SonarQubeService,
              private worldService: WorldService,
              private snackBar: MatSnackBar,
              private loadingService: LoadingService,
              private translate: TranslateService) {
  }

  ngOnInit() {
    this.sonarQubeService.getConfig().then(config => {
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

  checkSonarQubeUrl() {
    const loading = this.loadingService.getLoadingSpinner();
    let message: string;
    this.sonarQubeService.checkSonarQubeURL({
      name: this.configName,
      sonarServerUrl: this.sonarQubeUrl,
      httpBasicAuthPassword: this.httpBasicAuthPassword,
      httpBasicAuthUsername: this.httpBasicAuthUsername
    })
      .then(available => {
        if (available) {

          this.translate.get("ADMIN_PAGE.SONARQUBE_CONNECTION_TEST.SUCCESS").subscribe((translatedMessage) => {
            message = translatedMessage;
            loading.close();
            this.snackBar.open(message, null, {duration: 2500});
          });
        } else {
          this.translate.get("ADMIN_PAGE.SONARQUBE_CONNECTION_TEST.ERROR").subscribe((translatedMessage) => {
            message = translatedMessage;
            loading.close();
            this.snackBar.open(message, null, {duration: 2500});
          });
        }
      }).catch(() => {
      loading.close();
      this.translate.get("ADMIN_PAGE.SONARQUBE_CONNECTION_TEST.ERROR").subscribe((translatedMessage) => {
        message = translatedMessage;
        loading.close();
        this.snackBar.open(message, null, {duration: 2500});
      });
    });
  }

  save() {
    const loading = this.loadingService.getLoadingSpinner();
    const config: SonarQubeConfig = {
      name: this.configName,
      sonarServerUrl: this.sonarQubeUrl,
      httpBasicAuthUsername: this.httpBasicAuthUsername,
      httpBasicAuthPassword: this.httpBasicAuthPassword
    };
    this.sonarQubeService.checkSonarQubeURL(config).then((available) => {
      if (!available) {
        return Promise.reject(new Error('Url not available'));
      } else {
        this.translate.get("ADMIN_PAGE.CONNECTION_SAVED").subscribe((translatedMessage) => {
          loading.close();
          this.snackBar.open(translatedMessage, null, {duration: 2500});
        });
        return this.sonarQubeService.saveConfig(config);
      }
    }).then(() => {
      this.worldService.worldChanged();
      loading.close();
    }).catch(() => {
      this.translate.get("ADMIN_PAGE.SONARQUBE_CONNECTION_TEST.ERROR").subscribe((translatedMessage) => {
        loading.close();
        this.snackBar.open(translatedMessage, null, {duration: 2500});
      });
    })
  }

}
