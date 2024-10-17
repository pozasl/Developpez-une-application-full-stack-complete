import { TestBed } from '@angular/core/testing';

import { NotificationService } from './notification.service';
import { take } from 'rxjs';

describe('NotificationService', () => {
  let service: NotificationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NotificationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('when notifError $error should return an AppError Observable', (done) => {
    service.$error().pipe(take(1)).subscribe({
      next: appError => {
        expect(appError.name).toEqual("Error name");
        expect(appError.message).toEqual("Error message");
        done()
      }
    });
    service.notifyError("Error name", "Error message");
  });
});
