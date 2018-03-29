import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseCurrentWorldComponent } from './choose-current-world.component';

describe('ChooseCurrentWorldComponent', () => {
  let component: ChooseCurrentWorldComponent;
  let fixture: ComponentFixture<ChooseCurrentWorldComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChooseCurrentWorldComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChooseCurrentWorldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
