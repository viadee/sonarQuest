import {Component, OnInit} from '@angular/core';
import {Quest, Task} from "../../../../../../Interfaces/Quest";
import {WorldService} from "../../../../../../services/world.service";
import {World} from "../../../../../../Interfaces/World";
import {MatDialog, MatDialogRef} from "@angular/material";
import {GamemasterAddFreeTaskComponent} from "./components/gamemaster-add-free-task/gamemaster-add-free-task.component";
import {GamemasterSuggestTasksComponent} from "./components/gamemaster-suggest-tasks/gamemaster-suggest-tasks.component";
import {QuestService} from "../../../../../../services/quest.service";
import {GamemasterQuestComponent} from "app/pages/gamemaster-page/components/gamemaster-quest/gamemaster-quest.component";
import {TaskService} from "../../../../../../services/task.service";

@Component({
  selector: 'app-gamemaster-quest-create',
  templateUrl: './gamemaster-quest-create.component.html',
  styleUrls: ['./gamemaster-quest-create.component.css']
})
export class GamemasterQuestCreateComponent implements OnInit {

  title: string;
  gold: number;
  xp: number;
  story: string;
  currentWorld: World;
  selectedWorld: World;
  worlds: World[];
  tasks: Task[] = [];
  images: any[];
  selectedImage: string;

  constructor(private questService: QuestService,
              private taskService: TaskService,
              private dialog: MatDialog,
              private worldService: WorldService,
              private dialogRef: MatDialogRef<GamemasterQuestComponent>) {
  }

  ngOnInit() {
    this.worldService.currentWorld$.subscribe(world => {
      this.currentWorld = world;
      this.selectWorld();
    });
    this.worldService.worlds$.subscribe(worlds => {
      this.worlds = worlds;
      this.selectWorld();
    })

    this.loadImages();
    this.selectedImage = "http://via.placeholder.com/200x200";
  }

  selectWorld(){
    if(this.worlds && this.currentWorld){
      this.selectedWorld = this.worlds.filter(world => world.id == this.currentWorld.id)[0]
    }
  }

  createQuest() {
    if (this.title && this.gold && this.xp && this.story && this.selectedImage && this.selectedWorld && (this.tasks.length != 0)) {

      let quest = {
        title: this.title,
        gold: this.gold,
        xp: this.xp,
        story: this.story,
        world: this.selectedWorld,
        image: this.selectedImage
      }
      this.questService.createQuest(quest).then((quest) => {
        if (quest.id) {

          let promiseArray = [];
          this.tasks.forEach((value, index) => {
            let addTaskToQuest = this.taskService.addToQuest(value, quest);
            promiseArray.push(addTaskToQuest);
          })
          let addQuestToWorld = this.questService.addToWorld(quest, this.selectedWorld);
          promiseArray.push(addQuestToWorld);
          return Promise.all(promiseArray);
        }
      }).then(() => {
        this.dialogRef.close(true);
      })
    }
  }


  addFreeTask() {
    this.dialog.open(GamemasterAddFreeTaskComponent, {panelClass:"dialog-sexy", data: [this.selectedWorld, this.tasks] })
      .afterClosed().subscribe(result => {
      if (result) {
        this.tasks.push(result)
      }
    });
  }

  suggestTasks() {
    this.dialog.open(GamemasterSuggestTasksComponent, {panelClass:"dialog-sexy", width:"500px", data: [this.selectedWorld, this.tasks] }).afterClosed().subscribe(result => {
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

  loadImages() {
    this.images = [];

    for (let i = 0; i < 15; i++) {
      this.images[i] = {};
      this.images[i].src = "assets/images/quest/hero" + (i + 1) + ".jpg";
      this.images[i].name = "hero" + (i + 1);
    }
  }
}
