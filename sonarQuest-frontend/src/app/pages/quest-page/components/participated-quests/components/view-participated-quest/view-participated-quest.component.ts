import {Participation} from '../../../../../../Interfaces/Participation';
import {QuestService} from '../../../../../../services/quest.service';
import {TaskService} from '../../../../../../services/task.service';
import {MAT_DIALOG_DATA} from '@angular/material';
import {Component, Inject, OnInit} from '@angular/core';
import {Quest} from '../../../../../../Interfaces/Quest';
import {World} from '../../../../../../Interfaces/World';
import {WorldService} from '../../../../../../services/world.service';
import {SonarQubeService} from '../../../../../../services/sonar-qube.service';
import {User} from '../../../../../../Interfaces/User';
import {UserService} from '../../../../../../services/user.service';
import {Task} from '../../../../../../Interfaces/Task';

@Component({
  selector: 'app-view-participated-quest',
  templateUrl: './view-participated-quest.component.html',
  styleUrls: ['./view-participated-quest.component.css']
})
export class ViewParticipatedQuestComponent implements OnInit {

  currentWorld: World;
  tasks: Task[];
  user: User;

  constructor(
    private sonarQubeService: SonarQubeService,
    private worldService: WorldService,
    @Inject(MAT_DIALOG_DATA) public quest: Quest,
    public userService: UserService,
    public taskService: TaskService,
    public questService: QuestService
  ) {
  }

  ngOnInit() {
    this.tasks = this.quest.tasks;

    this.worldService.currentWorld$.subscribe(world => this.currentWorld = world)
    this.userService.user$.subscribe(user => this.user = user)
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

}
