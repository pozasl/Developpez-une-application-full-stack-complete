import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MeComponent } from './me.component';
import { HttpErrorResponse, provideHttpClient } from '@angular/common/http';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { SessionService } from 'src/app/services/session.service';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AuthService, JwtInfo, NewUser, ResponseMessage, SubscribtionsService, User } from 'src/app/core/modules/openapi';
import { of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/services/notification.service';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;

  const error = new HttpErrorResponse({ status: 404 });

  const bob: User = {
    id: 1,
    name: "Bob",
    email: "bob@test.com",
    created_at: Date.now().toString(),
    updated_at: Date.now().toString()
  }

  const sessionServiceStub = {
    logIn: jasmine.createSpy('logIn'),
    logOut: jasmine.createSpy('logOut'),
    user: bob
  };

  const routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  const authServiceStub = {
    getMe: jasmine.createSpy('getMe'),
    updateMe: jasmine.createSpy('updateMe')
  };

  const subsServiceStub = {
    getUserSubscribtions: jasmine.createSpy('getUserSubscribtions'),
    unsubscribe: jasmine.createSpy('unsubscribe'),
  };

  const notifserviceStub = {
    notifyError: jasmine.createSpy('notifyError')
  };

  const jwtInfo: JwtInfo = {
    token: 'un_token',
    userId: 1
  }

  const topic1 = {
    ref: 'java',
    name: 'Java',
    description: 'Java bla bla bla',
  }

  const boby: NewUser = {
    name: "Boby",
    email: "boby@test.com",
    password: "pass!123"
  }

  const bobyUser: User = {
    id: 1,
    name: "Boby",
    email: "boby@test.com",
    created_at: Date.now().toString(),
    updated_at: Date.now().toString()
  }

  describe('with topic loading ok', () => {

    beforeEach(async () => {
      await TestBed.configureTestingModule({
        imports: [MeComponent, AppRoutingModule, NoopAnimationsModule],
        providers: [
          provideHttpClient(),
          { provide: Router, useValue: routerMock },
          { provide: SessionService, useValue: sessionServiceStub },
          { provide: AuthService, useValue: authServiceStub },
          { provide: NotificationService, useValue: notifserviceStub },
          { provide: SubscribtionsService, useValue: subsServiceStub },
        ]
      })
        .compileComponents();
      subsServiceStub.getUserSubscribtions.and.returnValue(of([topic1]));
      fixture = TestBed.createComponent(MeComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    afterEach(() => {
      subsServiceStub.getUserSubscribtions.calls.reset();
      authServiceStub.updateMe.calls.reset();
      authServiceStub.getMe.calls.reset();
      subsServiceStub.unsubscribe.calls.reset();
      notifserviceStub.notifyError.calls.reset();
      sessionServiceStub.logIn.calls.reset();
      sessionServiceStub.logOut.calls.reset();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should load User Data in form', () => {
      expect(component.form.value.name).toEqual(bob.name);
      expect(component.form.value.name).toEqual(bob.name);
      expect(component.form.value.email).toEqual(bob.email);
      expect(component.form.value.password).toEqual('');
    });

    it('should load subscribed Topics', () => {
      expect(subsServiceStub.getUserSubscribtions).toHaveBeenCalledWith(1);
      expect(component.topics).toEqual([topic1]);
    });

    it('submit should send new user data and login again', () => {
      authServiceStub.updateMe.and.returnValue(of(jwtInfo));
      authServiceStub.getMe.and.returnValue(of(bobyUser));
      component.form.setValue(boby);
      component.submit();
      fixture.detectChanges();
      expect(authServiceStub.updateMe).toHaveBeenCalledWith(boby);
      expect(sessionServiceStub.logIn).toHaveBeenCalledWith(bobyUser);
    });

    it('when updateMe fails submit should notify error', () => {
      authServiceStub.updateMe.and.returnValue(throwError(() => error));
      component.form.setValue(boby);
      component.submit();
      fixture.detectChanges();
      expect(notifserviceStub.notifyError).toHaveBeenCalledWith("Erreur", error.message);
    });

    it('when getMe fails submit should notify error', () => {
      authServiceStub.updateMe.and.returnValue(of(jwtInfo));
      authServiceStub.getMe.and.returnValue(throwError(() => error));
      component.form.setValue(boby);
      component.submit();
      fixture.detectChanges();
      expect(notifserviceStub.notifyError).toHaveBeenCalledWith("Erreur", error.message);
    });

    it('unsubscribeTopic should unsubscribed from topic', () => {
      const response: ResponseMessage = { message: "good" };
      subsServiceStub.unsubscribe.and.returnValue(of(response))
      subsServiceStub.getUserSubscribtions.and.returnValue(of([]));
      component.unsubscribeTopic('java');
      fixture.detectChanges();
      expect(subsServiceStub.unsubscribe).toHaveBeenCalledWith(1, 'java');
      expect(subsServiceStub.getUserSubscribtions).toHaveBeenCalledWith(1);
      expect(component.topics).toHaveSize(0);
    });

    it('failing unsubscribeTopic should notify error', () => {
      const error = new HttpErrorResponse({ status: 404 });
      subsServiceStub.unsubscribe.and.returnValue(throwError(() => error))
      component.unsubscribeTopic('java');
      fixture.detectChanges();
      expect(notifserviceStub.notifyError).toHaveBeenCalledWith("Erreur", error.message);
    });

    it('logout should logout', () => {
      component.logout();
      expect(sessionServiceStub.logOut).toHaveBeenCalled();
      expect(routerMock.navigate).toHaveBeenCalledWith(['']);
    });

  });

  describe('with topic loading failure', () => {

    beforeEach(async () => {
      await TestBed.configureTestingModule({
        imports: [MeComponent, AppRoutingModule, NoopAnimationsModule],
        providers: [
          provideHttpClient(),
          { provide: Router, useValue: routerMock },
          { provide: SessionService, useValue: sessionServiceStub },
          { provide: AuthService, useValue: authServiceStub },
          { provide: NotificationService, useValue: notifserviceStub },
          { provide: SubscribtionsService, useValue: subsServiceStub },
        ]
      })
        .compileComponents();
      subsServiceStub.getUserSubscribtions.and.returnValue(throwError(() => error));
      fixture = TestBed.createComponent(MeComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should notify loading error', () => {
      expect(notifserviceStub.notifyError).toHaveBeenCalledWith("Erreur de chargement", error.message)
    });

  });
});