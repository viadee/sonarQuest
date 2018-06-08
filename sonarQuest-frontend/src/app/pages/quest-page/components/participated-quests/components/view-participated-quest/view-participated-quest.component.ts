import {Developer} from './../../../../../../Interfaces/Developer.d';
import {Participation} from './../../../../../../Interfaces/Quest';
import {QuestService} from './../../../../../../services/quest.service';
import {TaskService} from './../../../../../../services/task.service';
import {DeveloperService} from './../../../../../../services/developer.service';
import {ParticipationService} from './../../../../../../services/participation.service';
import {MAT_DIALOG_DATA} from '@angular/material';
import {MatDialogRef} from '@angular/material';
import {AvailableQuestsComponent} from './../../../available-quests/available-quests.component';
import {Component, OnInit, Inject} from '@angular/core';
import {Quest, Task} from '../../../../../../Interfaces/Quest';
import {World} from '../../../../../../Interfaces/Developer';
import {WorldService} from '../../../../../../services/world.service';
import {SonarCubeService} from '../../../../../../services/sonar-cube.service';
import {User} from '../../../../../../Interfaces/User';
import {UserService} from '../../../../../../services/user.service';

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
    private sonarCubeService: SonarCubeService,
    private worldService: WorldService,
    private dialogRef: MatDialogRef<AvailableQuestsComponent>,
    @Inject(MAT_DIALOG_DATA) public quest: Quest,
    public participationService: ParticipationService,
    public userService: UserService,
    public taskService: TaskService,
    public questService: QuestService
  ) {
  }

  ngOnInit() {
    this.tasks = this.quest.tasks;
    this.userService.getUser().subscribe(user => this.user = user);
    this.worldService.currentWorld$.subscribe(w => this.currentWorld = w);
  }

  addParticipation(task: Task) {
    return this.taskService.addParticipation(task, this.quest)
      .then(() => {
        return this.questService.getQuest(this.quest.id);
      }).then((updatedQuest: Quest) => {
        this.quest = updatedQuest;
        this.tasks = updatedQuest.tasks
      })
  }

  removeParticipation(task: Task) {
    return this.taskService.removeParticipation(task)
      .then(() => {
        return this.questService.getQuest(this.quest.id)
      }).then((updatedQuest: Quest) => {
        this.quest = updatedQuest;
        this.tasks = updatedQuest.tasks
      })
  }

  participatingDeveloper(task: Task): User {
    let user = null;
    if (this.user) {
      const participations: Participation[] = this.quest.participations
      participations.forEach((participation) => {
        if (participation.tasks.map(partTask => partTask.id).includes(task.id)) {
          user = participation.user;
        }
      })
    }
    return user;
  }

  participatingDeveloperIsLoggedInDeveloper(task: Task): Boolean {
    let result = false;
    if (this.user) {
      const participations: Participation[] = this.quest.participations
      participations.forEach((participation) => {
        if (participation.tasks.map(partTask => partTask.id).includes(task.id)) {
          if (this.user.id === participation.user.id) {
            result = true;
          }
        }
      })
    }
    return result;
  }

  openIssue(task: Task) {
    this.sonarCubeService.getIssueLink(task.key, this.currentWorld.name).subscribe(link => window.open(link, '_blank'));
  }

}
