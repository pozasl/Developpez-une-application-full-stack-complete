import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import Topic from '../models/Topic';

@Injectable({
  providedIn: 'root'
})
export class TopicsService {

  private topicsPath = "/api/topics";
  // private $topics = new BehaviorSubject<Topic[] | null>(null);

  constructor(private htpp:HttpClient) { }

  getAllTopics():Observable<Topic[]> {
    return this.htpp.get<Topic[]>(this.topicsPath);
  }

  getTopicByRef(ref: String):Observable<Topic> {
    return this.htpp.get<Topic>(this.topicsPath + "/" + ref);
  }

}
