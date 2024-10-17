import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedComponent } from './feed.component';
import { HttpErrorResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { of, take, throwError } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { FeedsService } from 'src/app/core/modules/openapi';
import { NotificationService } from 'src/app/services/notification.service';

describe('FeedComponent', () => {
  let component: FeedComponent;
  let fixture: ComponentFixture<FeedComponent>;

  const post1 = {
    id: "670e88d1bff0e2678d90c296",
    title: "Test java",
    topic: {
      ref: "java",
      name: "Java",
      description: "Java topic description"
    },
    content: "Java bla bla bla",
    author: {
      userId: 1,
      userName: "Alice Wonderland"
    },
    replies: [],
    created_at: "2024-10-15T15:22:57.099Z"
  }

  const post2 = {
    id: "670e88d1bff0e2678d90c297",
    title: "Test angular",
    topic: {
      ref: "angular",
      name: "Angular",
      description: "Angular topic description"
    },
    content: "Angular bla bla bla",
    author: {
      userId: 1,
      userName: "Alice Wonderland"
    },
    replies: [],
    created_at: "2024-11-15T15:22:57.099Z"
  }

  const mockRoute = {
    snapshot: {
      paramMap: {
        get: jasmine.createSpy('get').and.returnValue(of(1))
      }
    }
  }

  const sessionServiceStub = {
    user: { id: 1 }
  };

  const feedsServiceStub = {
    getUserFeed: jasmine.createSpy('getUserFeed')
  };

  const notifServiceStub = {
    notifyError: jasmine.createSpy('getUserFeed')
  };

  describe('with successfull feed loading', () => {

    beforeEach(async () => {
      await TestBed.configureTestingModule({
        imports: [FeedComponent],
        providers: [provideHttpClient(),
        { provide: ActivatedRoute, useValue: mockRoute },
        { provide: SessionService, useValue: sessionServiceStub },
        { provide: FeedsService, useValue: feedsServiceStub },
        { provide: NotificationService, useValue: notifServiceStub },
        ]
      })
        .compileComponents();
      feedsServiceStub.getUserFeed.and.returnValue(of([post1, post2]));
      fixture = TestBed.createComponent(FeedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    afterEach(() => {
      feedsServiceStub.getUserFeed.calls.reset();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('default sort should be desc', () => {
      expect(component.sort).toEqual('desc');
    });

    it('should load feeds data', (done) => {
      component.$feed.pipe(take(1)).subscribe({
        next: feeds => {
          expect(feeds).toHaveSize(2);
          expect(feeds.at(0)).toEqual(post1);
          expect(feeds.at(1)).toEqual(post2);
          expect(feedsServiceStub.getUserFeed).toHaveBeenCalledWith(1, 'desc');
          done();
        }
      });
    });

    it('switchSort change order', () => {
      component.switchSort();
      fixture.detectChanges();
      expect(component.sort).toEqual('asc');
    });
  });

  describe('with failed feed loading', () => {

    const error = new HttpErrorResponse({ status: 404 })

    beforeEach(async () => {
      await TestBed.configureTestingModule({
        imports: [FeedComponent],
        providers: [provideHttpClient(),
        { provide: ActivatedRoute, useValue: mockRoute },
        { provide: SessionService, useValue: sessionServiceStub },
        { provide: FeedsService, useValue: feedsServiceStub },
        { provide: NotificationService, useValue: notifServiceStub },
        ]
      })
        .compileComponents();
      feedsServiceStub.getUserFeed.and.returnValue(throwError(() => error))
      fixture = TestBed.createComponent(FeedComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    afterEach(() => {
      feedsServiceStub.getUserFeed.calls.reset();
    });

    it('when getUserFeed fails it should notify error', () => {
      expect(notifServiceStub.notifyError).toHaveBeenCalledWith("Erreur de chargement", error.message);
    });
  });
});
