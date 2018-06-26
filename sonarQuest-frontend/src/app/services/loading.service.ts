import { Injectable } from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material';
import {LoadingComponent} from '../components/loading/loading.component';

@Injectable()
export class LoadingService {

  constructor( private dialog: MatDialog) { }

  getLoadingSpinner() {
    return this.dialog.open(LoadingComponent, { disableClose: true, panelClass: 'transparent' });
  }

}
