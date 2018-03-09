import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAvatarPageComponent } from './my-avatar-page.component';

describe('MyAvatarPageComponent', () => {
  let component: MyAvatarPageComponent;
  let fixture: ComponentFixture<MyAvatarPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyAvatarPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyAvatarPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
