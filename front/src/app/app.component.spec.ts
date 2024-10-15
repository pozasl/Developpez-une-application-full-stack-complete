import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { of } from 'rxjs';
import { SessionService } from './services/session.service';
import { NotificationService } from './services/notification.service';
import { MatDialog } from '@angular/material/dialog';
import { RouterLink } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';

describe('AppComponent', () => {

  const sessionServiceStub = {
    $logged: jasmine.createSpy('$logged'),
    resume: jasmine.createSpy('resume')
  };
  const notificationServiceStub = {
    $error: jasmine.createSpy('$error')
  }
  const dialogStub = {
    open: jasmine.createSpy('open')
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        NoopAnimationsModule,
        RouterLink,
        MatSidenavModule,
        MatListModule,
        MatIconModule
      ],
      declarations: [
        AppComponent
      ],
      providers : [
        { provide: SessionService, useValue: sessionServiceStub },
        { provide: NotificationService, useValue: notificationServiceStub },
        { provide: MatDialog, useValue: dialogStub },
      ]
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'MDD'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('MDD');
  });

  it(`should return the logged state as a Boolean Observable`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    sessionServiceStub.$logged = jasmine.createSpy('$logged').and.returnValue(of(true));
    app.$isLogged().subscribe(is => {
      expect(is).toBeTrue();
      expect(sessionServiceStub.$logged).toHaveBeenCalled();
    });
  });

  it(`should open an error dialog when on error`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    notificationServiceStub.$error = jasmine.createSpy('$logged').and.returnValue(of({name: "Error", message: "I'm an error"}));
    app.ngOnInit();
    fixture.detectChanges();
    expect(dialogStub.open).toHaveBeenCalled();
  });

});
