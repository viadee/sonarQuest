import { Component, OnInit }  from '@angular/core';
import {Developer}            from '../../Interfaces/Developer';
import {DeveloperService}     from '../../services/developer.service';

@Component({
  selector: 'app-my-avatar-page',
  templateUrl: './my-avatar-page.component.html',
  styleUrls: ['./my-avatar-page.component.css']
})
export class MyAvatarPageComponent implements OnInit {

  public developer: Developer;
  constructor( public developerService: DeveloperService) { }

  ngOnInit() {
    this.developerService.getMyAvatar().subscribe(developer => this.developer = developer)
  }

}
