
import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { take } from 'rxjs';
import { Author, NewReply, Post, PostsService, Reply } from 'src/app/core/modules/openapi';
import { SessionService } from 'src/app/services/session.service';

/**
 * Displays the post's content/informations and display/create comments for this post 
 */
@Component({
  selector: 'app-post-detail',
  standalone: true,
  imports: [FormsModule, MatCardModule, ReactiveFormsModule, RouterLink, MatInputModule, MatFormFieldModule, MatIconModule, MatButtonModule,  MatSelectModule, DatePipe],
  templateUrl: './post-detail.component.html',
  styleUrl: './post-detail.component.scss'
})
export class PostDetailComponent implements OnInit{

  public onError = false;
  public post!: Post;
  public form = this.fb.group({
    message: ['', [Validators.required, Validators.min(2), Validators.max(255)]]
  })

  private postId!: string;
  private author!: Author;

  constructor(
    private postsService: PostsService,
    private sessionService: SessionService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
   this.postId = this.route.snapshot.paramMap.get('id')!;
   if (this.sessionService.user && this.sessionService.user.id && this.sessionService.user.name) {
    this.author = {
      userId: this.sessionService.user.id,
      userName: this.sessionService.user.name
    }
  }
   this.loadPost();
  }

  /**
   * Submit a comment for this post
   */
  public submit() {
    if (this.post.id && this.form.value.message) {
      const reply: NewReply = {
        message: this.form.value.message,
        author: this.author
      }
      this.postsService.createReply(this.post.id ,  reply).pipe(take(1)).subscribe({
        next: response => {
          console.log(response);
        },
        error: e => {
          console.log(e);
          this.onError = true;
        }
      })
    }
  }

  /**
   * Load the post data
   */
  private loadPost() {
    this.postsService.getPostById(this.postId).pipe(take(1)).subscribe({
      next: post=> {
        console.log("Post loaded", post);
        this.post = post
      },
      error: e => {
        console.log(e);
        this.onError = true;
      }
    })
  } 

}
