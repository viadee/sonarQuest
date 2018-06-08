import {UiDesignService} from './services/ui-design.service';
import {MatDialog} from '@angular/material';
import {ChooseCurrentWorldComponent} from './components/choose-current-world/choose-current-world/choose-current-world.component';
import {isUndefined} from 'util';
import {AfterViewInit, Component} from '@angular/core';
import {TdMediaService} from '@covalent/core';
import {Router} from '@angular/router';
import {WorldService} from './services/world.service';
import {World} from './Interfaces/World';
import {TranslateService} from '@ngx-translate/core';
import {UiDesign} from './Interfaces/UiDesign';
import {User} from './Interfaces/User';
import {AuthenticationService} from './login/authentication.service';
import {LoginComponent} from './login/login.component';
import {UserService} from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements AfterViewInit {

  public user: User;
  public currentWorld: World;
  public worlds: World[];
  public pageNames: any;
  public selected;
  private ui: UiDesign;

  constructor(
    private uiDesignService: UiDesignService,
    public media: TdMediaService,
    public router: Router,
    public worldService: WorldService,
    public translate: TranslateService,
    private dialog: MatDialog,
    private authService: AuthenticationService,
    private userService: UserService) {

    translate.setDefaultLang('en'); // Fallback language when a translation isn't found in the current language.
    translate.use(translate.getBrowserLang()); // The lang to use. If the lang isn't available, it will use the current loader to get them.
  }

  protected login(): void {
    const dialogRef = this.dialog.open(LoginComponent, {panelClass: 'dialog-sexy', width: '500px'});
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.authService.login(result.username, result.password)
          .subscribe(() => {
            this.userService.getUser().subscribe(user => {
              this.user = user;
              this.init();
            });
          });
      }
    });
  }

  protected logout(): void {
    this.user = null;
    this.authService.logout();
    // TODO Clear all
  }

  private init() {
    this.worldService.loadWorlds();
    this.worldService.worlds$.subscribe(worlds => {
      this.worlds = worlds;
      this.setSelected();
    });

    this.worldService.currentWorld$.subscribe(world => {
      if (world && !isUndefined(world.id)) {
        const image = world.image || 'bg01';
        this.changebackground(image);

        this.setDesign();
        this.currentWorld = world;
        this.setSelected();
      } else {
        this.dialog.open(ChooseCurrentWorldComponent, {panelClass: 'dialog-sexy', width: '500px'}).afterClosed().subscribe();
      }
    });
  }

  setSelected() {
    if (this.worlds && this.currentWorld) {
      this.selected = this.worlds.filter(world => {
        return (world.name === this.currentWorld.name);
      })[0]
    }
  }

  ngAfterViewInit() {
    this.media.broadcast();
    this.translate.get('APP_COMPONENT').subscribe((page_names) => {
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
      return ''
    }
  }


  updateWorld(world: World) {
    console.log('World changed. Currently this has no effect.');
  }

  changebackground(image: string) {
    const x = (<HTMLScriptElement><any>document.getElementsByClassName('background-image')[0]);
    x.style.backgroundImage = 'url("/assets/images/background/' + image + '.jpg")';
  }

  setDesign() {
    this.uiDesignService.ui$.subscribe(ui => {
      if (ui) {
        this.ui = ui;
      } else {
        this.ui.name = '';
      }

      const body = <HTMLScriptElement><any>document.getElementsByTagName('body')[0];
      const className = body.className;
      body.className = className + ' ' + this.ui.name;
    })
  }

  toggleDesign() {
    const dark = 'dark';
    const light = 'light';

    const body = <HTMLScriptElement><any>document.getElementsByTagName('body')[0];
    const body_light = <HTMLScriptElement><any>document.getElementsByClassName(light)[0];

    if (body_light) {
      body.className = this.removeSubString(body.className, light) + ' ' + dark;
      this.uiDesignService.updateUiDesign(dark);
    } else {
      body.className = this.removeSubString(body.className, dark) + ' ' + light;
      this.uiDesignService.updateUiDesign(light);
    }

  }

  removeSubString(fullString: string, removeString: string): string {
    let newString = fullString.replace(removeString, '');
    newString = newString.replace('  ', ' ');
    return newString;
  }


}
