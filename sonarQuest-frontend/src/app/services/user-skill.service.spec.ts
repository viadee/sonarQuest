import { TestBed } from '@angular/core/testing';

import { UserSkillService } from './user-skill.service';

describe('UserSkillService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserSkillService = TestBed.get(UserSkillService);
    expect(service).toBeTruthy();
  });
});
