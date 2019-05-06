import { TestBed, inject } from '@angular/core/testing';

import { TaskService } from './task.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {StandardTaskServiceTestingModule} from "./standard-task.service.mock.module";
import {SpecialTaskServiceTestingModule} from "./special-task.service.mock.module";

describe('TaskService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        StandardTaskServiceTestingModule,
        SpecialTaskServiceTestingModule
      ],
      providers: [TaskService]
    });
  });

  it('should be created', inject([TaskService], (service: TaskService) => {
    expect(service).toBeTruthy();
  }));
});
