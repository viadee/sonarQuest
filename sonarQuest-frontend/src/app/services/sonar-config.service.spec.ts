import { TestBed, inject } from '@angular/core/testing';

import { SonarConfigService } from './sonar-config.service';

describe('SonarConfigService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SonarConfigService]
    });
  });

  it('should be created', inject([SonarConfigService], (service: SonarConfigService) => {
    expect(service).toBeTruthy();
  }));
});
