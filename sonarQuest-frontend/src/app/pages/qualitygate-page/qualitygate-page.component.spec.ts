import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QualitygatePageComponent } from './qualitygate-page.component';

describe('QualitygatePageComponent', () => {
  let component: QualitygatePageComponent;
  let fixture: ComponentFixture<QualitygatePageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QualitygatePageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QualitygatePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
