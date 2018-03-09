import { TestBed, inject } from '@angular/core/testing';

import { ParticipationService } from './participation.service';

describe('ParticipationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ParticipationService]
    });
  });

  it('should be created', inject([ParticipationService], (service: ParticipationService) => {
    expect(service).toBeTruthy();
  }));
});
