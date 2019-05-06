import { TestBed, inject } from '@angular/core/testing';

import { UiDesignService } from './ui-design.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('UiDesignService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [UiDesignService]
    });
  });

  it('should be created', inject([UiDesignService], (service: UiDesignService) => {
    expect(service).toBeTruthy();
  }));
});
