import { Component, OnInit } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { Observable, take } from 'rxjs';
import Topic from 'src/app/models/Topic';
import { TopicsService } from 'src/app/services/topics.service';

@Component({
  selector: 'app-topics',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {
  $topics!: Observable<Topic[]>;

  constructor(private topicService: TopicsService) { }

  ngOnInit(): void {
    this.$topics = this.topicService.getAllTopics();
    this.$topics.pipe(take(1)).subscribe({
      next: () => {
        console.info("Topic data loaded");
      },
      error: (e: Error) => {
        console.error(e.message)
      },
    });
  }

  subscribeToTopic(topicRef: String):void {
    console.info(topicRef);
  }
}
