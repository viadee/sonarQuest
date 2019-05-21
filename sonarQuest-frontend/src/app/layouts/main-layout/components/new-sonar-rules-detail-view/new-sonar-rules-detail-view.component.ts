import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { MainLayoutComponent } from '../../main-layout.component';
import { SonarRule } from 'app/Interfaces/SonarRule';
import { SonarQubeService } from 'app/services/sonar-qube.service';
import { RoutingUrls } from 'app/app-routing/routing-urls';

@Component({
  selector: 'app-new-sonar-rules-detail-view',
  templateUrl: './new-sonar-rules-detail-view.component.html',
  styleUrls: ['./new-sonar-rules-detail-view.component.css']
})
export class NewSonarRulesDetailViewComponent implements OnInit {

  public skillTreeURL = RoutingUrls.skilltree;

  constructor( private dialogRef: MatDialogRef<MainLayoutComponent>,
    @Inject(MAT_DIALOG_DATA) public unassignedSonarRules: SonarRule[],
    private sonarQubeService: SonarQubeService) { }

  ngOnInit() {
  }
  close(): void {
    this.dialogRef.close();
  }

  openRule(key: string) {
    this.sonarQubeService.getRuleLink(key)
      .then(link => window.open(link, '_blank'));
  }
}
