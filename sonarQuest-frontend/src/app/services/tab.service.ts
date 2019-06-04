import { Injectable } from '@angular/core';

@Injectable()
export class TabService {

  public gamemasterTab: number;
  public adminTab: number;

  constructor() {
  }

  
  setGamemasterTab(number: number){
    this.gamemasterTab = number;
  }

  setAdminTab(number: number){
    this.adminTab = number;
  }


}
