import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicsComponent } from './topics.component';
import { of, take, throwError } from 'rxjs';
import { ResponseMessage, SubscribtionsService, TopicsService } from 'src/app/core/modules/openapi';
import { HttpErrorResponse, provideHttpClient } from '@angular/common/http';
import { SessionService } from 'src/app/services/session.service';
import { NotificationService } from 'src/app/services/notification.service';

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
    subscribeToTopic: jasmine.createSpy('subscribeToTopic'),
  }

  const notifserviceStub = {
    notifyError: jasmine.createSpy('notifyError')
  };

  const error = new HttpErrorResponse({ status: 404 });
  const response: ResponseMessage = { message: "Ok" };

  describe('when getAllTopics succeeded', () => {

    beforeEach(async () => {
      await TestBed.configureTestingModule({
        imports: [TopicsComponent],
        providers: [
          { provide: TopicsService, useValue: topicServiceStub },
          { provide: SubscribtionsService, useValue: subsServiceStub },
          { provide: SessionService, useValue: sessionServiceStub },
          { provide: NotificationService, useValue: notifserviceStub },
          provideHttpClient()
        ],
      })
        .compileComponents();
      topicServiceStub.getAllTopics.and.returnValue(of([topic1, topic2]));
      subsServiceStub.getUserSubscribtions.and.returnValue(of([topic1]));
      fixture = TestBed.createComponent(TopicsComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    afterAll(() => {
      topicServiceStub.getAllTopics.calls.reset();
      subsServiceStub.getUserSubscribtions.calls.reset()
    })

    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should have topics', () => {
      component.$topics.pipe(take(1)).subscribe({
        next: topics => expect(topics).toEqual([topic1, topic2])
      });
    });

    it('should fetch Topics and map subscribed ones', () => {
      const map = new Map<string, boolean>();
      map.set("java", false)
      map.set("angular", true)
      expect(topicServiceStub.getAllTopics).toHaveBeenCalled();
      expect(subsServiceStub.getUserSubscribtions).toHaveBeenCalledWith(1);
      expect(component.topicSubs).toEqual(map);
    });

    it('subscribeToTopic should subscribe to Topic', () => {
      subsServiceStub.subscribeToTopic.and.returnValue(of(response))
      component.subscribeToTopic('angular');
      expect(topicServiceStub.getAllTopics).toHaveBeenCalled();
      expect(subsServiceStub.getUserSubscribtions).toHaveBeenCalledWith(1);
      expect(subsServiceStub.subscribeToTopic).toHaveBeenCalledWith(1, 'angular');
    });

    it('when subscribtion fails subscribeToTopic should notify error', () => {
      subsServiceStub.subscribeToTopic.and.returnValue(throwError(() => error))
      component.subscribeToTopic('angular');
      expect(notifserviceStub.notifyError).toHaveBeenCalledWith("Erreur d'inscription", error.message);
    });

  });

  describe('when getAllTopics fails', () => {

    beforeEach(async () => {
      await TestBed.configureTestingModule({
        imports: [TopicsComponent],
        providers: [
          { provide: TopicsService, useValue: topicServiceStub },
          { provide: SubscribtionsService, useValue: subsServiceStub },
          { provide: SessionService, useValue: sessionServiceStub },
          { provide: NotificationService, useValue: notifserviceStub },
          provideHttpClient()
        ],
      })
        .compileComponents();
      topicServiceStub.getAllTopics.and.returnValue(throwError(() => error));
      subsServiceStub.getUserSubscribtions.and.returnValue(of([topic1]));
      fixture = TestBed.createComponent(TopicsComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should notify error', () => {
      expect(notifserviceStub.notifyError).toHaveBeenCalledWith("Erreur de chargement", error.message);
    });
  });

  describe('when getUserSubscribtions fails', () => {

    beforeEach(async () => {
      await TestBed.configureTestingModule({
        imports: [TopicsComponent],
        providers: [
          { provide: TopicsService, useValue: topicServiceStub },
          { provide: SubscribtionsService, useValue: subsServiceStub },
          { provide: SessionService, useValue: sessionServiceStub },
          { provide: NotificationService, useValue: notifserviceStub },
          provideHttpClient()
        ],
      })
        .compileComponents();
      topicServiceStub.getAllTopics.and.returnValue(of([topic1, topic2]));
      subsServiceStub.getUserSubscribtions.and.returnValue(throwError(() => error));
      fixture = TestBed.createComponent(TopicsComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should notify error', () => {
      expect(notifserviceStub.notifyError).toHaveBeenCalledWith("Erreur de chargement", error.message);
    });
  });

});
