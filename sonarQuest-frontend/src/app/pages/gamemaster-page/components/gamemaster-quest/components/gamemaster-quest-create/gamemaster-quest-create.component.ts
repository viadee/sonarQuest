import {Component, OnInit} from '@angular/core';
import {Task} from '../../../../../../Interfaces/Task';
import {WorldService} from '../../../../../../services/world.service';
import {World} from '../../../../../../Interfaces/World';
import {MatDialog, MatDialogRef} from '@angular/material';
import {GamemasterAddFreeTaskComponent} from './components/gamemaster-add-free-task/gamemaster-add-free-task.component';
import {GamemasterSuggestTasksComponent} from './components/gamemaster-suggest-tasks/gamemaster-suggest-tasks.component';
import {QuestService} from '../../../../../../services/quest.service';
import {GamemasterQuestComponent} from 'app/pages/gamemaster-page/components/gamemaster-quest/gamemaster-quest.component';
import {TaskService} from '../../../../../../services/task.service';
import { QuestState } from 'app/Interfaces/QuestState';
import { UserService } from 'app/services/user.service';
import { User } from 'app/Interfaces/User';

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
  visible: boolean;
  currentWorld: World;
  worlds: World[];
  tasks: Task[] = [];
  images: any[];
  selectedImage: string;
  user: User;

  constructor(private questService: QuestService,
              private taskService: TaskService,
              private dialog: MatDialog,
              private worldService: WorldService,
              private dialogRef: MatDialogRef<GamemasterQuestComponent>,
              private userService: UserService) {
  }

  ngOnInit() {
    this.worldService.currentWorld$.subscribe(world => this.currentWorld = world)
    this.worldService.worlds$.subscribe(worlds => {
      this.worlds = worlds;
      this.loadImages();
      this.selectedImage = 'http://via.placeholder.com/200x200';
    })

    this.userService.user$.subscribe(user => this.user = user)
  }

  createQuest() {
    if (this.title && this.gold && this.xp && this.story && this.selectedImage && this.currentWorld && (this.tasks.length !== 0)) {

      const quest = {
        title: this.title,
        gold: this.gold,
        xp: this.xp,
        visible: this.visible,
        story: this.story,
        world: this.currentWorld,
        image: this.selectedImage,
        creatorName: this.user.username
      };
      this.questService.createQuest(quest).then((createdQuest) => {
        if (createdQuest.id) {
          const promiseArray = [];
          this.tasks.forEach((value, index) => {
            const addTaskToQuest = this.taskService.addToQuest(value, createdQuest);
            promiseArray.push(addTaskToQuest);
          });
          const addQuestToWorld = this.questService.addToWorld(createdQuest, this.currentWorld);
          promiseArray.push(addQuestToWorld);
          return Promise.all(promiseArray);
        }
      }).then(() => {
        this.dialogRef.close(true);
      })
    }
  }


  addFreeTask() {
    this.dialog.open(GamemasterAddFreeTaskComponent, {panelClass: 'dialog-sexy', data: [this.currentWorld]})
      .afterClosed().subscribe(result => {
      if (result) {
        this.tasks.push(result)
      }
    });
  }

  suggestTasks() {
    this.dialog.open(GamemasterSuggestTasksComponent, {
      panelClass: 'dialog-sexy',
      data: [this.currentWorld, this.tasks]
    }).afterClosed().subscribe(result => {
      if (result) {
        this.tasks = this.tasks.concat(result)
      }
    })
  }

  removeTask(task: Task) {
    const taskIndex = this.tasks.indexOf(task);
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
      this.images[i].src = 'assets/images/quest/hero' + (i + 1) + '.jpg';
      this.images[i].name = 'hero' + (i + 1);
    }
  }
}
