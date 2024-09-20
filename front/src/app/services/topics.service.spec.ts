import { TestBed } from '@angular/core/testing';

import { TopicsService } from './topics.service';
import { Observable } from 'rxjs';
import Topic from '../models/Topic';
import { HttpClient } from '@angular/common/http';

describe('TopicsService', () => {
  let service: TopicsService;

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

  const httpClientSpy = jasmine.createSpyObj('HttpClient', ['get', 'post', 'delete']);

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{ provide: HttpClient, useValue: httpClientSpy }]
    });
    service = TestBed.inject(TopicsService);
  });

  afterEach(() => {
    httpClientSpy.get.calls.reset();
  });
 

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all topics', () => {
    const $topics = new Observable<Topic[]>(obs => obs.next([topic1, topic2]))
    httpClientSpy.get.and.returnValue($topics);
    const topics: Observable<Topic[]> = service.getAllTopics();
    expect(topics).toEqual($topics);
    expect(httpClientSpy.get).toHaveBeenCalledWith("/api/topics");
    
  });

  it('should get a topic by its ref', () => {
    const $topic = new Observable<Topic>(obs => obs.next(topic2))
    httpClientSpy.get.and.returnValue($topic);
    const topic: Observable<Topic> = service.getTopicByRef("angular");
    expect(topic).toEqual($topic);
    expect(httpClientSpy.get).toHaveBeenCalledWith("/api/topics/angular");
  });

  it('should subscribe to a Topic', () => {
    httpClientSpy.post.and.returnValue(new Observable(obs=>obs.next()));
    service.subscribe("angular", "0123456789abcdef");
    expect(httpClientSpy.post).toHaveBeenCalledWith("/api/topics/angular/subscribers", {id: "0123456789abcdef"});
  });

  it('should unsubscribe to a Topic', () => {
    httpClientSpy.delete.and.returnValue(new Observable(obs=>obs.next()));
    const topic: Observable<Topic> = service.getTopicByRef("angular");
    service.unSubscribe("angular", "0123456789abcdef");
    expect(httpClientSpy.delete).toHaveBeenCalledWith("/api/topics/angular/subscribers/0123456789abcdef");
  });

});
