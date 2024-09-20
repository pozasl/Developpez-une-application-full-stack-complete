import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import Topic from '../models/Topic';

@Injectable({
  providedIn: 'root'
})
export class TopicsService {

  private topicsUrl = "/api/topics";
  private $topics = new BehaviorSubject<Topic[] | null>(nulll);

  constructor(private htpp:HttpClient) { }

  getAllTopics():Observable<Topic> {
    return this.htpp.get<Topic[]>(this.topicsUrl)
  }
}
