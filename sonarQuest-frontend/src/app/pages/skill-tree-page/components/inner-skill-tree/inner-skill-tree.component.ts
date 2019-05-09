import { Component, OnInit, OnChanges, AfterViewInit, ViewChild } from '@angular/core';
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
import { SwalComponent } from '@sweetalert2/ngx-sweetalert2';
import { SonarRuleService } from 'app/services/sonar-rule.service';


@Component({
  selector: 'app-inner-skill-tree',
  templateUrl: './inner-skill-tree.component.html',
  styleUrls: ['./inner-skill-tree.component.css']
})
export class InnerSkillTreeComponent implements OnInit {
  @ViewChild('newRulesAdded') private newRulesAdded: SwalComponent

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
  public unassignedSonarRules;

  public swalOptionsAddedRuleSuccess: {};

  constructor(private skillTreeService: SkillTreeService,
    private _route: ActivatedRoute,
    public dialog: MatDialog,
    private permissionService: PermissionService,
    private userService: UserService,
    private worldService: WorldService,
    private sonarRuleService: SonarRuleService) {
  }

  ngOnInit() {
    this.id = +this._route.snapshot.params['id'];
    //this.userSkillTree = null;

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
      }
    } else {
      this.skillTreeService.userSkillTreeForUser$.subscribe(userSkillTreeForUser => {
        this.userSkillTree = userSkillTreeForUser;
      });
      this.skillTreeService.getUserSkillTreeFromUser(this.id, this.userService.getUser());
    }
    this.initSwal();
    this.subscribeUnassignedSonarRules();
    this.getUnassingendSonarRules();
  }

  private initSwal() {
    this.swalOptionsAddedRuleSuccess = {
      type: 'success',
      toast: true,
      showConfirmButton: false,
      position: 'top-end',
      backdrop: false,
      timer: 5000
    }
  }

  private subscribeUnassignedSonarRules(): void {
    this.sonarRuleService.unassignedSonarRules$.subscribe(unassignedSonarRules => {
      this.unassignedSonarRules = unassignedSonarRules;
    });
  }
  private getUnassingendSonarRules(): void {
    this.sonarRuleService.loadUnassignedSonarRules();
  }
  openDialog(node): void {
    console.log(this.userSkillTree);
    const dialogRef = this.dialog.open(InnerSkillDetailDialogComponent, {
      panelClass: 'dialog-sexy',
      width: '550px',
      data: node
    });
    dialogRef.afterClosed().subscribe();
  }
  addSkill(): void {
    const dialogRef = this.dialog.open(InnerSkillTreeAddSkillDialogComponent, {
      panelClass: 'dialog-sexy',
      data: {
        groupid: this.id,
        unassignedSonarRules: this.unassignedSonarRules
      },
      width: '550px'
    });
    dialogRef.afterClosed().subscribe(success => {
      if (success) {
        this.getUnassingendSonarRules();
        this.skillTreeService.getUserSkillTree(this.id);
        this.newRulesAdded.show();
      }

    });
  }
  calculateProcentage(repeats: number, requiredRepetitions: number): number {
    const procentage = Math.round((repeats / requiredRepetitions) * 100);
    if (procentage > 100) {
      return 100;
    }
    return procentage;
  }
}
