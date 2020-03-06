import { Task } from './../../../../Interfaces/Task';
import { Component, OnInit, Input, Inject } from '@angular/core';
import { Participation } from 'app/Interfaces/Participation';
import { User } from 'app/Interfaces/User';
import { Quest } from 'app/Interfaces/Quest';
import { UserService } from 'app/services/user.service';
import { TaskService } from 'app/services/task.service';
import { QuestService } from 'app/services/quest.service';
import { SonarQubeService } from 'app/services/sonar-qube.service';
import { WorldService } from 'app/services/world.service';
import { World } from 'app/Interfaces/World';

@Component({
  selector: 'app-tasklog',
  templateUrl: './tasklog.component.html',
  styleUrls: ['./tasklog.component.css']
})
export class TasklogComponent implements OnInit {
  @Input()
  public taskList: Task[];
  @Input()
  public quest: Quest;

  user: User;
  currentWorld: World;

  constructor(
    private sonarQubeService: SonarQubeService,
    private worldService: WorldService,
    public userService: UserService,
    public taskService: TaskService,
    public questService: QuestService) {
  }

  ngOnInit() {
    this.worldService.currentWorld$.subscribe(world => this.currentWorld = world);
    this.userService.user$.subscribe(user => this.user = user);
  }

  participatingDeveloperIsLoggedInDeveloper(task: Task): Boolean {
    let result = false;
    if (this.user.username === task.participant) {
      result = true;
    }
    return result;
  }

  openIssue(task: Task) {
    this.sonarQubeService.getIssueLink(task.key, this.currentWorld)
      .then(link => window.open(link, '_blank'));
  }

  addParticipation(task: Task) {
    return this.taskService.addParticipation(task, this.quest)
      .then(() => {
        return this.questService.getQuest(this.quest.id);
      }).then((updatedQuest: Quest) => {
        this.taskList = updatedQuest.tasks
      });
  }

  removeParticipation(task: Task) {
    return this.taskService.removeParticipation(task)
      .then(() => {
        return this.questService.getQuest(this.quest.id)
      }).then((updatedQuest: Quest) => {
        this.taskList = updatedQuest.tasks
      });
  }
}
