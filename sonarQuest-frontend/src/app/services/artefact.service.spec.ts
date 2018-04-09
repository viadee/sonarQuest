import { TestBed, inject } from '@angular/core/testing';

import { ArtefactService } from './artefact.service';

describe('ArtefactService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ArtefactService]
    });
  });

  it('should be created', inject([ArtefactService], (service: ArtefactService) => {
    expect(service).toBeTruthy();
  }));
});
