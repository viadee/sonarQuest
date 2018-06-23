import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditWorldComponent } from './edit-world.component';

describe('EditWorldComponent', () => {
  let component: EditWorldComponent;
  let fixture: ComponentFixture<EditWorldComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditWorldComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditWorldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
