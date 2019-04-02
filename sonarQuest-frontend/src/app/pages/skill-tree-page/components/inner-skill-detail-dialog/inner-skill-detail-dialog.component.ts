import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { SonarCubeConfig } from 'app/Interfaces/SonarCubeConfig';
import { SonarCubeService } from 'app/services/sonar-cube.service';
import { PermissionService } from 'app/services/permission.service';
import { RoutingUrls } from 'app/app-routing/routing-urls';

@Component({
  selector: 'app-inner-skill-detail-dialog',
  templateUrl: './inner-skill-detail-dialog.component.html',
  styleUrls: ['./inner-skill-detail-dialog.component.css']
})
export class InnerSkillDetailDialogComponent implements OnInit {

  public isAdmin: boolean;
  public isGamemaster: boolean;
  public isDeveloper: boolean;




  constructor(
    public dialogRef: MatDialogRef<InnerSkillDetailDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data, private sonarCubeService: SonarCubeService, private permissionService: PermissionService) { }

  ngOnInit() {
    this.isAdmin = true && this.permissionService.isUrlVisible(RoutingUrls.admin);
    this.isGamemaster = true && this.permissionService.isUrlVisible(RoutingUrls.gamemaster);
    this.isDeveloper = true && this.permissionService.isUrlVisible(RoutingUrls.skilltree);

  }
  onNoClick(): void {
    this.dialogRef.close();
  }

  openRule(key: string) {
    this.sonarCubeService.getRuleLink(key)
      .then(link => window.open(link, '_blank'));
  }
}
