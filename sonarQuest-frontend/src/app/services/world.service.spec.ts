import {inject, TestBed} from '@angular/core/testing';

import {WorldService} from './world.service';
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {UserServiceTestingModule} from "./user.service.mock.module";

describe('WorldService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        UserServiceTestingModule
      ],
      providers: [
        WorldService
      ]
    });
  });

  it('should be created', inject([WorldService], (service: WorldService) => {
    expect(service).toBeTruthy();
  }));
});
