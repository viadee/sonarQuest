import {Participation} from './../../../../../../Interfaces/Participation';
import {QuestService} from './../../../../../../services/quest.service';
import {TaskService} from './../../../../../../services/task.service';
import {MAT_DIALOG_DATA} from '@angular/material';
import {Component, OnInit, Inject} from '@angular/core';
import {Quest} from '../../../../../../Interfaces/Quest';
import {World} from '../../../../../../Interfaces/World';
import {WorldService} from '../../../../../../services/world.service';
import {SonarCubeService} from '../../../../../../services/sonar-cube.service';
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

  constructor(
    private sonarCubeService: SonarCubeService,
    private worldService: WorldService,
    @Inject(MAT_DIALOG_DATA) public quest: Quest,
    public userService: UserService,
    public taskService: TaskService,
    public questService: QuestService
  ) {
  }

  ngOnInit() {
    this.tasks = this.quest.tasks;
    this.currentWorld = this.worldService.getCurrentWorld();
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
        if (this.userService.getUser().id === participation.user.id) {
          result = true;
        }
      }
    });

    return result;
  }

  openIssue(task: Task) {
    this.sonarCubeService.getIssueLink(task.key, this.currentWorld)
      .then(link => window.open(link, '_blank'));
  }

}
