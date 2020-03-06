import { TestBed } from '@angular/core/testing';

import { QualityGateRaidService } from './quality-gate-raid.service';

describe('QualityGateRaidService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: QualityGateRaidService = TestBed.get(QualityGateRaidService);
    expect(service).toBeTruthy();
  });
});
