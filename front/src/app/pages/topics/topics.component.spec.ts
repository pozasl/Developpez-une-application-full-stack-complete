import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicsComponent } from './topics.component';
import { of } from 'rxjs';
import { SubscribtionsService, TopicsService } from 'src/app/core/modules/openapi';
import { provideHttpClient } from '@angular/common/http';
import { SessionService } from 'src/app/services/session.service';

describe('TopicsComponent', () => {
  let component: TopicsComponent;
  let fixture: ComponentFixture<TopicsComponent>;

  const topic1 = {
    ref: 'java',
    name: 'Java',
    description: 'Java bla bla bla',
  }

  const topic2 = {
    ref: 'angular',
    name: 'Angular',
    description: 'Angular bla bla bla',
  }

  const sessionServiceStub = {
    user: {
      id: 1
    }
  };

  const topicServiceStub = {
    getAllTopics: jasmine.createSpy('getAllTopics'),
  }

  const subsServiceStub = {
    getUserSubscribtions: jasmine.createSpy('getUserSubscribtions'),
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ TopicsComponent ],
      providers: [
        { provide: TopicsService, useValue: topicServiceStub},
        { provide: SubscribtionsService, useValue: subsServiceStub},
        { provide: SessionService, useValue: sessionServiceStub},
        provideHttpClient()
      ],
    })
    .compileComponents();
    topicServiceStub.getAllTopics.and.returnValue(of([topic1, topic2]))
    subsServiceStub.getUserSubscribtions.and.returnValue(of([topic1, topic2]))
    fixture = TestBed.createComponent(TopicsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have topics', () => {
    expect(component.$topics).toBeTruthy();
  });

  it('at initialization it should fetch Topics', () => {
    component.ngOnInit();
    fixture.detectChanges();
    expect(topicServiceStub.getAllTopics).toHaveBeenCalled();
    expect(subsServiceStub.getUserSubscribtions).toHaveBeenCalledWith(1);
  });
});
