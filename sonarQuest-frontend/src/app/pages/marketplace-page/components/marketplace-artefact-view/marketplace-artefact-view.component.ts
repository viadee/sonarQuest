import {ImageService} from '../../../../services/image.service';
import {MAT_DIALOG_DATA} from '@angular/material';
import {MatDialogRef} from '@angular/material';
import {Component, OnInit, Inject} from '@angular/core';
import {DomSanitizer} from '@angular/platform-browser';
import { Artefact } from 'app/Interfaces/Artefact';

@Component({
  selector: 'app-marketplace-artefact-view',
  templateUrl: './marketplace-artefact-view.component.html',
  styleUrls: ['./marketplace-artefact-view.component.css']
})
export class ArtefactViewDetailsComponent implements OnInit {

  imageToShow: any;

  constructor(
    private dialogRef: MatDialogRef<ArtefactViewDetailsComponent>,
    private domSanitizer: DomSanitizer,
    private imageService: ImageService,
    @Inject(MAT_DIALOG_DATA) public artefact: Artefact) {
    this.artefact = {...this.artefact};
  }

  ngOnInit() { 
  }

  cancel() {
    this.dialogRef.close(false);
  }

  protected createSkillsList(artefact: any) {
    const skillnames = artefact.skills.map(skill => skill.name);
    return skillnames.join(', ');
  }
}
