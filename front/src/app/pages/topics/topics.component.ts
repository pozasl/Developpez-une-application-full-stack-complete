import { Component, OnInit } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { Observable, take } from 'rxjs';
import { SubscribtionsService, Topic, TopicsService } from 'src/app/core/modules/openapi';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-topics',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {
  $topics!: Observable<Topic[]>;

  constructor(
    private topicService: TopicsService,
    private subsService: SubscribtionsService,
    private sessionService: SessionService
  ) { }

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

  subscribeToTopic(ref: string):void {
    console.info(ref);
    const userId = this.sessionService.user?.id
    if (userId && ref) {
      this.subsService.subscribeToTopic(userId, ref)
      .subscribe({
        next: msg => {
          console.log(msg);
        },
        error: console.error
    });
    }
  }
}
