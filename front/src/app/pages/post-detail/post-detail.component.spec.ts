import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PostDetailComponent } from './post-detail.component';
import { HttpErrorResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { NewReply, Post, PostsService, Reply, ResponseMessage } from 'src/app/core/modules/openapi';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { NotificationService } from 'src/app/services/notification.service';

describe('PostDetailComponent', () => {
  let component: PostDetailComponent;
  let fixture: ComponentFixture<PostDetailComponent>;

  const response: ResponseMessage = { message: 'ok' };
  const error = new HttpErrorResponse({ status: 404 });

  const post: Post = {
    id: "670e88d1bff0e2678d90c296",
    title: "Test java",
    topic: {
      ref: "java",
      name: "Java",
      description: "Java topic description"
    },
    content: "Java bla bla bla",
    author: {
      userId: 2,
      userName: "Alice Wonderland"
    },
    replies: [],
    created_at: "2024-10-15T15:22:57.099Z"
  }

  const newReply: NewReply = {
    message: "Test de message",
    author: {userId: 1, userName: "Bob"}
  }

  const sessionServiceStub = {
    user: {
      id: 1,
      name: "Bob"
    }
  };

  const postsServiceStub = {
    getPostById: jasmine.createSpy("getPostById"),
    createReply: jasmine.createSpy("createReply")
  }

  const notifServiceStub = {
    notifyError: jasmine.createSpy("notifyError")
  }

  const mockRoute = {
    snapshot: {
      paramMap: {
        get: jasmine.createSpy('get').and.returnValue(post.id)
      }
    }
  }

  describe('when post load succeeded', () => {

    beforeEach(async () => {
      await TestBed.configureTestingModule({
        imports: [PostDetailComponent, NoopAnimationsModule],
        providers: [provideHttpClient(),
        { provide: ActivatedRoute, useValue: mockRoute },
        { provide: SessionService, useValue: sessionServiceStub },
        { provide: PostsService, useValue: postsServiceStub },
        { provide: NotificationService, useValue: notifServiceStub }
        ]
      })
        .compileComponents();
      postsServiceStub.getPostById.and.returnValue(of(post));
      fixture = TestBed.createComponent(PostDetailComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should have loaded post', () => {
      expect(postsServiceStub.getPostById).toHaveBeenCalledWith(post.id);
      expect(component.post).toEqual(post);
    });

    it('submit should send reply', () => {
      const udpatedPost: Post = {...post};
      const reply:Reply = {author: newReply.author, content: newReply.message, created_at:Date.now().toString()}
      udpatedPost.replies = [reply];
      postsServiceStub.createReply.and.returnValue(of(response));
      postsServiceStub.getPostById.and.returnValue(of(udpatedPost));
      component.form.setValue({message: "Test de message"});
      component.submit();
      fixture.detectChanges();
      expect(postsServiceStub.createReply).toHaveBeenCalledWith(post.id, newReply);
      expect(postsServiceStub.getPostById).toHaveBeenCalledWith(post.id);
      expect(component.post.replies).toEqual([reply]);
    });

    it('when createReply fails submit should notify error', () => {
      postsServiceStub.createReply.and.returnValue(throwError(() => error));
      component.form.setValue({message: "Test de message"});
      component.submit();
      fixture.detectChanges();
      expect(notifServiceStub.notifyError).toHaveBeenCalledWith("Erreur d'envoie", error.message)
    });


  });

  describe('when post load failed', () => {
    beforeEach(async () => {
      await TestBed.configureTestingModule({
        imports: [PostDetailComponent, NoopAnimationsModule],
        providers: [provideHttpClient(),
        { provide: ActivatedRoute, useValue: mockRoute },
        { provide: SessionService, useValue: sessionServiceStub },
        { provide: PostsService, useValue: postsServiceStub },
        { provide: NotificationService, useValue: notifServiceStub }
        ]
      })
        .compileComponents();
      postsServiceStub.getPostById.and.returnValue(throwError(() => error));
      fixture = TestBed.createComponent(PostDetailComponent);
      component = fixture.componentInstance;
      fixture.detectChanges();
    });

    it('it should notify error', () => {
      expect(notifServiceStub.notifyError).toHaveBeenCalledWith("Erreur de chargement", error.message);
    });

  });
});