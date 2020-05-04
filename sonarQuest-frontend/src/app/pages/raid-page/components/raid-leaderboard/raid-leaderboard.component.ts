import { Component, OnInit, Input } from '@angular/core';
import { RaidLeaderboard } from 'app/Interfaces/RaidLeaderboard';
import { ITdDataTableColumn } from '@covalent/core';
import { UserService } from 'app/services/user.service';
import { ImageService } from 'app/services/image.service';

@Component({
  selector: 'app-raid-leaderboard',
  templateUrl: './raid-leaderboard.component.html',
  styleUrls: ['./raid-leaderboard.component.css']
})
export class RaidLeaderboardComponent implements OnInit {
  @Input()
  raidLeaderboard: RaidLeaderboard[];

  columns: ITdDataTableColumn[] = [
    { name: 'user.username', label: 'Name' },
    { name: 'scoreXp', label: 'XP' },
    { name: 'scoreGold', label: 'Gold' }
  ]

  constructor() { }

  ngOnInit() {
  }
}
