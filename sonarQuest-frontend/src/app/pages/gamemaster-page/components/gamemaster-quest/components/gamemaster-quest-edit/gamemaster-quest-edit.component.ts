import {GamemasterSuggestTasksComponent} from './../gamemaster-quest-create/components/gamemaster-suggest-tasks/gamemaster-suggest-tasks.component';
import {GamemasterAddFreeTaskComponent} from './../gamemaster-quest-create/components/gamemaster-add-free-task/gamemaster-add-free-task.component';
import {QuestService} from './../../../../../../services/quest.service';
import {MAT_DIALOG_DATA, MatDialog} from '@angular/material';
import {MatDialogRef} from '@angular/material';
import {Component, OnInit, Inject} from '@angular/core';
import {GamemasterQuestComponent} from '../../gamemaster-quest.component';
import {Quest} from '../../../../../../Interfaces/Quest';
import {TaskService} from '../../../../../../services/task.service';
import {Task} from '../../../../../../Interfaces/Task';

@Component({
  selector: 'app-gamemaster-quest-edit',
  templateUrl: './gamemaster-quest-edit.component.html',
  styleUrls: ['./gamemaster-quest-edit.component.css']
})
export class GamemasterQuestEditComponent implements OnInit {

  isSolved: boolean;
  oldTasks: Task[];
  images: any[];

  constructor(
    private dialogRef: MatDialogRef<GamemasterQuestComponent>,
    @Inject(MAT_DIALOG_DATA) public quest: Quest,
    public questService: QuestService,
    public taskService: TaskService,
    private dialog: MatDialog) {
    this.oldTasks = this.quest.tasks.filter(() => true);
  }

  ngOnInit() {
    //TODO: MAT_DIALOG_DATA makes it hard to see where the data is coming from. Use Events/Services instead?
    if (this.quest.status === 'SOLVED') {
      this.isSolved = true;
    } else {
      this.isSolved = false;
    }
    this.loadImages();
  }

  calculateGoldAmountOfTasks(): number {
    return this.taskService.calculateGoldAmountOfTasks(this.quest.tasks);
  }

  calculateXpAmountOfTasks(): number {
    return this.taskService.calculateXpAmountOfTasks(this.quest.tasks);
  }

  addFreeTask() {
    this.dialog.open(GamemasterAddFreeTaskComponent, {data: [this.quest.world, this.quest.tasks]})
      .afterClosed().subscribe(result => {
      if (result) {
        this.quest.tasks.push(result);
      }
    });
  }

  suggestTasks() {
    this.dialog.open(GamemasterSuggestTasksComponent, {data: [this.quest.world, this.quest.tasks]}).afterClosed()
      .subscribe(result => {
        if (result) {
          this.quest.tasks = this.quest.tasks.concat(result);
        }
      });
  }

  removeTask(task: Task) {
    const taskIndex = this.quest.tasks.indexOf(task);
    this.quest.tasks.splice(taskIndex, 1);
  }

  solveTask(task: Task) {
    this.taskService.solveTaskManually(task);
    const taskIndex = this.quest.tasks.indexOf(task);
    // TODO: get new status from backend
    this.quest.tasks[taskIndex].status='SOLVED';
  }

  solveAllTasks() {
    for (const index in this.quest.tasks) {
      this.taskService.solveTaskManually(this.quest.tasks[index]);
      this.quest.tasks[index].status='SOLVED';
    }
  }

  editQuest() {
    if (this.quest.title && this.quest.gold && this.quest.xp
      && this.quest.story && this.quest.image && this.quest.tasks.length !== 0) {
      const newAndDeselectedTasks = this.taskService.identifyNewAndDeselectedTasks(this.oldTasks, this.quest.tasks);
      const newTasks = newAndDeselectedTasks[0];
      const deselectedTasks = newAndDeselectedTasks[1];
      this.questService.updateQuest(this.quest).then(() => {
        const promiseArray = [];
        newTasks.forEach((value, index) => {
          const addTaskToQuest = this.taskService.addToQuest(value, this.quest);
          promiseArray.push(addTaskToQuest);
        });
        deselectedTasks.forEach((value, index) => {
          const removeTaskFromQuest = this.taskService.deleteFromQuest(value);
          promiseArray.push(removeTaskFromQuest);
        });
        return Promise.all(promiseArray)
      }).then(() => {
        this.dialogRef.close(true);
      })
    }
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
