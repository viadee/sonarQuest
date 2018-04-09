import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GamemasterArtefactCreateComponent } from './gamemaster-artefact-create.component';

describe('GamemasterArtefactCreateComponent', () => {
  let component: GamemasterArtefactCreateComponent;
  let fixture: ComponentFixture<GamemasterArtefactCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GamemasterArtefactCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GamemasterArtefactCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
