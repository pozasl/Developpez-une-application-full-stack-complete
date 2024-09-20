import { Component, OnInit } from '@angular/core';
import { CommonModule, AsyncPipe } from '@angular/common';
import { Observable, take } from 'rxjs';
import Topic from 'src/app/models/Topic';
import { TopicsService } from 'src/app/services/topics.service';

@Component({
  selector: 'app-topics',
  standalone: true,
  imports: [CommonModule, AsyncPipe],
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {
  $topics!: Observable<Topic[]>;
  ready!: Boolean;

  constructor(private topicService: TopicsService) { }

  ngOnInit(): void {
    this.ready = false
    this.$topics = this.topicService.getAllTopics();
    this.$topics.pipe(take(1)).subscribe({
      next: () => {
        console.info("Topic data loaded");
        this.ready = true
      },
      error: (e: Error) => {
        console.error(e.message)
      },
    });
  }
}
