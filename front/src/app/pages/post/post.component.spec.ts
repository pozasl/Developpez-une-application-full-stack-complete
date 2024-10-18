import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostComponent } from './post.component';
import { HttpErrorResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NewPost, PostsService, ResponseMessage, Topic, TopicsService } from 'src/app/core/modules/openapi';
import { NotificationService } from 'src/app/services/notification.service';
import { SessionService } from 'src/app/services/session.service';

describe('PostComponent', () => {
  let component: PostComponent;
  let fixture: ComponentFixture<PostComponent>;

  const response: ResponseMessage = { message: 'ok' };
  const error = new HttpErrorResponse({ status: 404 });

  const mockRoute = {
    snapshot: {
      paramMap: {
        get: jasmine.createSpy('get').and.returnValue(of(1))
      }
    }
  }

  const topic1: Topic = {
    ref: 'java',
    name: 'Java',
    description: 'Java bla bla bla',
  }

  const topic2: Topic = {
    ref: 'angular',
    name: 'Angular',
    description: 'Angular bla bla bla',
  }

  const newPost: NewPost = {
    author: { userId: 1, userName: 'Bob' },
    title: 'Test java',
    content: 'Java bla bla bla',
    topic : topic1
  }

  const sessionServiceStub = {
    user: {
      id: 1,
      name: 'Bob'
    }
  };

  const routerMock = {
    navigate: jasmine.createSpy('navigate'),
  }

  const topicServiceStub = {
    getAllTopics: jasmine.createSpy('getAllTopics'),
  }

  const postsServiceStub = {
    createPost: jasmine.createSpy('createPost'),
  }

  const notifserviceStub = {
    notifyError: jasmine.createSpy('notifyError')
  };

  describe(' when load topic succeeded', () => {

    beforeEach(async () => {
      await TestBed.configureTestingModule({
        imports: [PostComponent, NoopAnimationsModule],
        providers: [provideHttpClient(),
        { provide: Router, useValue: routerMock },
        { provide: ActivatedRoute, useValue: mockRoute },
        { provide: SessionService, useValue: sessionServiceStub },
        { provide: TopicsService, useValue: topicServiceStub },
        { provide: PostsService, useValue: postsServiceStub },
        { provide: NotificationService, useValue: notifserviceStub }
        ]
      })
        .compileComponents();
      topicServiceStub.getAllTopics.and.returnValue(of([topic1, topic2]));
      postsServiceStub.createPost.and.returnValue(of())
      fixture = TestBed.createComponent(PostComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should load topics', (done) => {
      component.$topics.subscribe({
        next: topics => {
          expect(topics).toEqual([topic1, topic2]);
          done();
        }
      })
    });

    it('submit should send Post and redirect to /feed', () => {
      postsServiceStub.createPost.and.returnValue(of(response));
      component.form.setValue({
        topic: newPost.topic.ref!,
        title: newPost.title,
        content: newPost.content
      });
      component.submit();
      fixture.detectChanges();
      expect(postsServiceStub.createPost).toHaveBeenCalledWith(newPost);
      expect(routerMock.navigate).toHaveBeenCalledWith(['/feed']);
    });
    
    it('submit should send Post and redirect to /feed', () => {
      postsServiceStub.createPost.and.returnValue(throwError(() => error));
      component.form.setValue({
        topic: newPost.topic.ref!,
        title: newPost.title,
        content: newPost.content
      });
      component.submit();
      fixture.detectChanges();
      expect(notifserviceStub.notifyError).toHaveBeenCalledWith("Erreur", error.message);
    });

  });
  describe('when load topic failed', () => {

    beforeEach(async () => {
      await TestBed.configureTestingModule({
        imports: [PostComponent, NoopAnimationsModule],
        providers: [provideHttpClient(),
        { provide: ActivatedRoute, useValue: mockRoute },
        { provide: TopicsService, useValue: topicServiceStub },
        { provide: PostsService, useValue: postsServiceStub },
        { provide: NotificationService, useValue: notifserviceStub }
        ]
      })
        .compileComponents();
      topicServiceStub.getAllTopics.and.returnValue(throwError(() => error));
      fixture = TestBed.createComponent(PostComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('it should notify error', () => {
      expect(notifserviceStub.notifyError).toHaveBeenCalledWith('Erreur', error.message);
    });

  });

});
