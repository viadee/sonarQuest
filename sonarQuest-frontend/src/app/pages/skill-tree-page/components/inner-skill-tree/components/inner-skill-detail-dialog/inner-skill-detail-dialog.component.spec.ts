import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InnerSkillDetailDialogComponent } from './inner-skill-detail-dialog.component';

describe('InnerSkillDetailDialogComponent', () => {
  let component: InnerSkillDetailDialogComponent;
  let fixture: ComponentFixture<InnerSkillDetailDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InnerSkillDetailDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InnerSkillDetailDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
