import { TestBed } from '@angular/core/testing';

import { QualityGateServiceService } from './quality-gate-service.service';

describe('QualityGateServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: QualityGateServiceService = TestBed.get(QualityGateServiceService);
    expect(service).toBeTruthy();
  });
});
