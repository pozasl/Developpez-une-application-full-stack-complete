import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PostDetailComponent } from './post-detail.component';
import { provideHttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { PostsService } from 'src/app/core/modules/openapi';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('PostDetailComponent', () => {
  let component: PostDetailComponent;
  let fixture: ComponentFixture<PostDetailComponent>;

  const post = {
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

  const sessionServiceStub = {
    user: {
      id: 1,
      name: "bob"
    }
  };

  const postsServiceStub = {
    getPostById: jasmine.createSpy("getPostById")
  }

  const mockRoute = {
    snapshot: {
      paramMap: {
        get: jasmine.createSpy('get').and.returnValue(of("aaaaa"))
      }
    }
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostDetailComponent, NoopAnimationsModule],
      providers: [provideHttpClient(),
        { provide: ActivatedRoute, useValue: mockRoute },
        { provide: SessionService, useValue: sessionServiceStub },
        { provide: PostsService, useValue: postsServiceStub }
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
});
