import { DeveloperService } from './../../../../../../services/developer.service';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { AdminDeveloperComponent } from './../../admin-developer.component';
import { Developer } from './../../../../../../Interfaces/Developer.d';
import { Component, OnInit, Inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, ValidatorFn, Validators } from '@angular/forms';
import { Observable } from 'rxjs/Rx'

@Component({
  selector: 'app-admin-developer-create',
  templateUrl: './admin-developer-create.component.html',
  styleUrls: ['./admin-developer-create.component.css']
})
export class AdminDeveloperCreateComponent implements OnInit {

  public username: String;
  public aboutMe:  String;
  public images: any[];
  public selectedImage: string;
  public nameTaken: boolean;

  createForm = new FormGroup({
    name: new FormControl(null, [Validators.required, this.matchNameValidator()]),
    about: new FormControl()
  });
    
  constructor(
    private dialogRef: MatDialogRef<AdminDeveloperComponent>,
    private developerService: DeveloperService,
    @Inject(MAT_DIALOG_DATA) public developers: Developer[]
  ) { }
  
  ngOnInit() {    
    this.loadImages();
    this.selectedImage = "http://via.placeholder.com/200x200";
    this.nameTaken = false;        
  }

  matchNameValidator() {
    return (control: FormControl) => {
      const nameVal = control.value;
      this.developers.forEach( e => {
        if(e.username == nameVal){          
          this.nameTaken = true;                   
        } else {
          this.nameTaken = false;         
        }       
      })
      return this.nameTaken ? {'currentName' : {nameVal}} : null;
    } 
  }

  createDeveloper(){    
    if(!this.nameTaken){      
      let new_developer = {
        username: this.createForm.get('name').value,
        aboutMe:  this.createForm.get('about').value,
        picture: this.selectedImage
      }        

      this.developerService.createDeveloper(new_developer)
        .then( developer => {
         this.dialogRef.close(developer);
      }) 
    }       
  }
 

  cancel(){
    this.dialogRef.close();
  }

  loadImages() {
    this.images = [];

    for (let i = 0; i < 15; i++) {
      this.images[i] = {};
      this.images[i].src = "assets/images/quest/hero" + (i + 1) + ".jpg";
      this.images[i].name = "hero" + (i + 1);
    }
  }

  getErrorMessage(){
    if(this.createForm.get('name').hasError('required')){      
      return 'You must enter a name';
    }
    if(this.nameTaken){
      this.createForm.controls['name'].setErrors({'matchNameValidator' : true});     
      return 'Name already taken. Please choose a different name';
    }
    
    
  }


}



