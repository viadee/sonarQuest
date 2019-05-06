import {Injectable, NgModule} from '@angular/core';
import {UiDesignService} from "./ui-design.service";
import {Observable} from "rxjs";
import {UiDesign} from "../Interfaces/UiDesign";

@Injectable()
export class UiDesignServiceMock {

  getUiDesign(): Observable<UiDesign> {
    return new Observable<UiDesign>();
  }

  updateUiDesign(designName: String): Promise<UiDesign> {
    return new Promise<UiDesign>(() => {});
  }

}

@NgModule({
  declarations: [

  ],
  providers: [
    { provide: UiDesignService, useClass: UiDesignServiceMock }

  ],
  imports: [

  ],
  exports: [

  ]
})
export class UiDesignServiceTestingModule {

}
