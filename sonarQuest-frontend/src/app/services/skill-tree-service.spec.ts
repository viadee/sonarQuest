import { TestBed } from '@angular/core/testing';

import { SkillTreeService } from './skill-tree.service';

describe('SkillTreeServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SkillTreeService = TestBed.get(SkillTreeService);
    expect(service).toBeTruthy();
  });
});
