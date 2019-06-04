import { ReplaySubject } from 'rxjs/ReplaySubject';
import { WorldService } from './world.service';
import { User } from './../Interfaces/User';
import {Adventure} from './../Interfaces/Adventure';
import {Subject, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {Response} from '@angular/http';
import {environment} from '../../environments/environment';
import {Quest} from '../Interfaces/Quest';
import {World} from '../Interfaces/World';
import {HttpClient} from '@angular/common/http';
import {Task} from '../Interfaces/Task';
import {ParticipationService} from './participation.service';
import {TaskService} from './task.service';
import { UserService } from './user.service';

@Injectable()
export class QuestService {

  private questsSubject: Subject<Quest[]> = new ReplaySubject(1);
  quests$ = this.questsSubject.asObservable();

  currentWorld: World;
  user: User;

  constructor(private http: HttpClient,
              private participationService: ParticipationService,
              private taskService: TaskService,
              private userService: UserService,
              private worldService: WorldService) {
    userService.user$.subscribe(user => {this.user = user})

    this.worldService.currentWorld$.subscribe(world => {
      this.currentWorld = world;
      this.getQuestsForWorld(world);
    })
  }

  getQuestsForWorld(world: World): Observable<Quest[]> {
    this.http.get<Quest[]>(`${environment.endpoint}/quest/world/${world.id}`)
      .subscribe(
        result => this.questsSubject.next(result),
        err => this.questsSubject.error(err)
      );
    return this.questsSubject;
  }

  public getQuest(id: number): Promise<Quest> {
    let quest: Quest;
    return this.http.get<Quest>(`${environment.endpoint}/quest/${id}`)
      .toPromise().then(q => {
        quest = q;
        return this.participationService.getParticipations(quest)
      }).then(participations => {
        quest.participations = participations;
        return this.taskService.getTasksForQuest(quest)
      }).then(tasks => {
        quest.tasks = tasks;
        return quest;
      });
  }

  createQuest(quest: any): Promise<Quest> {
    return this.http.post<Quest>(`${environment.endpoint}/quest`, quest)
      .toPromise()
      .catch(this.handleError);
  }

  updateQuest(quest: Quest): Promise<any> {
    return this.http.put<Quest>(`${environment.endpoint}/quest/${quest.id}`, quest)
      .toPromise()
      .catch(this.handleError);
  }

  deleteQuest(quest: Quest): Promise<any> {
    return this.http.delete(`${environment.endpoint}/quest/${quest.id}`)
      .toPromise()
  }

  addToWorld(quest: any, world: any): Promise<any> {
    return this.http.post(`${environment.endpoint}/quest/${quest.id}/addWorld/${world.id}`, null)
      .toPromise()
      .catch(this.handleError);
  }

  suggestTasksWithApproxXpAmountForWorld(world: World, xp: number): Promise<Task[]> {
    return this.http.get<Task[]>(`${environment.endpoint}/quest/suggestTasksForQuestByXpAmount/${world.id}/${xp}`)
      .toPromise()
      .catch(this.handleError);
  }

  suggestTasksWithApproxGoldAmountForWorld(world: World, gold: number): Promise<Task[]> {
    return this.http.get<Task[]>(`${environment.endpoint}/quest/suggestTasksForQuestByGoldAmount/${world.id}/${gold}`)
      .toPromise()
      .catch(this.handleError);
  }

  getFreeQuestsForWorld(world: World): Promise<any> {
    return this.http.get(`${environment.endpoint}/quest/getAllFreeForWorld/${world.id}`)
      .toPromise()
      .catch(this.handleError);
  }

  getAllParticipatedQuestsForWorldAndUser(world: World) {
    return this.getAllQuestsForWorldAndUser(world).then(quests => quests[0])
  }

  getAllAvailableQuestsForWorldAndUser(world: World) {
    return this.getAllQuestsForWorldAndUser(world).then(quests => quests[1])
  }

  refreshQuests(world: World) {
    this.getQuestsForWorld(world);
  }

  private getAllQuestsForWorldAndUser(world: World) {
    return this.http.get(`${environment.endpoint}/quest/getAllQuestsForWorldAndUser/${world.id}`)
      .toPromise()
      .catch(this.handleError);
  }

  private handleError(error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Promise.reject(errMsg);
  }

  identifyNewTasks(oldQuests: Quest[], currentQuests: Quest[]): Quest[] {
    return this.questDifference(currentQuests, oldQuests);
  }

  identifyDeselectedTasks(oldQuests: Quest[], currentQuests: Quest[]): Quest[] {
    return this.questDifference(oldQuests, currentQuests);
  }

  private questDifference(list1: Quest[], list2: Quest[]) {
    return list1.filter(x => !list2.includes(x));
  }

  calculateGoldAmountOfQuests(quests: Quest[]): number {
    return quests.map(quest => quest.gold).reduce(function (a, b) {
      return a + b;
    }, 0);
  }

  calculateXpAmountOfQuests(quests: Quest[]): number {
    return quests.map(quest => quest.xp).reduce(function (a, b) {
      return a + b;
    }, 0);
  }

  addToAdventure(quest: Quest, adventure: Adventure): Promise<any> {
    return this.http.post(`${environment.endpoint}/quest/${quest.id}/addAdventure/${adventure.id}`, null)
      .toPromise()
      .catch(this.handleError);
  }

  deleteFromAdventure(quest: any): Promise<any> {
    return this.http.delete(`${environment.endpoint}/quest/${quest.id}/removeAdventure`)
      .toPromise()
  }

}
