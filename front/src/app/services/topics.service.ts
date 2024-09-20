import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import Topic from '../models/Topic';

@Injectable({
  providedIn: 'root',
})
export class TopicsService {

  private topicsPath = "/api/topics";

  constructor(private http: HttpClient) { }

  getAllTopics():Observable<Topic[]> {
    return this.http.get<Topic[]>(this.topicsPath);
  }

  getTopicByRef(ref: String):Observable<Topic> {
    return this.http.get<Topic>(this.topicsPath + "/" + ref);
  }

}
