import { Component, OnInit, Input } from '@angular/core';
import { RaidLeaderboard } from 'app/Interfaces/RaidLeaderboard';
import { ITdDataTableColumn } from '@covalent/core';

@Component({
  selector: 'app-raid-highscore',
  templateUrl: './raid-highscore.component.html',
  styleUrls: ['./raid-highscore.component.css']
})
export class RaidHighscoreComponent implements OnInit {

  @Input()
  raidHighScores: RaidLeaderboard[];

  // TODO use translateservice
  columns: ITdDataTableColumn[] = [
    { name: 'user.username', label: 'Name' },
    { name: 'scorePoints', label: 'Anzahl Aufträge' },
    { name: 'scoreXp', label: 'XP' },
    { name: 'scoreGold', label: 'Gold' }
  ]

  constructor() { }

  ngOnInit() {
    console.log(this.raidHighScores);
  }

}
