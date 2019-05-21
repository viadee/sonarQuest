import { Component, OnInit, Inject } from '@angular/core';
import { UserSkill } from 'app/Interfaces/UserSkill';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { SonarRule } from 'app/Interfaces/SonarRule';
import { SonarRuleService } from 'app/services/sonar-rule.service';
import { FormControl, Validators, FormGroup } from '@angular/forms';
import { UserSkillService } from 'app/services/user-skill.service';
import { TranslateService } from '@ngx-translate/core';
import { InnerSkillTreeComponent } from '../../inner-skill-tree.component';

@Component({
  selector: 'app-inner-skill-tree-add-skill-dialog',
  templateUrl: './inner-skill-tree-add-skill-dialog.component.html',
  styleUrls: ['./inner-skill-tree-add-skill-dialog.component.css']
})
export class InnerSkillTreeAddSkillDialogComponent implements OnInit {

  public unassignedSonarRules;
  private userSkillGroupID: number;
  public userSkills;

  private followingUserSkillError: boolean;
  private previousUserSkillError: boolean;
  private isRootError: boolean;

  createUserSkillForm = new FormGroup({
    name: new FormControl(null, [Validators.required]),
    followingUserSkills: new FormControl([], [this.matchFollowingUserSkillValidator(), this.matchIsRootValidator()]),
    previousUserSkills: new FormControl([], [Validators.required, this.matchPreviousUserSkillValidator()]),
    description: new FormControl(),
    requiredRepetitions: new FormControl(null, [Validators.required]),
    sonarRules: new FormControl([], [Validators.required])
  });

  constructor(/*public isRoot: boolean = false,*/
    @Inject(MAT_DIALOG_DATA) private transferedData,
    private userSkillService: UserSkillService,
    private translateService: TranslateService,
    private dialogRef: MatDialogRef<InnerSkillTreeAddSkillDialogComponent>
  ) {
    this.createUserSkillForm.get('requiredRepetitions').setValue(3);
  }

  ngOnInit() {
    this.unassignedSonarRules = this.transferedData.unassignedSonarRules;
    this.userSkillGroupID = this.transferedData.groupid;
    this.subscribeUserSkills();
    this.getUserSkills();
    console.log(this.unassignedSonarRules);
  }

  private subscribeUserSkills(): void {
    this.userSkillService.userSkills$.subscribe(userSkills => {
      this.userSkills = userSkills;
    });
  }

  private getUserSkills(): void {
    this.userSkillService.loadUserSkillsFromGroup(this.userSkillGroupID);
  }

  public isSomethingSelected(event) {
    console.log(event.source.ngControl.name);
    console.log(this.createUserSkillForm.get('followingUserSkills'));

    if (event.value.length === 0) {
      this.followingUserSkillError = false;
      this.previousUserSkillError = false;
      if (event.source.ngControl.control === this.createUserSkillForm.get('followingUserSkills')) {
        this.isRootError = false;
      }
    }
  }

  public addSkill(): void {
    if (!this.followingUserSkillError && !this.previousUserSkillError && !this.isRootError && this.createUserSkillForm.valid) {
      const newUserSkill: UserSkill = {
        name: this.createUserSkillForm.get('name').value,
        description: this.createUserSkillForm.get('description').value,
        root: false,
        followingUserSkills: this.createUserSkillForm.get('followingUserSkills').value,
        previousUserSkills: this.createUserSkillForm.get('previousUserSkills').value,
        sonarRules: this.createUserSkillForm.get('sonarRules').value,
        requiredRepetitions: this.createUserSkillForm.get('requiredRepetitions').value
      }
      this.userSkillService.createUserSkill(newUserSkill, this.userSkillGroupID)
        .then(userSkill => {
          if (userSkill.id !== null) {
            this.dialogRef.close(true);
          } else {
            this.dialogRef.close(false);
          }

        });
    }
  }

  public close(): void {
    this.dialogRef.close(false);
  }

  matchFollowingUserSkillValidator() {
    return (control: FormControl) => {
      const followingVal = control.value;
      if (typeof this.createUserSkillForm !== 'undefined' && typeof followingVal !== 'undefined') {
        const previousVal = this.createUserSkillForm.get('previousUserSkills').value;
        for (const following of followingVal) {
          const index = previousVal.indexOf(following, 0);
          if (index > -1) {
            this.followingUserSkillError = true;
            break;
          } else {
            this.followingUserSkillError = false;
          }
        }
        return this.followingUserSkillError ? { 'currentFollowingUserSkill': { followingVal } } : null;
      }

    }
  }
  matchIsRootValidator() {
    return (control: FormControl) => {
      const followingVal = control.value;
      if (typeof followingVal !== 'undefined') {
        for (const following of followingVal) {
          console.log(following);
          if (following.root) {
            this.isRootError = true;
            break;
          } else {
            this.isRootError = false;
          }
        }
        return this.isRootError ? { 'currentFollowingUserSkill': { followingVal } } : null;
      }

    }
  }

  matchPreviousUserSkillValidator() {
    return (control: FormControl) => {
      const previousVal = control.value;
      if (typeof this.createUserSkillForm !== 'undefined' && typeof previousVal !== 'undefined') {
        const followingVal = this.createUserSkillForm.get('followingUserSkills').value;
        for (const previous of previousVal) {
          const index = followingVal.indexOf(previous, 0);
          if (index > -1) {
            this.previousUserSkillError = true;
            break;
          } else {
            this.previousUserSkillError = false;
          }
        }
        return this.previousUserSkillError ? { 'currentPreviousUserSkill': { followingVal } } : null;
      }

    }
  }

  getErrorMessage(control: string) {
    if (this.createUserSkillForm.get(control).hasError('required')) {
      return this.translate('GLOBAL.REQUIRED_VALUE');
    }
    if (control === 'followingUserSkills' && this.followingUserSkillError) {
      this.createUserSkillForm.controls['followingUserSkills'].setErrors({ 'matchFollowingUserSkillValidator': true });
      return this.translate('SKILL_TREE_PAGE.ADD_INNER_SKILL_TREE.FOLLOWING_ERROR');
    }
    if (control === 'followingUserSkills' && this.isRootError) {
      this.createUserSkillForm.controls['followingUserSkills'].setErrors({ 'matchIsRootValidator': true });
      return this.translate('SKILL_TREE_PAGE.ADD_INNER_SKILL_TREE.IS_ROOT_ERROR');
    }
    if (control === 'previousUserSkills' && this.previousUserSkillError) {
      this.createUserSkillForm.controls['previousUserSkills'].setErrors({ 'matchPreviousUserSkillValidator': true });
      return this.translate('SKILL_TREE_PAGE.ADD_INNER_SKILL_TREE.PREVIOUS_ERROR');
    }
  }

  translate(messageString: string): string {
    let msg = '';
    this.translateService.get(messageString).subscribe(translateMsg => msg = translateMsg);
    return msg;
  }
}
