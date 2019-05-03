import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { GamemasterArtefactEditComponent } from '../../gamemaster-artefact-edit.component';
import { SkillService } from 'app/services/skill.service';
import { Skill } from 'app/Interfaces/Skill';

@Component({
  selector: 'app-gamemaster-skill-edit',
  templateUrl: './gamemaster-skill-edit.component.html',
  styleUrls: ['./gamemaster-skill-edit.component.css']
})
export class GamemasterSkillEditComponent implements OnInit {
  types = ['Gold', 'Xp'];
  name: string;
  type: string;
  value: number;

  constructor(
    private dialogRef: MatDialogRef<GamemasterArtefactEditComponent>,
    private skillServie: SkillService,
    @Inject(MAT_DIALOG_DATA) public skill: Skill) {
    this.name = skill.name;
    this.type = skill.type;
    this.value = skill.value;
  }

  ngOnInit() {
    console.log(this.skill);
  }

  edit() {
    const skill = {
      id: this.skill.id,
      name: this.name,
      type: this.type.toUpperCase(),
      value: this.value
    };
    if (typeof skill.id !== 'undefined') {
      this.skillServie.updateSkill(skill)
        .then(() => this.dialogRef.close(skill));
    } else {

      this.dialogRef.close(skill)
    }
  }

}
