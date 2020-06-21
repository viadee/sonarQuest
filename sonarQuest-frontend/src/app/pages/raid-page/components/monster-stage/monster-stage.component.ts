import { Component,OnChanges, OnInit, Input } from '@angular/core';
import { Monster } from 'app/Interfaces/Monster';

@Component({
  selector: 'app-monster-stage',
  templateUrl: './monster-stage.component.html',
  styleUrls: ['./monster-stage.component.css']
})
export class MonsterStageComponent implements OnChanges {

  @Input()
  public monster: Monster;

  public monsterHealthProgress: number;
  public monsterProgressStyleWidth: string;
  public monsterDamagedProgress: number;
  public monsterName: string;
  public monsterImage: string;

  constructor() { }

  ngOnChanges() {
    if (this.monster) {
      this.monsterImage = this.monster.Image;
      this.monsterName = this.monster.Name;
      this.monsterHealthProgress = this.monster.progress;
      this.monsterProgressStyleWidth = this.monsterHealthProgress + '%';
      this.monsterDamagedProgress = 100 - this.monsterHealthProgress;
    }
  }
}
