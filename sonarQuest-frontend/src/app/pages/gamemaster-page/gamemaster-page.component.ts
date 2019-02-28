import {Component, OnInit} from '@angular/core';
import { RoutingUrls } from 'app/app-routing/routing-urls';
import { PermissionService } from 'app/services/permission.service';

@Component({
  selector: 'app-gamemaster-page',
  templateUrl: './gamemaster-page.component.html',
  styleUrls: ['./gamemaster-page.component.css']
})
export class GamemasterPageComponent implements OnInit {

  protected isMarketplaceVisible: boolean;
  selectedTab: number;

  constructor(private permissionService: PermissionService) {
    this.isMarketplaceVisible = this.permissionService.isUrlVisible(RoutingUrls.marketplace);
  }

  ngOnInit() {
  }

  onTabChange(tab: any) {
    this.selectedTab = tab.index;
  }

}
