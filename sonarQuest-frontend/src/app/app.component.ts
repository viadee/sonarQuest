import { UiDesignService } from './services/ui-design.service';
import { MatDialog } from '@angular/material';
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { TdMediaService } from '@covalent/core';
import { Router } from '@angular/router';
import { WorldService } from './services/world.service';
import { World } from './Interfaces/World';
import { TranslateService } from '@ngx-translate/core';
import { UiDesign } from './Interfaces/UiDesign';
import { AuthenticationService } from './login/authentication.service';
import { LoginComponent } from './login/login.component';
import { UserService } from './services/user.service';
import { User } from './Interfaces/User';
import { PermissionService } from './services/permission.service';
import { RoutingUrls } from './app-routing/routing-urls';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, AfterViewInit {

  public currentWorld: World = null;
  public worlds: World[];
  public pageNames: any;
  public selected: World;
  public user: User = null;
  private ui: UiDesign = null;

  public myAvatarUrl = RoutingUrls.myAvatar;
  public adventuresUrl = RoutingUrls.adventures;
  public questsUrl = RoutingUrls.quests;
  public marketplaceUrl = RoutingUrls.marketplace;
  public gamemasterUrl = RoutingUrls.gamemaster;
  public adminUrl = RoutingUrls.admin;
  public eventUrl = RoutingUrls.events;

  public isWorldSelectVisible: boolean;
  public isMyAvatarVisible: boolean;
  public isAdventuresVisible: boolean;
  public isQuestsVisible: boolean;
  public isMarketplaceVisible: boolean;
  public isGamemasterVisible: boolean;
  public isAdminVisible: boolean;
  public isEventVisible: boolean;

  private body = <HTMLScriptElement><any>document.getElementsByTagName('body')[0];


  constructor(
    private uiDesignService: UiDesignService,
    public media: TdMediaService,
    public router: Router,
    public worldService: WorldService,
    public translate: TranslateService,
    private dialog: MatDialog,
    private authService: AuthenticationService,
    private permissionService: PermissionService,
    private userService: UserService) {


    translate.setDefaultLang('en'); // Fallback language when a translation isn't found in the current language.
    translate.use(translate.getBrowserLang()); // The lang to use. If the lang isn't available, it will use the current loader to get them.
  }

  protected login(): void {
    this.dialog.open(LoginComponent, { panelClass: 'dialog-sexy', width: '500px' }).afterClosed()
      .subscribe(() => this.setDesign());
  }

  protected logout(): void {
    this.router.navigateByUrl('/');
    this.authService.logout();
    this.userService.loadUser();
    this.currentWorld = null;
    this.selected = null;
    this.worlds = null;
    this.user = null;
    this.ui = null;
    this.updateMenu(false);
    this.setBackground();
  }

  ngOnInit() {
    this.userService.onUserChange().subscribe(() => {
      if (this.userService.getUser()) {
        this.user = this.userService.getUser();
        this.updateMenu();
        this.susbcribeWorlds();
        this.setDesign();
        this.updateWorldsFromCurrentUser();
      }
    });
    this.setPreDesign();
    this.setBackground();
    this.userService.loadUser();
  }

  private updateMenu(enable: boolean = true) {
    if (enable) {
      this.permissionService.loadPermissions().then(() => {
        this.updateMenuDirectly();
      });
    } else {
      this.updateMenuDirectly(false);
    }
  }

  private updateMenuDirectly(enable: boolean = true) {
    this.isWorldSelectVisible = enable;
    this.isMyAvatarVisible = enable && this.permissionService.isUrlVisible(RoutingUrls.myAvatar);
    this.isAdventuresVisible = enable && this.permissionService.isUrlVisible(RoutingUrls.adventures);
    this.isQuestsVisible = enable && this.permissionService.isUrlVisible(RoutingUrls.quests);
    this.isMarketplaceVisible = enable && this.permissionService.isUrlVisible(RoutingUrls.marketplace);
    this.isGamemasterVisible = enable && this.permissionService.isUrlVisible(RoutingUrls.gamemaster);
    this.isAdminVisible = enable && this.permissionService.isUrlVisible(RoutingUrls.admin);
    this.isEventVisible = enable && this.permissionService.isUrlVisible(RoutingUrls.events);
  }


  private susbcribeWorlds() {
    this.worldService.currentWorld$.subscribe(world => {
      if (world) this.currentWorld = world;
      this.setBackground();
    })
    this.worldService.worlds$.subscribe(worlds => {
      if(this.currentWorld == null){
        this.currentWorld = worlds[0]
        this.setBackground();
      }
      this.worlds = worlds;
    })
  }

  protected updateWorldsFromCurrentUser(): void {
    this.worldService.getWorlds();
  }

  private setBackground() {
    if (this.currentWorld && this.user) {
      this.changebackground(this.currentWorld.image);
    } else if (!this.currentWorld && this.user) {
      this.changebackground("");
    } else if (!this.currentWorld && !this.user) {
      this.changebackground("bg13");
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
        case '/events':
          return this.pageNames.EVENT;
        default:
          return '';
      }
    } else {
      return ''
    }
  }

  updateWorld(world: World) {
    this.worldService.setCurrentWorld(world);
  }

  changebackground(image: string) {
    if (image != "") {
      this.body.style.backgroundImage = 'url("/assets/images/background/' + image + '.jpg")';
    } else {
      this.body.style.backgroundImage = 'url("")';
    }
    this.addClass(this.body, "background-image");
  }

  setDesign() {
    if (this.user) {
      this.uiDesignService.getUiDesign().subscribe(ui => {
        this.ui = ui;
        this.body.className = '';
        this.addClass(this.body, this.ui.name);
        this.addClass(this.body, "background-image");
      });
    }
  }

  setPreDesign() {
    this.toggleDesign();
  }

  toggleDesign() {
    const dark  = 'dark';
    const light = 'light';

    if (this.hasClass(this.body, light)) { // If light is choosen, toggle to dark
      this.body.className = this.removeSubString(this.body.className, light);
      this.addClass(this.body, dark);
      this.uiDesignService.updateUiDesign(dark);
    } else if (this.hasClass(this.body, dark)) { // If dark is choosen, toggle to light
      this.body.className = this.removeSubString(this.body.className, dark);
      this.addClass(this.body, light);
      this.uiDesignService.updateUiDesign(light);
    } else { // If no design is choosen
      this.addClass(this.body, light);
    }
    this.addClass(this.body, "background-image");
  }

  hasClass(element: HTMLScriptElement, cssClass: string): Boolean {
    return element.classList.contains(cssClass);
  }

  removeSubString(fullString: string, removeString: string): string {
    const newString = fullString.replace(removeString, '');
    return newString.replace('  ', ' ');
  }

  addClass(element, cssClass) {
    if (!this.hasClass(element, cssClass)) {
      element.className += ' ' + cssClass + ' ';
    }

    return element;
  }
}
