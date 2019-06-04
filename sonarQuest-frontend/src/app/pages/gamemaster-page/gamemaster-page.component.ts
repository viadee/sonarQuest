import {Component, OnInit} from '@angular/core';
import { RoutingUrls } from 'app/app-routing/routing-urls';
import { PermissionService } from 'app/services/permission.service';
import { TabService } from 'app/services/tab.service';

@Component({
  selector: 'app-gamemaster-page',
  templateUrl: './gamemaster-page.component.html',
  styleUrls: ['./gamemaster-page.component.css']
})
export class GamemasterPageComponent implements OnInit {

  public isMarketplaceVisible: boolean;
  selectedTab: number;

  constructor(
    private permissionService: PermissionService,
    private tabService: TabService) {
    this.isMarketplaceVisible = this.permissionService.isUrlVisible(RoutingUrls.marketplace);
    this.selectedTab = this.tabService.gamemasterTab
  }

  ngOnInit() {
  }

  onTabChange(tab: any) {
    this.tabService.setGamemasterTab(tab.index)
  }

}
