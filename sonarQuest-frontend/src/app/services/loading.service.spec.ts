import {inject, TestBed} from '@angular/core/testing';

import {LoadingService} from './loading.service';
import {MatDialogModule} from "@angular/material";

describe('LoadingService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        MatDialogModule
      ],
      providers: [LoadingService]
    });
  });

  it('should be created', inject([LoadingService], (service: LoadingService) => {
    expect(service).toBeTruthy();
  }));
});
