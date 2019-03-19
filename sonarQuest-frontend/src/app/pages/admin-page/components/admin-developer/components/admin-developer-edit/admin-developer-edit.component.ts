import { TranslateService } from '@ngx-translate/core';
import { MAT_DIALOG_DATA } from '@angular/material';
import { MatDialogRef } from '@angular/material';
import { AdminDeveloperComponent } from './../../admin-developer.component';
import { Component, OnInit, Inject } from '@angular/core';
import { UserService } from '../../../../../../services/user.service';
import { User } from '../../../../../../Interfaces/User';
import { ImageService } from '../../../../../../services/image.service';
import { UserToWorld } from '../../../../../../Interfaces/UserToWorld';
import { ITdDataTableColumn } from '@covalent/core';
import { UserToWorldService } from '../../../../../../services/user-to-world.service';
import { Role } from 'app/Interfaces/Role';
import { RoleService } from 'app/services/role.service';

@Component({
  selector: 'app-admin-developer-edit',
  templateUrl: './admin-developer-edit.component.html',
  styleUrls: ['./admin-developer-edit.component.css']
})
export class AdminDeveloperEditComponent implements OnInit {

  imageToShow: any;
  userToWorlds: UserToWorld[];
  roles: Role[];

  columns: ITdDataTableColumn[] = [
    { name: 'userId', label: 'UserId', hidden: true },
    { name: 'worldId', label: 'WorldId', hidden: true },
    { name: 'worldName', label: 'World' },
    { name: 'editJoined', label: 'Joined' },
  ];

  constructor(
    private dialogRef: MatDialogRef<AdminDeveloperComponent>,
    private userService: UserService,
    private translateService: TranslateService,
    @Inject(MAT_DIALOG_DATA) public user: User,
    private imageService: ImageService,
    private roleService: RoleService,
    private userToWorldService: UserToWorldService
  ) {
  }

  ngOnInit() {
    this.translateTable();
    this.loadImages();
    this.userToWorldService.getUserToWorlds(this.user).then(userToWorlds => {
      this.userToWorlds = userToWorlds
    });
    
    this.roleService.getRoles().then(roles => this.roles = roles);
  }

  translateTable() {
    this.translateService.get('TABLE.COLUMNS').subscribe((col_names) => {
      this.columns = [
        { name: 'worldName', label: col_names.WORLD },
        { name: 'editJoined', label: col_names.JOINED }]
    });
  }

  editDeveloper() {
    this.userService.updateUser(this.user).then(() => {
      this.userToWorldService.saveUserToWorlds(this.userToWorlds);
      this.dialogRef.close(true);
    })
  }

  cancel() {
    this.dialogRef.close(false);
  }

  loadImages() {
    this.userService.getImageForUser(this.user).subscribe((blob) => {
      this.imageService.createImageFromBlob(blob).subscribe(image => this.imageToShow = image);
    });
  }

}
