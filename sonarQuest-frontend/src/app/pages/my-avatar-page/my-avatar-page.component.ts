import {ImageService} from './../../services/image.service';
import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {AvatarEditComponent} from './components/my-avatar-edit/my-avatar-edit.component';
import {UserService} from '../../services/user.service';
import {User} from '../../Interfaces/User';

@Component({
  selector: 'app-my-avatar-page',
  templateUrl: './my-avatar-page.component.html',
  styleUrls: ['./my-avatar-page.component.css']
})
export class MyAvatarPageComponent implements OnInit {

  public XPpercent = 0;
  public level: number;
  protected user: User;
  imageToShow: any;

  constructor(private userService: UserService,
              private dialog: MatDialog,
              private imageService: ImageService) {
  }

  ngOnInit() {
    this.init();
    this.userService.onUserChange().subscribe(() => this.init());
  }

  private init() {
    if (this.userService.getUser()) {
      this.user = this.userService.getUser();
      this.getAvatar();
    }
  }

  private getAvatar() {
    this.level = this.user.level.level;
    this.xpPercent();
    if (this.user) {
      this.userService.getImage().subscribe((blob) => {
        this.imageService.createImageFromBlob(blob).subscribe(image => this.imageToShow = image);
      });
    }
  }

  protected createSkillsList(artefact: any) {
    const skillnames = artefact.skills.map(skill => skill.name);
    return skillnames.join(', ');
  }

  public xpPercent(): void {
    this.XPpercent = 100 / this.user.level.maxXp * this.user.xp;
    this.XPpercent.toFixed(2);
  }

  public editAvatar(): void {
    this.dialog.open(AvatarEditComponent, {panelClass: 'dialog-sexy', data: this.user, width: '500px'}).afterClosed().subscribe(
      result => {
        if (result) {
          this.getAvatar();
        }
      }
    );
  }
}
