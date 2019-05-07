import { Component, OnInit, OnChanges, AfterViewInit } from '@angular/core';
import { RoutingUrls } from 'app/app-routing/routing-urls';
import { ActivatedRoute } from '@angular/router';
import { SkillTreeService } from 'app/services/skill-tree.service';
import { identifierName } from '@angular/compiler';
import * as shape from 'd3-shape';
import { Subject } from 'rxjs';
import { MatDialog } from '@angular/material';
import { InnerSkillDetailDialogComponent } from '../inner-skill-detail-dialog/inner-skill-detail-dialog.component';
import { SonarRule } from 'app/Interfaces/SonarRule';
import { PermissionService } from 'app/services/permission.service';
import { UserService } from 'app/services/user.service';
import { WorldService } from 'app/services/world.service';
import { NgxGraphModule } from '@swimlane/ngx-graph';
import { InnerSkillTreeAddSkillDialogComponent } from './components/inner-skill-tree-add-skill-dialog/inner-skill-tree-add-skill-dialog.component';


@Component({
  selector: 'app-inner-skill-tree',
  templateUrl: './inner-skill-tree.component.html',
  styleUrls: ['./inner-skill-tree.component.css']
})
export class InnerSkillTreeComponent implements OnInit {

  public skillTreeUrl = RoutingUrls.skilltree;
  userSkillTree: { nodes: [], links: [] };
  curve = shape.curveMonotoneY
  isDataAvailable: boolean;
  isAdmin = false;
  isGamemaster = false;
  private id: number;
  color = 'primary';
  mode = 'determinate';
  value = 50;
  bufferValue = 75;


  constructor(private skillTreeService: SkillTreeService,
    private _route: ActivatedRoute,
    public dialog: MatDialog,
    private permissionService: PermissionService,
    private userService: UserService,
    private worldService: WorldService) {
  }

  ngOnInit() {
    this.id = +this._route.snapshot.params['id'];
    this.userSkillTree = null;

    this.isAdmin = true && this.permissionService.isUrlVisible(RoutingUrls.admin);
    this.isGamemaster = true && this.permissionService.isUrlVisible(RoutingUrls.gamemaster);
    if (this.isAdmin) {
      this.skillTreeService.userSkillTree$.subscribe(userSkillTree => {
        this.userSkillTree = userSkillTree;
      });
      this.skillTreeService.getUserSkillTree(this.id);
    } else if (this.isGamemaster) {
      console.log('isGM');
      this.skillTreeService.userSkillTreeForTeam$.subscribe(userSkillTreeForTeam => {
        this.userSkillTree = userSkillTreeForTeam;
      });
      if (this.worldService.getCurrentWorld() !== null) {
        this.skillTreeService.getUserSkillTreeFromTeam(this.id, this.worldService.getCurrentWorld());
      } else {
        this.userSkillTree = { nodes: [], links: [] };
        console.log(this.userSkillTree.nodes.length);
      }
    } else {
      this.skillTreeService.userSkillTreeForUser$.subscribe(userSkillTreeForUser => {
        this.userSkillTree = userSkillTreeForUser;
      });
      this.skillTreeService.getUserSkillTreeFromUser(this.id, this.userService.getUser());
    }
  }

  openDialog(node): void {
    const dialogRef = this.dialog.open(InnerSkillDetailDialogComponent, {panelClass: 'dialog-sexy',
      width: '550px',
      data: node
    });
    dialogRef.afterClosed().subscribe();
  }
  addSkill(): void {
    const dialogRef = this.dialog.open(InnerSkillTreeAddSkillDialogComponent, {panelClass: 'dialog-sexy',
      data: this.id,
      width: '550px'
    });
    dialogRef.afterClosed().subscribe();
  }
  calculateProcentage(repeats: number, requiredRepetitions: number): number {
    const procentage = Math.round((repeats / requiredRepetitions) * 100);
    if (procentage > 100) {
      return 100;
    }
    return procentage;
  }
}
