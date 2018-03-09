import { TranslateService } from '@ngx-translate/core';
import { Component, OnInit } from '@angular/core';
import {
  ITdDataTableColumn, ITdDataTableSortChangeEvent, TdDataTableService,
  TdDataTableSortingOrder,
  IPageChangeEvent
} from "@covalent/core";

import { AdventureService } from '../../services/adventure.service';
import { DeveloperService } from '../../services/developer.service';
import { WorldService } from '../../services/world.service';
import { Adventure } from '../../Interfaces/Adventure';
import { Developer } from '../../Interfaces/Developer';
import { World } from '../../Interfaces/World';

@Component({
  selector: 'app-adventure-page',
  templateUrl: './adventure-page.component.html',
  styleUrls: ['./adventure-page.component.css']
})
export class AdventurePageComponent implements OnInit {

  columns: ITdDataTableColumn[];
  developer: Developer;
  world: World;

  public adventures: Adventure[];
  public myAdventures: Adventure[];
  public availableAdventures: Adventure[];
  constructor(
    public adventureService: AdventureService,
    public worldService: WorldService,
    public developerService: DeveloperService,
    public translateService: TranslateService) { }



  ngOnInit() {
    this.translateService.get("TABLE.COLUMNS").subscribe((col_names) => {
      this.columns=[
        { name: 'title', label: col_names.TITLE, width: 200 },
        { name: 'gold', label: col_names.GOLD},
        { name: 'xp', label: col_names.XP },
        { name: 'story', label: col_names.STORY },
        { name: 'status', label: col_names.STATUS },
        { name: 'edit', label: '' }]
    });
    this.developerService.getMyAvatar().then(developer => {
      this.developer = developer;
      this.world = this.worldService.getCurrentWorld();
      return this.loadAdventures(this.world, this.developer)
    });
  }

  loadAdventures(world, developer) {
    return this.adventureService.getAdventuresByDeveloperAndWorld(world, developer).subscribe(
      result => {
        this.myAdventures = result[0];
        this.availableAdventures = result[1];
      }
    );
  }

  leaveAdventure(row) {
    this.adventureService.leaveAdventure(row, this.developer)
  }

  joinAdventure(row) {
    this.adventureService.joinAdventure(row, this.developer)
  }
}
