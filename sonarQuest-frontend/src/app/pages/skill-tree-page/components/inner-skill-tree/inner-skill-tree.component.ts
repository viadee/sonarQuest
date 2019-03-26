import { Component, OnInit, OnChanges, AfterViewInit } from '@angular/core';
import { RoutingUrls } from 'app/app-routing/routing-urls';
import {ActivatedRoute} from '@angular/router';
import { SkillTreeService } from 'app/services/skill-tree.service';
import { identifierName } from '@angular/compiler';
import * as shape from 'd3-shape';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-inner-skill-tree',
  templateUrl: './inner-skill-tree.component.html',
  styleUrls: ['./inner-skill-tree.component.css']
})
export class InnerSkillTreeComponent implements OnInit{ 

  public skillTreeUrl = RoutingUrls.skilltree;
  userSkillTree: { nodes: [], links: [] };
  hierarchialGraph: { nodes: [], links: [] };
  curve = shape.curveBundle.beta(1);
  isDataAvailable: boolean;
  private id: number;

  constructor(private skillTreeService: SkillTreeService, private _route: ActivatedRoute) { }

  ngOnInit() {
    this.id = +this._route.snapshot.params['id'];
    console.log(this.id);
    this.userSkillTree= null;
    this.hierarchialGraph = null;
    this.skillTreeService.userSkillTree$.subscribe(userSkillTree => {
      this.userSkillTree = userSkillTree;
    });
    this.skillTreeService.getUserSkillTree(this.id);
    console.log('############## Server Data ############');
    console.log(this.userSkillTree);
    this.hierarchialGraph = this.userSkillTree;
  }

}
