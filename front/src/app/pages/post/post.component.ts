import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';
import { Router, RouterLink } from '@angular/router';
import { Observable, take } from 'rxjs';
import { Author, NewPost, PostsService, Topic, TopicsService } from 'src/app/core/modules/openapi';
import { SessionService } from 'src/app/services/session.service';
import { AsyncPipe } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { NotificationService } from 'src/app/services/notification.service';

@Component({
  selector: 'app-post',
  standalone: true,
  imports: [FormsModule, MatCardModule, ReactiveFormsModule, MatButtonModule, MatIconModule, RouterLink, MatInputModule, MatFormFieldModule, MatIconModule, MatSelectModule, AsyncPipe],
  templateUrl: './post.component.html',
  styleUrl: './post.component.scss'
})
export class PostComponent implements OnInit{

  public onError = false;
  public $topics!: Observable<Topic[]>;
  private topicsMap!: Map<string, Topic>;

  private author!: Author;
  
  public form = this.fb.group({
    topic: ['', [Validators.required]],
    title: ['', [Validators.required, Validators.min(10),  Validators.max(255)]],
    content: ['', [Validators.required, Validators.min(10),  Validators.max(255)],]
  });

  constructor(
    private postsService: PostsService,
    private sessionService: SessionService,
    private topicsService: TopicsService,
    private router: Router,
    private fb: FormBuilder,
    private notificationService: NotificationService
  ) {}

  ngOnInit(): void {
    if (this.sessionService.user && this.sessionService.user.id && this.sessionService.user.name) {
      this.author = {
        userId: this.sessionService.user.id,
        userName: this.sessionService.user.name
      }
    }
    this.loadTopics();
  }

  public submit() {
    if (this.form.value.topic && this.form.value.title && this.form.value.content) {
      const post: NewPost = {
        topic: this.topicsMap.get(this.form.value.topic)!,
        title: this.form.value.title,
        content: this.form.value.content,
        author: this.author
      };
      this.postsService.createPost(post).pipe(take(1)).subscribe({
        next: () => {
          this.router.navigate(['/feed']);
        },
        error: (err) => {
          this.notificationService.notifyError("Erreur", err.message);
          this.onError = true;
        }
      });
    }  
  }

  private loadTopics() {
    this.$topics = this.topicsService.getAllTopics();
    this.$topics.pipe(
      take(1)
    ).subscribe({
      next: topics => {
        console.log("topics loaded", topics);
        this.topicsMap = new Map<string, Topic>;
        topics.forEach(topic => this.topicsMap.set(topic.ref!, topic));
      },
      error: e => {
        this.notificationService.notifyError("Erreur", e.message);
        this.onError = true;
      }
    })
  }
}