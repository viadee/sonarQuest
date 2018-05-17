import { TestBed, inject } from '@angular/core/testing';

import { UiDesignService } from './ui-design.service';

describe('UiDesignService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [UiDesignService]
    });
  });

  it('should be created', inject([UiDesignService], (service: UiDesignService) => {
    expect(service).toBeTruthy();
  }));
});
