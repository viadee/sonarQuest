import {MatDialogRef} from '@angular/material';
import {GamemasterArtefactCreateComponent} from './../../gamemaster-artefact-create.component';
import {SkillService} from './../../../../../../../../services/skill.service';
import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-gamemaster-skill-create',
  templateUrl: './gamemaster-skill-create.component.html',
  styleUrls: ['./gamemaster-skill-create.component.css']
})
export class GamemasterSkillCreateComponent implements OnInit {
  types = ['Gold', 'Xp'];
  name: string;
  type: string;
  value: number;

  constructor(
    private dialogRef: MatDialogRef<GamemasterArtefactCreateComponent>,
    private skillServie: SkillService) {
  }

  ngOnInit() {
  }

  create() {
    const skill = {
      name: this.name,
      type: this.type.toUpperCase(),
      value: this.value
    };
    this.skillServie.createSkill(skill)
      .then(() => this.dialogRef.close(skill));
  }
}

