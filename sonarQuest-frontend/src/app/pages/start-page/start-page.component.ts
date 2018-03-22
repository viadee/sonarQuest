import { Component, OnInit } from '@angular/core';
import { Developer } from '../../Interfaces/Developer';
import { DeveloperService } from '../../services/developer.service';

@Component({
  selector: 'app-start-page',
  templateUrl: './start-page.component.html',
  styleUrls: ['./start-page.component.css']
})
export class StartPageComponent implements OnInit {

  public developer: Developer;
  public XPpercent: number = 0;
  constructor(public developerService: DeveloperService) { }

  ngOnInit() {
    this.developerService.avatar$.subscribe({
      next: developer => {
        this.developer = developer;
        this.xpPercent();
      }
    })
  }

  public xpPercent(): void {
    this.XPpercent = 100 / this.developer.level.max * this.developer.xp;
    this.XPpercent.toFixed(2);
  }

}
