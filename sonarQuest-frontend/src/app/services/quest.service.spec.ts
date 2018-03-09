import { TestBed, inject } from '@angular/core/testing';

import { QuestService } from './quest.service';

describe('QuestService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [QuestService]
    });
  });

  it('should be created', inject([QuestService], (service: QuestService) => {
    expect(service).toBeTruthy();
  }));
});
