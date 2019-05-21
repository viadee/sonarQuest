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
  public level: number = 0;
  public maxXp: number = 0;
  public minXpForLevel2 = 10;
  public imageToShow: any = "";
  public user: User;

  constructor(private userService: UserService,
              private dialog: MatDialog,
              private imageService: ImageService) {
  }

  ngOnInit() {
    this.userService.user$.subscribe(user =>{
      this.user = user;
      this.level = (this.user.level == undefined ? 1 : this.user.level.levelNumber);
      this.maxXp = (this.level > 1 ? this.user.level.maxXp : this.minXpForLevel2);
      this.xpPercent();      
      this.getAvatar();
    })
  }

  private getAvatar() {
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
    if (this.user.level != null && this.user.level.maxXp > 0 && this.user.xp > 0) {
      this.XPpercent = 100 / this.user.level.maxXp * this.user.xp;
    } else {
      this.XPpercent = 0;
    }
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
