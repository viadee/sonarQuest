import { TestBed, inject } from '@angular/core/testing';

import { QuestService } from './quest.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {ParticipationServiceTestingModule} from "./participation.service.mock.module";
import {TaskServiceTestingModule} from "./task.service.mock.module";
import {UserServiceTestingModule} from "./user.service.mock.module";

describe('QuestService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        ParticipationServiceTestingModule,
        TaskServiceTestingModule,
        UserServiceTestingModule
      ],
      providers: [QuestService]
    });
  });

  it('should be created', inject([QuestService], (service: QuestService) => {
    expect(service).toBeTruthy();
  }));
});
