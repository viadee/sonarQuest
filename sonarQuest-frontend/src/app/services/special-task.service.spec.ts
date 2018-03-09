import { TestBed, inject } from '@angular/core/testing';

import { SpecialTaskService } from './special-task.service';

describe('SpecialTaskService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SpecialTaskService]
    });
  });

  it('should be created', inject([SpecialTaskService], (service: SpecialTaskService) => {
    expect(service).toBeTruthy();
  }));
});
