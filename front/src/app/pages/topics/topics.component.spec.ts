import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicsComponent } from './topics.component';
import { Observable } from 'rxjs';
import { TopicsService } from 'src/app/core/modules/openapi';

describe('TopicsComponent', () => {
  let component: TopicsComponent;
  let fixture: ComponentFixture<TopicsComponent>;

  const topicServiceSpy = jasmine.createSpyObj('TopicsService', ['getAllTopics']);

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

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ TopicsComponent ],
      providers: [
        { provide: TopicsService, useValue: topicServiceSpy }
      ],
    })
    .compileComponents();
    topicServiceSpy.getAllTopics.and.returnValue(new Observable(obs=>obs.next([topic1, topic2])))
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
    expect(topicServiceSpy.getAllTopics).toHaveBeenCalled();
  });
});
