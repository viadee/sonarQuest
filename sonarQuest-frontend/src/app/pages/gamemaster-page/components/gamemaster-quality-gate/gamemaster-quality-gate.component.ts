import { QualityGateServiceService } from './../../../../services/quality-gate-service.service';
import { RaidModel } from './../../../../game-model/RaidModel';
import { Component, OnInit, Input } from '@angular/core';
import { Raid } from 'app/Interfaces/Raid';
import { Condition } from 'app/Interfaces/Condition';
import {TranslateService} from '@ngx-translate/core';
import {
  IPageChangeEvent,
  ITdDataTableColumn, ITdDataTableSortChangeEvent, TdDataTableService,
  TdDataTableSortingOrder
} from '@covalent/core';
import { WorldService } from 'app/services/world.service';
import { World } from 'app/Interfaces/World';
import { QualityGateRaid } from 'app/Interfaces/QualityGateRaid';
import { QualityGateRaidModel } from 'app/game-model/QualityGateRaidModel';
import { QualityGateRaidService } from 'app/services/quality-gate-raid.service';

@Component({
  selector: 'app-gamemaster-quality-gate',
  templateUrl: './gamemaster-quality-gate.component.html',
  styleUrls: ['./gamemaster-quality-gate.component.css']
})
export class GamemasterQualityGateComponent implements OnInit {
  raid: QualityGateRaid;
  conditions: Condition[];
  world: World;

  data: any[] = [];
  columns: ITdDataTableColumn[] = [
    {name: 'id', label: 'Id'},
    {name: 'title', label: 'Title', width: 200},
    {name: 'gold', label: 'Gold'},
    {name: 'xp', label: 'XP'},
    {name: 'status', label: 'Status'}
  ];

  filteredData: any[] = this.data;
  constructor(private worldService: WorldService, private qualityGateService: QualityGateRaidService) { }

  ngOnInit() {
    this.worldService.currentWorld$.subscribe(world => {
      this.world = world;
    });
    this.loadQualityGateRaid();
  }

  loadQualityGateRaid() {
    return this.qualityGateService.findByWorld(this.world.id).subscribe(raid => {
      this.raid = raid;
    });
  }

  newQualityGateRaid() {
    this.raid = new QualityGateRaidModel(this.world);
    this.updateQualityGateRaid();
  }

  updateQualityGateRaid() {
    this.qualityGateService.updateQualityGateRaid(this.raid).subscribe(raid => {
      this.raid = raid;
    });
  }
}
