import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InnerSkillTreeAddSkillDialogComponent } from './inner-skill-tree-add-skill-dialog.component';

describe('InnerSkillTreeAddSkillDialogComponent', () => {
  let component: InnerSkillTreeAddSkillDialogComponent;
  let fixture: ComponentFixture<InnerSkillTreeAddSkillDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InnerSkillTreeAddSkillDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InnerSkillTreeAddSkillDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
