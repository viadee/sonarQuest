import { TestBed, inject } from '@angular/core/testing';

import { StandardTaskService } from './standard-task.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('StandardTaskService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [StandardTaskService]
    });
  });

  it('should be created', inject([StandardTaskService], (service: StandardTaskService) => {
    expect(service).toBeTruthy();
  }));
});
