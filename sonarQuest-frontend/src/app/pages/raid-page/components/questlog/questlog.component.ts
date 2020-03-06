import { Task } from './../../../../Interfaces/Task';
import { Quest } from './../../../../Interfaces/Quest';
import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { QuestService } from 'app/services/quest.service';
import { World } from 'app/Interfaces/World';
import { WorldService } from 'app/services/world.service';
import { QuestModel } from 'app/game-model/QuestModel';
import { Subscription } from 'rxjs';
import { ActivatedRoute, Router } from '@angular/router';
import {MatExpansionModule} from '@angular/material/expansion';

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
    private worldService: WorldService) {}

  ngOnInit() {
   // this.questList = new Array<QuestModel>();
    const id = this.route.snapshot.paramMap.get('id');

  }
}
