import { TestBed, inject } from '@angular/core/testing';

import { ArtefactService } from './artefact.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('ArtefactService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [ArtefactService]
    });
  });

  it('should be created', inject([ArtefactService], (service: ArtefactService) => {
    expect(service).toBeTruthy();
  }));
});
