import { TestBed, inject } from '@angular/core/testing';

import { SkillService } from './skill.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('SkillService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [SkillService]
    });
  });

  it('should be created', inject([SkillService], (service: SkillService) => {
    expect(service).toBeTruthy();
  }));
});
