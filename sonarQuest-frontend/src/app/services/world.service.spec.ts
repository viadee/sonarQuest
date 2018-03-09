import { TestBed, inject } from '@angular/core/testing';

import { WorldService } from './world.service';

describe('WorldService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [WorldService]
    });
  });

  it('should be created', inject([WorldService], (service: WorldService) => {
    expect(service).toBeTruthy();
  }));
});
