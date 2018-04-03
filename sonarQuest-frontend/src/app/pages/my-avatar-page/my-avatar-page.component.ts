import {Component, OnInit} from '@angular/core';
import {Developer} from '../../Interfaces/Developer';
import {DeveloperService} from '../../services/developer.service';

@Component({
  selector: 'app-my-avatar-page',
  templateUrl: './my-avatar-page.component.html',
  styleUrls: ['./my-avatar-page.component.css']
})
export class MyAvatarPageComponent implements OnInit {

  public developer: Developer;
  public XPpercent: number = 0;

  constructor(public developerService: DeveloperService) {
  }

  ngOnInit() {
    this.developerService.avatar$.subscribe(developer => {
      this.developer = developer;
      this.xpPercent();
    })
  }

  createSkillsList(artefact: any) {
    const skillnames = artefact.skills.map(skill => skill.name);
    return skillnames.join(', ');
  }

  public xpPercent(): void {
    this.XPpercent = 100 / this.developer.level.max * this.developer.xp;
    this.XPpercent.toFixed(2);
  }

  public editAvatar(): void {

  }
}
