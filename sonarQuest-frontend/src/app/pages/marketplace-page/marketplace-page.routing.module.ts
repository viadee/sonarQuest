import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MarketplacePageComponent} from './marketplace-page.component';


const routes: Routes = [
  {
    path: '',
    component: MarketplacePageComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MarketplacePageRoutingModule {
}
