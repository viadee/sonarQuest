import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import {ArtefactViewDetailsComponent} from "./marketplace-artefact-view.component";

describe('AvatarEditComponent', () => {
  let component: ArtefactViewDetailsComponent;
  let fixture: ComponentFixture<ArtefactViewDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArtefactViewDetailsComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArtefactViewDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
