import { Component, OnInit } from '@angular/core';
import { AsyncPipe } from '@angular/common';
import { map, Observable, take, zip } from 'rxjs';
import { SubscribtionsService, Topic, TopicsService } from 'src/app/core/modules/openapi';
import { SessionService } from 'src/app/services/session.service';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { NotificationService } from 'src/app/services/notification.service';

/**
 * Topics component displays the Topics list for subscribtions
 */
@Component({
  selector: 'app-topics',
  standalone: true,
  imports: [AsyncPipe, MatIconModule, MatButtonModule, MatCardModule],
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {
  topicSubs = new Map<string, boolean>();
  $topics!: Observable<Topic[]>;

  private userId: number | null = null;

  constructor(
    private topicService: TopicsService,
    private subsService: SubscribtionsService,
    private sessionService: SessionService,
    private notificationService: NotificationService,
  ) { }

  ngOnInit(): void {
    if (this.sessionService.user && this.sessionService.user.id) {
      this.userId = this.sessionService.user.id
      this.loadUnsubscribedTopics(this.userId);
    }
  }

  /**
   * Subscribe to a Topic
   *
   * @param ref The topic ref
   */
  subscribeToTopic(ref: string): void {
    console.info(ref);
    if (this.userId && ref) {
      this.subsService.subscribeToTopic(this.userId, ref)
        .subscribe({
          next: () => {
            if (this.userId)
              this.loadUnsubscribedTopics(this.userId)
          },
          error: err => this.notificationService.notifyError("Erreur d'inscription", err.message)
        });
    }
  }

  /**
   * Load the Topics and create mapping for subscribed ones
   *
   * @param userId 
   */
  private loadUnsubscribedTopics(userId: number) {
    this.$topics = zip(
      this.topicService.getAllTopics(),
      this.subsService.getUserSubscribtions(userId)
    ).pipe(
      map(
        ([topics, subs]) => {
          topics.forEach(t => this.topicSubs.set(t.ref!, subs.map(s => s.ref).indexOf(t.ref) < 0));
          return topics;
        },
      ));
    this.$topics.pipe(take(1)).subscribe({
      next: () => {
        console.info("Topic data loaded");
      },
      error: (e: Error) => {
        this.notificationService.notifyError("Erreur de chargement", e.message);
      },
    });

  }
}
