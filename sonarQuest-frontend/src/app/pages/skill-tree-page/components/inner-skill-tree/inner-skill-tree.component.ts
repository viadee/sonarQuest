import { Component, OnInit, OnChanges, AfterViewInit, ViewChild } from '@angular/core';
import { RoutingUrls } from 'app/app-routing/routing-urls';
import { ActivatedRoute } from '@angular/router';
import { SkillTreeService } from 'app/services/skill-tree.service';
import { identifierName } from '@angular/compiler';
import * as shape from 'd3-shape';
import { MatDialog } from '@angular/material';
import { InnerSkillDetailDialogComponent } from './components/inner-skill-detail-dialog/inner-skill-detail-dialog.component';
import { PermissionService } from 'app/services/permission.service';
import { UserService } from 'app/services/user.service';
import { WorldService } from 'app/services/world.service';
import { InnerSkillTreeAddSkillDialogComponent } from './components/inner-skill-tree-add-skill-dialog/inner-skill-tree-add-skill-dialog.component';
import { SwalComponent } from '@sweetalert2/ngx-sweetalert2';
import { SonarRuleService } from 'app/services/sonar-rule.service';
import { User } from 'app/Interfaces/User';


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
  private user: User;

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
    this.userService.user$.subscribe(user => {
      this.user = user;
    });
    this.id = +this._route.snapshot.params['id'];
    this.isAdmin = true && this.permissionService.isUrlVisible(RoutingUrls.admin);
    this.isGamemaster = true && this.permissionService.isUrlVisible(RoutingUrls.gamemaster);
    if (this.isAdmin) {
      this.skillTreeService.userSkillTree$.subscribe(userSkillTree => {
        this.userSkillTree = userSkillTree;
      });
      this.skillTreeService.getUserSkillTree(this.id);
    } else if (this.isGamemaster) {
      this.skillTreeService.userSkillTreeForTeam$.subscribe(userSkillTreeForTeam => {
        this.userSkillTree = userSkillTreeForTeam;
      });
        this.skillTreeService.getUserSkillTreeFromTeam(this.id, this.user.currentWorld);
    } else {
      this.skillTreeService.userSkillTreeForUser$.subscribe(userSkillTreeForUser => {
        this.userSkillTree = userSkillTreeForUser;
      });
      this.skillTreeService.getUserSkillTreeFromUser(this.id, this.user);
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

  isSkillTreeLoaded(): boolean {
    if (typeof this.userSkillTree !== 'undefined' && this.userSkillTree.nodes != null) {
      return true;
    }
    return false;
  }

  isThereACurrentWorld(): boolean {
    if (this.user.currentWorld !== null) {
      return true;
    }
    return false;
  }
}
