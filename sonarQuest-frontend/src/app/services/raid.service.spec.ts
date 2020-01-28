import { TestBed } from '@angular/core/testing';

import { RaidService } from './raid.service';

describe('RaidService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: RaidService = TestBed.get(RaidService);
    expect(service).toBeTruthy();
  });
});
