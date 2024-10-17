import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { HttpErrorResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AuthInfo, AuthService, JwtInfo, User, UsersService } from 'src/app/core/modules/openapi';
import { SessionService } from 'src/app/services/session.service';
import { NotificationService } from 'src/app/services/notification.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  const routerMock = {
    navigate: jasmine.createSpy('navigate')
  };
  const authServiceStub = {
    login: jasmine.createSpy('login')
  };
  const sessionServiceStub = {
    logIn: jasmine.createSpy('logIn'),
    token: null
  };
  const usersServiceStub = {
    getUserById: jasmine.createSpy('getUserById')
  };
  const notifserviceStub = {
    notifyError: jasmine.createSpy('notifyError')
  };

  const jwtInfo: JwtInfo = {
    token: 'un_token',
    userId: 1
  }

  const bob: User = {
    id: 1,
    name: "Bob",
    email: "bob@test.com",
    created_at: Date.now().toString(),
    updated_at: Date.now().toString()
  }

  const authInfo:AuthInfo = {
    email: "bob@test.com",
    password: "pass1234"
  }

  const mockRoute = {
    snapshot: {
      paramMap: {
        get: jasmine.createSpy('get').and.returnValue(of(1))
      }
    }
  }

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [LoginComponent, NoopAnimationsModule],
      providers: [
        provideHttpClient(),
        { provide: ActivatedRoute, useValue: mockRoute },
        { provide: Router, useValue: routerMock },
        { provide: AuthService, useValue: authServiceStub },
        { provide: SessionService, useValue: sessionServiceStub },
        { provide: UsersService, useValue: usersServiceStub },
        { provide: NotificationService, useValue: notifserviceStub },
      ]
    });
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('submit should login', () => {
    component.form.value.email = authInfo.email;
    component.form.value.password = authInfo.password;
    authServiceStub.login.and.returnValue(of(jwtInfo));
    usersServiceStub.getUserById.and.returnValue(of(bob));
    component.submit();
    expect(authServiceStub.login).toHaveBeenCalledWith(authInfo);
    expect(usersServiceStub.getUserById).toHaveBeenCalledWith(1);
    expect(sessionServiceStub.logIn).toHaveBeenCalledWith(bob);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/topics']);
  });

  it('submit notify login failure', () => {
    const error = new HttpErrorResponse({ status: 404 })
    component.form.value.email = authInfo.email;
    component.form.value.password = authInfo.password;
    authServiceStub.login.and.returnValue(throwError(() => error))
    component.submit();
    expect(authServiceStub.login).toHaveBeenCalledWith(authInfo);
    expect(notifserviceStub.notifyError).toHaveBeenCalledWith("Login failed", error.message);
  });

  it('submit notify getUserById null', () => {
    component.form.value.email = authInfo.email;
    component.form.value.password = authInfo.password;
    authServiceStub.login.and.returnValue(of(jwtInfo));
    usersServiceStub.getUserById.and.returnValue(of(null));
    component.submit();
    expect(notifserviceStub.notifyError).toHaveBeenCalledWith("Login failed", "Empty user received");
  });

  it('submit notify getUserById failed', () => {
    const error = new HttpErrorResponse({ status: 404 })
    component.form.value.email = authInfo.email;
    component.form.value.password = authInfo.password;
    authServiceStub.login.and.returnValue(of(jwtInfo));
    usersServiceStub.getUserById.and.returnValue(throwError(() => error));
    component.submit();
    expect(notifserviceStub.notifyError).toHaveBeenCalledWith("Login failed", error.message);
  });
});
