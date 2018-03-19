import { Component } from '@angular/core';
import { TdMediaService } from '@covalent/core';
import { Router } from '@angular/router';
import { DeveloperService } from './services/developer.service';
import { Developer } from './Interfaces/Developer';
import { WorldService } from './services/world.service';
import { World } from './Interfaces/World';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  public developer: Developer;
  public worlds: World[];
  public selectedWorld: World;
  public pageNames: any;

  constructor(public media: TdMediaService,
     public router: Router,
      public developerService: DeveloperService,
       public worldService: WorldService, 
       public translate: TranslateService) {
       // this language will be used as a fallback when a translation isn't found in the current language
       translate.setDefaultLang('en');

       // the lang to use, if the lang isn't available, it will use the current loader to get them
      translate.use('en');
  }

  ngAfterViewInit(): void {
    this.media.broadcast();
    this.translate.get("APP_COMPONENT").subscribe((page_names)=>{
      this.pageNames = page_names;
    })  
    this.developerService.getMyAvatar().subscribe(developer => this.developer = developer);
    this.worldService.getWorlds().then(worlds => {
      this.worlds = worlds.filter(world => world.active === true)
      this.selectedWorld = worlds[0]
    });
  }

  determinePageTitle(url: string): string {
    if(this.pageNames){
      switch (url) {
        case '/start':
          return this.pageNames.STARTPAGE;
        case '/myAvatar':
          return this.pageNames.MY_AVATAR;
        case '/adventures':
          return this.pageNames.ADVENTURES;
        case '/quests':
          return this.pageNames.QUESTS;
        case '/marketplace':
          return this.pageNames.MARKETPLACE;
        case '/gamemaster':
          return this.pageNames.GAMEMASTER;
        case '/admin':
        return this.pageNames.ADMIN;
        default:
          return '';
      }
    }else{
      return ""
    }
    
  }
}
