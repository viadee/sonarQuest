import { Component, OnInit, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';

@Component({
  selector: 'app-inner-skill-detail-dialog',
  templateUrl: './inner-skill-detail-dialog.component.html',
  styleUrls: ['./inner-skill-detail-dialog.component.css']
})
export class InnerSkillDetailDialogComponent implements OnInit {



  constructor(
    public dialogRef: MatDialogRef<InnerSkillDetailDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data) { }

  ngOnInit() {
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
