import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { PermissionService } from 'app/services/permission.service';
import { RoutingUrls } from 'app/app-routing/routing-urls';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserSkillService } from 'app/services/user-skill.service';
import { UserSkill } from 'app/Interfaces/UserSkill';
import {SonarQubeService} from 'app/services/sonar-qube.service';

@Component({
  selector: 'app-inner-skill-detail-dialog',
  templateUrl: './inner-skill-detail-dialog.component.html',
  styleUrls: ['./inner-skill-detail-dialog.component.css']
})
export class InnerSkillDetailDialogComponent implements OnInit {

  public isAdmin: boolean;
  public isGamemaster: boolean;
  public isDeveloper: boolean;

  editForm = new FormGroup({
    requiredRepetitions: new FormControl(null, [Validators.required,  Validators.pattern('^[0-9]*$')])
  });


  constructor(
    public dialogRef: MatDialogRef<InnerSkillDetailDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data,
    private sonarCubeService: SonarQubeService,
    private permissionService: PermissionService,
    private userSkillService: UserSkillService) { }

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


  getErrorMessage(control: string) {
    if (this.editForm.get(control).hasError('required')) {
      return 'This value is mandatory';
    }
  }

  updateRepetitions() {
    this.userSkillService.updateUserSkill(this.skillTreeObjectToUserSkill(this.data));
    this.dialogRef.close();
  }

  skillTreeObjectToUserSkill(data): UserSkill {
    console.log(data)
    const userSkill: UserSkill = {
      id: parseInt(data.id),
      name: data.label,
      requiredRepetitions: this.editForm.get('requiredRepetitions').value,
      followingUserSkills: null
    };
    return userSkill;
  }
}
