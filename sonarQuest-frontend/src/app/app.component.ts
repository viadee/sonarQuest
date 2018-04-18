import { MatDialog } from '@angular/material';
import { ChooseCurrentWorldComponent } from './components/choose-current-world/choose-current-world/choose-current-world.component';
import { isUndefined } from 'util';
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
  public currentWorld: World;
  public worlds: World[];
  public pageNames: any;
  public selected

  constructor(public media: TdMediaService,
    public router: Router,
    public developerService: DeveloperService,
    public worldService: WorldService,
    public translate: TranslateService,
    private dialog: MatDialog) {

    translate.setDefaultLang('en'); // this language will be used as a fallback when a translation isn't found in the current language
    translate.use('en'); // the lang to use, if the lang isn't available, it will use the current loader to get them    ,


    this.developerService.avatar$.subscribe(developer => {
      this.developer = developer
    })

    this.worldService.worlds$.subscribe(worlds => {
      this.worlds = worlds
      this.setSelected();
    })

    this.worldService.currentWorld$.subscribe(world => {
      if (world && !isUndefined(world.id)) {
        this.currentWorld = world;
        this.setSelected();
      } else {
        this.dialog.open(ChooseCurrentWorldComponent, {panelClass: "dialog-sexy", width: "500px" }).afterClosed().subscribe()
      }
    })

    this.developerService.getMyAvatar()
    
  }

  setSelected() {
    if (this.worlds && this.currentWorld) {
      this.selected = this.worlds.filter(world => { return (world.name == this.currentWorld.name) })[0]
    }
  }

  ngAfterViewInit() {
    this.media.broadcast();
    this.translate.get("APP_COMPONENT").subscribe((page_names) => {
      this.pageNames = page_names;
    })
  }


  determinePageTitle(url: string): string {
    if (this.pageNames) {
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
    } else {
      return ""
    }
  }


  updateWorld(world: World) {
    this.developerService.updateCurrentWorldToDeveloper(world, this.developer)
  }

}
