import { AsyncPipe, DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { Observable, take } from 'rxjs';
import { FeedsService, Post } from 'src/app/core/modules/openapi';
import { SessionService } from 'src/app/services/session.service';


const enum Sort {
  ASC = 'asc',
  DESC = 'desc'
}

@Component({
  selector: 'app-feed',
  standalone: true,
  imports: [RouterLink, AsyncPipe, MatCardModule, DatePipe, MatIconModule, MatButtonModule],
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.scss'
})
export class FeedComponent implements OnInit{

  public $feed!: Observable<Post[]>;
  public sort = Sort.DESC

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

  public switchSort() {
    this.sort = this.sort == Sort.DESC ? Sort.ASC : Sort.DESC
    this.loadFeed();
  }
  
  private loadFeed() {
    this.$feed = this.feedsService.getUserFeed(this.userId, this.sort);
    this.$feed.pipe(take(1)).subscribe({
      next: (posts) => {
        console.log("loaded feed's posts",posts);
      },
      error : console.error
    })
  }

}
