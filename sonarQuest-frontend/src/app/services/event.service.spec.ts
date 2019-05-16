import { TestBed, inject } from '@angular/core/testing';

import { EventService } from './event.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {WorldServiceTestingModule} from "./world.service.mock.module";
import {UserServiceTestingModule} from "./user.service.mock.module";
import {ImageService} from "./image.service";
import { RouterTestingModule } from '@angular/router/testing';

describe('EventService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        WorldServiceTestingModule,
        UserServiceTestingModule,
        RouterTestingModule
      ],
      providers: [
        EventService,
        ImageService
      ]
    });
  });

  it('should be created', inject([EventService], (service: EventService) => {
    expect(service).toBeTruthy();
  }));
});
