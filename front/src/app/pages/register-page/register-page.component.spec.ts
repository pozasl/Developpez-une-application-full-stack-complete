import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterPageComponent } from './register-page.component';
import { HttpErrorResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AuthService, NewUser, ResponseMessage } from 'src/app/core/modules/openapi';
import { NotificationService } from 'src/app/services/notification.service';

describe('RegisterPageComponent', () => {
  let component: RegisterPageComponent;
  let fixture: ComponentFixture<RegisterPageComponent>;

  const routerMock = {
    navigate: jasmine.createSpy('navigate')
  };

  const authServiceStub = {
    register: jasmine.createSpy('register')
  };

  const notifserviceStub = {
    notifyError: jasmine.createSpy('notifyError')
  };

  const bob:NewUser = {
    name: "Bob",
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
      imports: [RegisterPageComponent, NoopAnimationsModule],
      providers: [
        provideHttpClient(),
        { provide: ActivatedRoute, useValue: mockRoute },
        { provide: Router, useValue: routerMock },
        { provide: AuthService, useValue: authServiceStub },
        { provide: NotificationService, useValue: notifserviceStub },
      ]
    });
    fixture = TestBed.createComponent(RegisterPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('submit should register', () => {
    const response: ResponseMessage = { message: "Ok" };
    authServiceStub.register.and.returnValue(of(response));
    component.form.setValue(bob);
    component.submit();
    fixture.detectChanges();
    expect(authServiceStub.register).toHaveBeenCalledWith(bob);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('submit should register', () => {
    const error = new HttpErrorResponse({ status: 409 });
    authServiceStub.register.and.returnValue(throwError(() => error));
    component.form.setValue(bob);
    component.submit();
    fixture.detectChanges();
    expect(authServiceStub.register).toHaveBeenCalledWith(bob);
    expect(notifserviceStub.notifyError).toHaveBeenCalledWith("Erreur d'enregistrement", error.message);
  });
  
});
