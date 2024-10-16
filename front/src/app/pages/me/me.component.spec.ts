import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MeComponent } from './me.component';
import { provideHttpClient } from '@angular/common/http';
import { AppRoutingModule } from 'src/app/app-routing.module';
import { SessionService } from 'src/app/services/session.service';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { SubscribtionsService } from 'src/app/core/modules/openapi';
import { Observable } from 'rxjs';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;

  const sessionServiceStub = {
    user: {
      id: 1
    }
  };

  const topic1 = {
    ref: 'java',
    name: 'Java',
    description: 'Java bla bla bla',
  }

  const subsServiceStub = {
    getUserSubscribtions: jasmine.createSpy('getUserSubscribtions'),
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MeComponent, AppRoutingModule, NoopAnimationsModule],
      providers: [
        provideHttpClient(),
        { provide: SessionService, useValue: sessionServiceStub},
        { provide: SubscribtionsService, useValue: subsServiceStub},
      ]
    })
    .compileComponents();
    subsServiceStub.getUserSubscribtions.and.returnValue(new Observable(obs=>obs.next([topic1])));
    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
