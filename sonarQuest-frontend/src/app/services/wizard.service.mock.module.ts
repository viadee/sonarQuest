import {Injectable, NgModule} from '@angular/core';
import {Observable} from 'rxjs';
import {WizardService} from "./wizard.service";
import {Wizard} from "../Interfaces/Wizard";

@Injectable()
export class WizardServiceMock {

  public getWizardMessage(): Observable<Wizard> {
    return new Observable<Wizard>();
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: WizardService, useClass: WizardServiceMock }
  ],
  imports: [

  ],
  exports: [

  ]
})
export class WizardServiceTestingModule {

}
