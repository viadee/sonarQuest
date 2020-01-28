import { Quest } from './../../../../Interfaces/Quest';
import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { QuestService } from 'app/services/quest.service';
import { World } from 'app/Interfaces/World';
import { WorldService } from 'app/services/world.service';
import { QuestModel } from 'app/game-model/QuestModel';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-questlog',
  templateUrl: './questlog.component.html',
  styleUrls: ['./questlog.component.css']
})
export class QuestlogComponent implements OnInit {

  currentWorld: World;

  @Input()
  public questList: Quest[];

  public questProgress: Subscription;

  constructor(private route: ActivatedRoute, private router: Router,
    private questService: QuestService,
    private worldService: WorldService) { }

  ngOnInit() {
   // this.questList = new Array<QuestModel>();
    const id = this.route.snapshot.paramMap.get('id');

  }

  /*
  loadQuests() {
    if (this.currentWorld) {
      return this.questService.getAllAvailableQuestsForWorldAndUser(this.currentWorld).then(quests => {
        quests.forEach((quest: Quest) => {
          let questModel = new QuestModel(quest.id, quest.title, quest.story, quest.creatorName, quest.status, quest.gold, quest.xp, quest.image, quest.visible, quest.startdate, quest.enddate, quest.world, quest.adventure, quest.tasks, quest.participations);
          questModel.questProgress = quest.questProgress;
          this.questList.push(questModel);
        });
      }).then(() => {
      });
    }
    
    this.worldService.currentWorld$.subscribe(world => {
      this.currentWorld = world
      this.loadQuests();
    })
  } */
}
