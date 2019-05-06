import { TestBed, inject } from '@angular/core/testing';

import { SpecialTaskService } from './special-task.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('SpecialTaskService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [SpecialTaskService]
    });
  });

  it('should be created', inject([SpecialTaskService], (service: SpecialTaskService) => {
    expect(service).toBeTruthy();
  }));
});
