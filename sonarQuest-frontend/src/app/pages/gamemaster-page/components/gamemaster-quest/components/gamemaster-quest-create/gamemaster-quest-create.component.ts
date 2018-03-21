import { Component, OnInit } from '@angular/core';
import { Quest, Task } from "../../../../../../Interfaces/Quest";
import { WorldService } from "../../../../../../services/world.service";
import { World } from "../../../../../../Interfaces/World";
import { MatDialog, MatDialogRef } from "@angular/material";
import { GamemasterAddFreeTaskComponent } from "./components/gamemaster-add-free-task/gamemaster-add-free-task.component";
import { GamemasterSuggestTasksComponent } from "./components/gamemaster-suggest-tasks/gamemaster-suggest-tasks.component";
import { QuestService } from "../../../../../../services/quest.service";
import { GamemasterQuestComponent } from "app/pages/gamemaster-page/components/gamemaster-quest/gamemaster-quest.component";
import { TaskService } from "../../../../../../services/task.service";

@Component({
  selector: 'app-gamemaster-quest-create',
  templateUrl: './gamemaster-quest-create.component.html',
  styleUrls: ['./gamemaster-quest-create.component.css']
})
export class GamemasterQuestCreateComponent implements OnInit {

  quest: Quest;
  title: string;
  gold: number;
  xp: number;
  story: string;
  currentWorld: World;
  tasks: Task[] = [];

  constructor(
    private questService: QuestService,
    private taskService: TaskService,
    private dialog: MatDialog,
    private worldService: WorldService,
    private dialogRef: MatDialogRef<GamemasterQuestComponent>
  ) { }

  ngOnInit() {
    this.worldService.currentWorld$.subscribe(world => this.currentWorld = world);
  }

  createQuest() {
    if (this.title && this.gold && this.xp && this.story && this.currentWorld && (this.tasks.length != 0)) {
      let quest = {
        title: this.title,
        gold: this.gold,
        xp: this.xp,
        story: this.story,
        world: this.currentWorld,
      }
      this.questService.createQuest(quest).then((quest) => {
        if (quest.id) {
          let promiseArray = [];
          this.tasks.forEach((value, index) => {
            let addTaskToQuest = this.taskService.addToQuest(value, quest);
            promiseArray.push(addTaskToQuest);
          })
          let addQuestToWorld = this.questService.addToWorld(quest, this.currentWorld);
          promiseArray.push(addQuestToWorld);
          return Promise.all(promiseArray);
        }
      }).then(() => {
        this.dialogRef.close();
      })
    }
  }


  addFreeTask() {
    this.dialog.open(GamemasterAddFreeTaskComponent, { data: [this.currentWorld, this.tasks] })
      .afterClosed().subscribe(result => {
        if (result) {
          this.tasks.push(result)
        }
      });
  }

  suggestTasks() {
    this.dialog.open(GamemasterSuggestTasksComponent, { data: [this.currentWorld, this.tasks] }).afterClosed().subscribe(result => {
      if (result) {
        this.tasks = this.tasks.concat(result)
      }
    })
  }

  removeTask(task: Task) {
    let taskIndex = this.tasks.indexOf(task);
    this.tasks.splice(taskIndex, 1);
  }

  calculateGoldAmountOfTasks(): number {
    return this.tasks.map(task => task.gold).reduce(function (a, b) {
      return a + b;
    }, 0);
  }
  calculateXpAmountOfTasks(): number {
    return this.tasks.map(task => task.xp).reduce(function (a, b) {
      return a + b;
    }, 0);
  }


}
