import { Component, OnInit, OnChanges, AfterViewInit } from '@angular/core';
import { RoutingUrls } from 'app/app-routing/routing-urls';
import { ActivatedRoute } from '@angular/router';
import { SkillTreeService } from 'app/services/skill-tree.service';
import { identifierName } from '@angular/compiler';
import * as shape from 'd3-shape';
import { Subject } from 'rxjs';
import { MatDialog } from '@angular/material';
import { InnerSkillDetailDialogComponent } from '../inner-skill-detail-dialog/inner-skill-detail-dialog.component';

@Component({
  selector: 'app-inner-skill-tree',
  templateUrl: './inner-skill-tree.component.html',
  styleUrls: ['./inner-skill-tree.component.css']
})
export class InnerSkillTreeComponent implements OnInit {

  public skillTreeUrl = RoutingUrls.skilltree;
  userSkillTree: { nodes: [], links: [] };
  curve = shape.curveLinear;
  isDataAvailable: boolean;
  private id: number;
  nodecolor = '#c0c0c0';
  color = 'primary';
  mode = 'determinate';
  value = 50;
  bufferValue = 75;

  constructor(private skillTreeService: SkillTreeService, private _route: ActivatedRoute, public dialog: MatDialog) {
  }

  ngOnInit() {
    this.id = +this._route.snapshot.params['id'];
    this.userSkillTree = null;
    this.skillTreeService.userSkillTree$.subscribe(userSkillTree => {
      this.userSkillTree = userSkillTree;
    });
    this.skillTreeService.getUserSkillTree(this.id);

  }

  openDialog(event): void {
    const dialogRef = this.dialog.open(InnerSkillDetailDialogComponent, {
      width: '250px',
      data: event
    });
    console.log(event);
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}
