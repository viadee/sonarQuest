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

  /*  public name: string;
   public followingUserSkills: UserSkill[];
   public prviousUserSkills: UserSkill[];
   public description: string;
   public requierdRepetitions: number;
   public sonarRules: SonarRule[]; */
  public unassignedSonarRules;
  public userSkills;
  public toRemoveUserSkill: UserSkill[];
  // public filteredUserSkillsFollowing: UserSkill[];
  //public filteredUserSkillsPrevious: UserSkill[];

  private followingUserSkillError: boolean;
  private previousUserSkillError: boolean;

  createUserSkillForm = new FormGroup({
    name: new FormControl(null, [Validators.required]),
    followingUserSkills: new FormControl([], [this.matchFollowingUserSkillValidator()]),
    previousUserSkills: new FormControl([], [this.matchPreviousUserSkillValidator()]),
    description: new FormControl(),
    requiredRepetitions: new FormControl(null, [Validators.required]),
    sonarRules: new FormControl([], [Validators.required])
  });

  constructor(/*public isRoot: boolean = false,*/
    @Inject(MAT_DIALOG_DATA) public userSkillGroup: number,
    private sonarRuleService: SonarRuleService,
    private userSkillService: UserSkillService,
    private translateService: TranslateService,
    private dialogRef: MatDialogRef<InnerSkillTreeAddSkillDialogComponent>
  ) { }

  ngOnInit() {
    this.subscribeUnassignedSonarRules();
    this.subscribeUserSkills();
    this.getUnassingendSonarRules();
    this.getUserSkills();
    console.log(this.unassignedSonarRules);
  }

  private subscribeUnassignedSonarRules(): void {
    this.sonarRuleService.unassignedSonarRules$.subscribe(unassignedSonarRules => {
      this.unassignedSonarRules = unassignedSonarRules;
    });
  }

  private subscribeUserSkills(): void {
    this.userSkillService.userSkills$.subscribe(userSkills => {
      this.userSkills = userSkills;
    });
  }

  private getUnassingendSonarRules(): void {
    this.sonarRuleService.loadUnassignedSonarRules();
  }

  private getUserSkills(): void {
    this.userSkillService.loadUserSkillsFromGroup(this.userSkillGroup);
  }

  public isSomethingSelected(event) {
    if (event.value.length === 0) {
      this.previousUserSkillError = false;
      this.followingUserSkillError = false;
    }
  }

  public addSkill(): void {

  }

  public close():void{
    this.dialogRef.close();
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
