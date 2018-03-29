import { Adventure } from './../Interfaces/Adventure';
import { Subject } from 'rxjs/Subject';
import { Developer } from './../Interfaces/Developer.d';
import { Task } from 'app/Interfaces/Task';
import { Injectable } from '@angular/core';
import {Http, RequestOptions, Response, Headers} from "@angular/http";
import {environment} from "../../environments/environment";
import {Quest} from "../Interfaces/Quest";
import {World} from "../Interfaces/World";
import {ReplaySubject} from "rxjs/ReplaySubject";
import {Observable} from "rxjs/Observable";

@Injectable()
export class QuestService {

  private questSubject;

  constructor(public http: Http) {
    this.questSubject = new Subject();
  }

  getQuestsForWorld(world: World): Observable<Quest[]> {
    this.http.get(`${environment.endpoint}/quest/world/${world.id}`)
      .map(this.extractData)
      .subscribe(
        result => this.questSubject.next(result),
        err => this.questSubject.error(err)
      );
    return this.questSubject;
  }

  getQuest(id: number): Promise<Quest> {
    return this.http.get(`${environment.endpoint}/quest/${id}`)
    .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }


  createQuest(quest: any): Promise<Quest> {
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${environment.endpoint}/quest/`, quest, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  updateQuest(quest: Quest): Promise<any>{
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.put(`${environment.endpoint}/quest/${quest.id}`, quest, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  deleteQuest(quest: Quest): Promise<any> {
    return this.http.delete(`${environment.endpoint}/quest/${quest.id}`)
      .toPromise()
  }

  addToWorld(quest:any, world: any): Promise<any>{
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${environment.endpoint}/quest/${quest.id}/addWorld/${world.id}`, null, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  suggestTasksWithApproxXpAmountForWorld(world: World, xp: number){
    return this.http.get(`${environment.endpoint}/quest/suggestTasksForQuestByXpAmount/${world.id}/${xp}`)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  suggestTasksWithApproxGoldAmountForWorld(world: World, gold: number){
    return this.http.get(`${environment.endpoint}/quest/suggestTasksForQuestByGoldAmount/${world.id}/${gold}`)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  getFreeQuestsForWorld(world:World): Promise<any> {
    return this.http.get(`${environment.endpoint}/quest/getAllFreeForWorld/${world.id}`)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  getAllParticipatedQuestsForWorldAndDeveloper(world: World, developer: Developer){
    return this.getAllQuestsForWorldAndDeveloper(world,developer).then(quests=>quests[0])
  }

  getAllAvailableQuestsForWorldAndDeveloper(world: World, developer: Developer){
    return this.getAllQuestsForWorldAndDeveloper(world,developer).then(quests=>quests[1])
  }

  refreshQuests(world: World){
    this.getQuestsForWorld(world);
  }

  private getAllQuestsForWorldAndDeveloper(world: World, developer: Developer){
    return this.http.get(`${environment.endpoint}/quest/getAllQuestsForWorldAndDeveloper/${world.id}/${developer.id}`)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  private extractData(res: Response) {
    const body = res.json();
    return body || {};
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

  
  identifyNewTasks(oldQuests: Quest[], currentQuests: Quest[]): Quest[]{
    return this.questDifference(currentQuests, oldQuests);
  }

  identifyDeselectedTasks(oldQuests: Quest[], currentQuests: Quest[]): Quest[]{
    return this.questDifference(oldQuests, currentQuests);
  }

  private questDifference(list1: Quest[], list2: Quest[]){
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
    let headers = new Headers({ 'Content-Type': 'application/json' });
    let options = new RequestOptions({ headers: headers });
    return this.http.post(`${environment.endpoint}/quest/${quest.id}/addAdventure/${adventure.id}`, null, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  deleteFromAdventure(quest: any): Promise<any> {
    return this.http.delete(`${environment.endpoint}/quest/${quest.id}/removeAdventure`)
      .toPromise()
  }

}
