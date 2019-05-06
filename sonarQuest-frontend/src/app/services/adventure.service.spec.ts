import { TestBed, inject } from '@angular/core/testing';

import { AdventureService } from './adventure.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('AdventureService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [AdventureService]
    });
  });

  it('should be created', inject([AdventureService], (service: AdventureService) => {
    expect(service).toBeTruthy();
  }));
});
