import { Component, OnInit, Input } from '@angular/core';
import { Monster } from 'app/Interfaces/Monster';

@Component({
  selector: 'app-monster-stage',
  templateUrl: './monster-stage.component.html',
  styleUrls: ['./monster-stage.component.css']
})
export class MonsterStageComponent implements OnInit {

  @Input()
  public monster: Monster;

  public monsterHealthProgress: number;
  public monsterProgressStyleWidth: string;

  constructor() { }

  ngOnInit() {
    this.monsterHealthProgress = this.monster.progress;
    this.monsterProgressStyleWidth = this.monsterHealthProgress + '%';
  }



}
