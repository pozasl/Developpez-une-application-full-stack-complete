import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostComponent } from './post.component';
import { provideHttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { TopicsService } from 'src/app/core/modules/openapi';

describe('PostComponent', () => {
  let component: PostComponent;
  let fixture: ComponentFixture<PostComponent>;

  const mockRoute = {
    snapshot: {
      paramMap: {
        get: jasmine.createSpy('get').and.returnValue(of(1))
      }
    }
  }

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

  const topicServiceStub = {
    getAllTopics: jasmine.createSpy('getAllTopics'),
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PostComponent, NoopAnimationsModule],
      providers: [provideHttpClient(),
        { provide: ActivatedRoute, useValue: mockRoute },
        { provide: TopicsService, useValue: topicServiceStub }
      ]
    })
    .compileComponents();
    topicServiceStub.getAllTopics.and.returnValue(of([topic1, topic2]));
    fixture = TestBed.createComponent(PostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
