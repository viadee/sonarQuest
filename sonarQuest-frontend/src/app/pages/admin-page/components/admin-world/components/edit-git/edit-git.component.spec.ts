import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditGitComponent } from './edit-git.component';

describe('EditGitComponent', () => {
  let component: EditGitComponent;
  let fixture: ComponentFixture<EditGitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditGitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditGitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
