import { Developer } from './../../../../../../Interfaces/Developer.d';
import { DeveloperService } from './../../../../../../services/developer.service';
import { ParticipationService } from './../../../../../../services/participation.service';
import { Quest } from './../../../../../../Interfaces/Quest';
import { MAT_DIALOG_DATA } from '@angular/material';
import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { AvailableQuestsComponent } from '../../available-quests.component';

@Component({
  selector: 'app-view-available-quest',
  templateUrl: './view-available-quest.component.html',
  styleUrls: ['./view-available-quest.component.css']
})
export class ViewAvailableQuestComponent implements OnInit {

  developer: Developer;
  constructor(
    private dialogRef: MatDialogRef<AvailableQuestsComponent>,
    @Inject(MAT_DIALOG_DATA) public quest: Quest,
    public participationService: ParticipationService,
    public developerService: DeveloperService
  ) { }

  ngOnInit() {
    this.developerService.avatar$.subscribe(developer => this.developer = developer)
  }

  participateInQuest() {
    return this.participationService.createParticipation(this.developer, this.quest)
      .then((msg) => {
        this.dialogRef.close();
      })
  }

}
