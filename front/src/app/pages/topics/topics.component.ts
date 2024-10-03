import { Component, OnInit } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { concat, filter, first, map, mergeMap, Observable, take, zip } from 'rxjs';
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

  private userId: number | null = null;

  constructor(
    private topicService: TopicsService,
    private subsService: SubscribtionsService,
    private sessionService: SessionService
  ) { }

  ngOnInit(): void {
    if (this.sessionService.user && this.sessionService.user.id)
    {
      this.userId = this.sessionService.user.id
      this.loadUnsubscribedTopics(this.userId);
    }
  }

  subscribeToTopic(ref: string):void {
    console.info(ref);
    if (this.userId && ref) {
      this.subsService.subscribeToTopic(this.userId, ref)
      .subscribe({
        next: msg => {
          console.log(msg);
          if (this.userId)
            this.loadUnsubscribedTopics(this.userId)
        },
        error: console.error
    });
    }
  }

  private loadUnsubscribedTopics(userId: number) {
    this.$topics = zip(
      this.topicService.getAllTopics(),
      this.subsService.getUserSubscribtions(userId)
    ).pipe(
      first(),
      map(([topics,subs]) => topics.filter((t)=> subs.indexOf(t) < 0))
    );
    this.$topics.pipe(first()).subscribe({
      next: () => {
        console.info("Topic data loaded");
      },
      error: (e: Error) => {
        console.error(e.message)
      },
    });

  }
}
