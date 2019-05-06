import { TestBed, inject } from '@angular/core/testing';

import { ParticipationService } from './participation.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('ParticipationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [ParticipationService]
    });
  });

  it('should be created', inject([ParticipationService], (service: ParticipationService) => {
    expect(service).toBeTruthy();
  }));
});
