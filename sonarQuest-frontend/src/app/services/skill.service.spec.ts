import { TestBed, inject } from '@angular/core/testing';

import { SkillService } from './skill.service';

describe('SkillService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SkillService]
    });
  });

  it('should be created', inject([SkillService], (service: SkillService) => {
    expect(service).toBeTruthy();
  }));
});
