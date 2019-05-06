import { TestBed } from '@angular/core/testing';

import { SonarRuleService } from './sonar-rule.service';

describe('SonarRuleService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SonarRuleService = TestBed.get(SonarRuleService);
    expect(service).toBeTruthy();
  });
});
