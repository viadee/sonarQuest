import {AfterViewInit, Component, OnInit} from '@angular/core';
import {World} from "../../Interfaces/World";
import {User} from "../../Interfaces/User";
import {UiDesign} from "../../Interfaces/UiDesign";
import {RoutingUrls} from "../../app-routing/routing-urls";
import {UiDesignService} from "../../services/ui-design.service";
import {TdMediaService} from "@covalent/core";
import {Router} from "@angular/router";
import {WorldService} from "../../services/world.service";
import {TranslateService} from "@ngx-translate/core";
import {AuthenticationService} from "../../authentication/authentication.service";
import {PermissionService} from "../../services/permission.service";
import {UserService} from "../../services/user.service";

@Component({
  selector: 'app-main-layout',
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.css']
})
export class MainLayoutComponent implements OnInit, AfterViewInit {

  public currentWorld: World = null;
  public worlds: World[];
  public pageNames: any;
  public selected: World;
  public user: User = null;
  private ui: UiDesign = null;
  private clickToggleDesignButton = false;

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

  public body = <HTMLScriptElement><any>document.getElementsByTagName('body')[0];


  constructor(
    private uiDesignService: UiDesignService,
    public media: TdMediaService,
    public router: Router,
    public worldService: WorldService,
    public translate: TranslateService,
    private authService: AuthenticationService,
    private permissionService: PermissionService,
    private userService: UserService) {
  }

  protected logout(): void {
    this.authService.logout();
    this.router.navigate([RoutingUrls.login]);
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
      this.changeBackground(this.currentWorld.image);
    } else if (!this.currentWorld && this.user) {
      this.changeBackground("");
    } else if (!this.currentWorld && !this.user) {
      this.changeBackground("bg13");
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

  changeBackground(image: string) {
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

  clickToggleDesign(){
    this.clickToggleDesignButton = true;
    this.toggleDesign()
  }

  toggleDesign() {
    const dark  = 'dark';
    const light = 'light';

    if (this.hasClass(this.body, light)) { // If light is choosen, change to dark
      this.body.className = this.removeSubString(this.body.className, light);
      this.addClass(this.body, dark);
      if (this.clickToggleDesignButton) {
        this.uiDesignService.updateUiDesign(dark); 
        this.clickToggleDesignButton = false;
      }
    } else if (this.hasClass(this.body, dark)) { // If dark is choosen, change to light
      this.body.className = this.removeSubString(this.body.className, dark);
      this.addClass(this.body, light);
      if (this.clickToggleDesignButton) {
        this.uiDesignService.updateUiDesign(light); 
        this.clickToggleDesignButton = false;
      }
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
    element.className.replace('  ', ' ');

    return element;
  }
}
