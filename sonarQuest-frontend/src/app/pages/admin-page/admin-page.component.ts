import { Component, OnInit } from '@angular/core';
import { Wizard } from '../../Interfaces/Wizard';
import { WizardService } from '../../services/wizard.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { TabService } from 'app/services/tab.service';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {

  wizard$: Observable<Wizard>;
  selectedTab: number;

  constructor(
    private wizardService: WizardService, 
    private route: ActivatedRoute,
    private tabService: TabService) { 
      this.selectedTab = this.tabService.adminTab
    }

  ngOnInit() {
    this.route.params.subscribe(() => {
      this.wizard$ = this.wizardService.getWizardMessage();
    });
  }
  
  
  onTabChange(tab: any) {
    this.tabService.setAdminTab(tab.index)
  }

}
