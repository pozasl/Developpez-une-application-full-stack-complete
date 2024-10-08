import { AsyncPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { RouterLink } from '@angular/router';
import { Observable, take } from 'rxjs';
import { FeedsService, Post } from 'src/app/core/modules/openapi';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [RouterLink, AsyncPipe, MatCardModule],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss'
})
export class FeedComponent implements OnInit{

  public $feed!: Observable<Post[]>;

  private userId!: number;

  constructor(
    private sessionService: SessionService,
    private feedsService: FeedsService
  ) {}

  ngOnInit(): void {
    if (this.sessionService.user && this.sessionService.user.id) {
      this.userId = this.sessionService.user?.id;
      this.loadFeed();
    }
  }
  
  private loadFeed() {
    this.$feed = this.feedsService.getUserFeed(this.userId);
    this.$feed.pipe(take(1)).subscribe({
      next: (posts) => {
        console.log("loaded feed's posts",posts);
      },
      error : console.error
    })
  }

}
