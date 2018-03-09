import { TestBed, inject } from '@angular/core/testing';

import { DeveloperService } from './developer.service';

describe('DeveloperService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DeveloperService]
    });
  });

  it('should be created', inject([DeveloperService], (service: DeveloperService) => {
    expect(service).toBeTruthy();
  }));
});
