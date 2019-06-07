import { ParticipationService } from './../../../../services/participation.service';
import { Participation } from './../../../../Interfaces/Participation';
import {QuestService} from '../../../../services/quest.service';
import {TaskService} from '../../../../services/task.service';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Component, Inject, OnInit} from '@angular/core';
import {Quest} from '../../../../Interfaces/Quest';
import {World} from '../../../../Interfaces/World';
import {WorldService} from '../../../../services/world.service';
import {SonarQubeService} from '../../../../services/sonar-qube.service';
import {User} from '../../../../Interfaces/User';
import {UserService} from '../../../../services/user.service';
import {Task} from '../../../../Interfaces/Task';
import { EventDto } from 'app/Interfaces/EventDto';

@Component({
  selector: 'app-view-quest',
  templateUrl: './view-quest.component.html',
  styleUrls: ['./view-quest.component.css']
})
export class ViewQuestComponent implements OnInit {

  currentWorld: World;
  tasks: Task[];
  user: User;
  quest: Quest;
  participated: Boolean = true;

  constructor(
    
    private dialogRef: MatDialogRef<ViewQuestComponent>,
    private sonarQubeService: SonarQubeService,
    private worldService: WorldService,
    @Inject(MAT_DIALOG_DATA) public event: EventDto,
    public userService: UserService,
    public taskService: TaskService,
    public questService: QuestService,
    public participationService: ParticipationService

  ) {
    this.questService.getQuest(event.type_id).then(quest => {
      this.quest = quest
      
      this.tasks = this.quest.tasks;

      this.worldService.currentWorld$.subscribe(world => this.currentWorld = world)
      this.userService.user$.subscribe(user => {
        this.user = user
        this.participated = this.iAmParticipated();
      })
    })
  }

  ngOnInit() {
  }
  
  iAmParticipated(): Boolean{
    return this.quest.participants.includes(this.user.username);
  }

  addParticipation(task: Task) {
    //if (task.status != 'SOLVED') {
      return this.taskService.addParticipation(task, this.quest)
        .then(() => {
          return this.questService.getQuest(this.quest.id);
        }).then((updatedQuest: Quest) => {
          this.quest = updatedQuest;
          this.tasks = updatedQuest.tasks
        })
    //}
  }

  removeParticipation(task: Task) {
    //if (task.status != 'SOLVED') {
      return this.taskService.removeParticipation(task)
        .then(() => {
          return this.questService.getQuest(this.quest.id)
        }).then((updatedQuest: Quest) => {
          this.quest = updatedQuest;
          this.tasks = updatedQuest.tasks
        })
    //}
  }

  participatingDeveloper(task: Task): User {
    let user = null;
    this.quest.participations.forEach((participation) => {
      if (participation.tasks.map(partTask => partTask.id).includes(task.id)) {
        user = participation.user;
      }
    });

    return user;
  }

  participatingDeveloperIsLoggedInDeveloper(task: Task): Boolean {
    let result = false;

    const participations: Participation[] = this.quest.participations;
    participations.forEach((participation) => {
      if (participation.tasks.map(partTask => partTask.id).includes(task.id)) {
        if (this.user.id === participation.user.id) {
          result = true;
        }
      }
    });

    return result;
  }

  openIssue(task: Task) {
    this.sonarQubeService.getIssueLink(task.key, this.currentWorld)
      .then(link => window.open(link, '_blank'));
  }

  

  participateInQuest() {
    return this.participationService.createParticipation(this.quest)
      .then((msg) => {
        this.dialogRef.close();
      })
  }
}
