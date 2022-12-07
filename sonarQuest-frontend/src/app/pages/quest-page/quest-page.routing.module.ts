import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {QuestPageComponent} from './quest-page.component';


const routes: Routes = [
  {
    path: '',
    component: QuestPageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class QuestPageRoutingModule {
}
