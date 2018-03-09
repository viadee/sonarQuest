import { AvailableQuestsComponent } from './components/available-quests/available-quests.component';
import { Quest } from './../../Interfaces/Quest';
import { QuestService } from './../../services/quest.service';
import { Component, OnInit, ContentChild, ViewChild } from '@angular/core';
import { WorldService } from '../../services/world.service';
import { DeveloperService } from '../../services/developer.service';
import { ParticipatedQuestsComponent } from './components/participated-quests/participated-quests.component';

@Component({
  selector: 'app-quest-page',
  templateUrl: './quest-page.component.html',
  styleUrls: ['./quest-page.component.css']
})
export class QuestPageComponent implements OnInit {

  constructor(
    private questService: QuestService,
    private worldService: WorldService,
    private developerService: DeveloperService) { }

  ngOnInit() {
  }

  


  

}
