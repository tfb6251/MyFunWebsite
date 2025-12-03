import { TestBed } from '@angular/core/testing';

import { TelemService } from './telem.service';

describe('TelemService', () => {
  let service: TelemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TelemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
