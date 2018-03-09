import { TestBed, inject } from '@angular/core/testing';

import { StandardTaskService } from './standard-task.service';

describe('StandardTaskService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StandardTaskService]
    });
  });

  it('should be created', inject([StandardTaskService], (service: StandardTaskService) => {
    expect(service).toBeTruthy();
  }));
});
