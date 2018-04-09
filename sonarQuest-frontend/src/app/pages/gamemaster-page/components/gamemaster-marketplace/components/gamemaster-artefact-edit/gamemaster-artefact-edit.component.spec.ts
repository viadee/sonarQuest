import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterArtefactEditComponent } from './gamemaster-artefact-edit.component';

describe('GamemasterArtefactEditComponent', () => {
  let component: GamemasterArtefactEditComponent;
  let fixture: ComponentFixture<GamemasterArtefactEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterArtefactEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterArtefactEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
