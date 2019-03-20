import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SkillTreePageComponent } from './skill-tree-page.component';

describe('SkillTreePageComponent', () => {
  let component: SkillTreePageComponent;
  let fixture: ComponentFixture<SkillTreePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SkillTreePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SkillTreePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
