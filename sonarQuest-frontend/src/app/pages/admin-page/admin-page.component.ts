import { Component, OnInit } from '@angular/core';
import { Wizard } from '../../Interfaces/Wizard';
import { WizardService } from '../../services/wizard.service';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {

  wizard$: Observable<Wizard>;

  constructor(private wizardService: WizardService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(() => {
      this.wizard$ = this.wizardService.getWizardMessage();
    });
  }

}
