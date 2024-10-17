import { TestBed } from '@angular/core/testing';

import { SessionService } from './session.service';
import { HttpErrorResponse, provideHttpClient } from '@angular/common/http';
import { of, take, throwError } from 'rxjs';
import { AuthService, User } from '../core/modules/openapi';
import { NotificationService } from './notification.service';

describe('SessionService', () => {

  let service: SessionService;

  const bob: User = {
    id: 1,
    name: "Bob",
    email: "bob@test.com",
    created_at: Date.now().toString(),
    updated_at: Date.now().toString()
  }

  describe('without token in localstorage ', () => {

    beforeEach(() => {
      TestBed.configureTestingModule({
        providers: [provideHttpClient()]
      });
      localStorage.removeItem("token");
      service = TestBed.inject(SessionService);
    });

    it('should be created', () => {
      expect(service).toBeTruthy();
    });

    it('resuming should be false', () => {
      expect(service.resuming).toBeFalse();
    });

    it('$logged should return a false logged status observable', (done) => {
      service.$logged().subscribe({
        next: is => {
          expect(is).toBeFalse();
          done();
        }
      })
    });

    it('when login a user $logged should return a true logged status observable', (done) => {
      let count = 0;
      service.$logged().pipe(take(2)).subscribe({
        next: is => {
          count++;
          if (count > 1) {
            expect(is).toBeTrue();
            expect(service.logged).toBeTrue();
            expect(service.user).toEqual(bob);
            expect(is).toBeTrue();
            done();
          }
          else {
            expect(is).toBeFalse();
          }
        }
      })
      service.logIn(bob);
    });

    it('When logout is called $logged should return a false logged status observable', (done) => {
      let count = 0;
      service.$logged().subscribe({
        next: is => {
          count++;
          if (count > 1) {
            expect(is).toBeFalse();
            expect(service.token).toBeNull();
            expect(localStorage.getItem('token')).toBeNull();
            expect(service.user).toBeUndefined();
            expect(service.logged).toBeFalse();
            done();
          }
          else {
            expect(is).toBeFalse();
          }
        }
      })

      service.logOut();
    });

  });

  describe('with token in localstoraged', () => {

    const authServiceStub = {
      getMe: jasmine.createSpy("getMe")
    }

    const notifServiceStub = {
      notifyError: jasmine.createSpy("notifyError")
    }

    beforeEach(() => {
      TestBed.configureTestingModule({
        providers: [
          provideHttpClient(),
          { provide: AuthService, useValue: authServiceStub },
          { provide: NotificationService, useValue: notifServiceStub },
        ]
      });
      localStorage.setItem("token", "fake_token");
      service = TestBed.inject(SessionService);
    });

    afterEach(() => {
      authServiceStub.getMe.calls.reset();
      notifServiceStub.notifyError.calls.reset();
    });

    it('resuming should be true', () => {
      expect(service.resuming).toBeTrue();
    });

    it('when getMe fails with 401 $logged should return a false logged status observable', (done) => {
      let count = 0;
      authServiceStub.getMe.and.returnValue(throwError(() => new HttpErrorResponse({ status: 401 })))

      service.$logged().pipe(take(2)).subscribe({
        next: is => {
          count++;
          if (count > 1) {
            expect(is).toBeFalse();
            expect(authServiceStub.getMe).toHaveBeenCalled();
            expect(service.resuming).toBeFalse();
            done();
          } else {
            expect(is).toBeFalse();
            service.resume();
          }
        }
      });
    });

    it('when getMe fails $logged should return a false logged status observable and notify error', (done) => {
      let count = 0;
      authServiceStub.getMe.and.returnValue(throwError(() => new HttpErrorResponse({ status: 404 })))

      service.$logged().pipe(take(2)).subscribe({
        next: is => {
          count++;
          if (count > 1) {
            expect(is).toBeFalse();
            expect(authServiceStub.getMe).toHaveBeenCalled();
            expect(notifServiceStub.notifyError).toHaveBeenCalled();
            expect(service.resuming).toBeFalse();
            done();
          } else {
            expect(is).toBeFalse();
            service.resume();
          }
        }
      })
    });

    it('when getMe succeeds with user $logged should return a true logged status observable', (done) => {
      let count = 0;
      authServiceStub.getMe.and.returnValue(of(bob))

      service.$logged().pipe(take(2)).subscribe({
        next: is => {
          count++;
          if (count > 1) {
            expect(is).toBeTrue();
            expect(service.resuming).toBeFalse();
            done();
          } else {
            expect(is).toBeFalse();
            service.resume();
          }
        }
      })
    });
  });

});
