import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InnerSkillTreeComponent } from './inner-skill-tree.component';

describe('InnerSkillTreeComponent', () => {
  let component: InnerSkillTreeComponent;
  let fixture: ComponentFixture<InnerSkillTreeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InnerSkillTreeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InnerSkillTreeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
