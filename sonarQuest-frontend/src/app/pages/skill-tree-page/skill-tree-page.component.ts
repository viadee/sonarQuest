import { Component, OnInit } from '@angular/core';
import { UserSkill } from '../../Interfaces/UserSkill';
import { SkillTreeService } from '../../services/skill-tree.service';
import { NestedTreeControl } from '@angular/cdk/tree';
import { MatTreeNestedDataSource } from '@angular/material/tree';
import * as shape from 'd3-shape';
import { NgxGraphModule } from '@swimlane/ngx-graph';
import { UserSkillGroup } from 'app/Interfaces/UserSkillGroup';
import {Router, RouterModule} from '@angular/router';
import { RoutingUrls } from 'app/app-routing/routing-urls';


@Component({
  selector: 'app-skill-tree-page',
  templateUrl: './skill-tree-page.component.html',
  styleUrls: ['./skill-tree-page.component.css']
})
export class SkillTreePageComponent implements OnInit {

  userSkillGroupTree: { nodes: [], links: [] };
  curve = shape.curveLinear
  nodecolor =  "#c0c0c0";
 // curve = shape.curveLinear;


  constructor(private skillTreeService: SkillTreeService, private router: Router) { }

  ngOnInit() {
    this.skillTreeService.userSkillGroupTree$.subscribe(userSkillGroupTree => {
      this.userSkillGroupTree = userSkillGroupTree;
    });
    this.skillTreeService.getData();
   // this.showGraph();
  }
  navigatToInnerSkilLTree(id: number) {
    this.router.navigate([RoutingUrls.innerskilltree, id]);
    console.log(RoutingUrls.innerskilltree);
  }

  /*showGraph() {
    
    console.log("####### Data from Server#####");
    console.log(this.userSkillGroupTree)
  
  }*/
}
