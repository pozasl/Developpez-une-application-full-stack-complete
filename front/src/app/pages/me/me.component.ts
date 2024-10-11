import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from '@angular/material/icon';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiModule, User, SubscribtionsService, Topic, NewUser, AuthService } from 'src/app/core/modules/openapi';
import { mergeMap, take, tap } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { AsyncPipe } from '@angular/common';
import { Router } from '@angular/router';
import { emptyOrMinSizeValidators } from 'src/app/shared/validators/emptyOrMinSizeValidators';
import { MatButtonModule } from '@angular/material/button';
import { NotificationService } from 'src/app/services/notification.service';

/**
 * User's informations page with subscribed topics
 */
@Component({
  selector: 'app-me',
  standalone: true,
  imports: [FormsModule, MatCardModule, ReactiveFormsModule, MatInputModule, MatFormFieldModule, MatIconModule, ApiModule, MatButtonModule, MatDividerModule, AsyncPipe],
  templateUrl: './me.component.html',
  styleUrl: './me.component.scss'
})
export class MeComponent implements OnInit {
  public onError: Boolean = false;
  public hide: Boolean = true;
  private userId!: number;
  public user!: User;
  public topics!: Topic[];

  public form = this.fb.group({
    name: ['', [Validators.required, Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [emptyOrMinSizeValidators(6)]],
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private sessionService: SessionService,
    private subsService: SubscribtionsService,
    private router: Router,
    private notificationService: NotificationService
  ) { }

  ngOnInit(): void {
    this.loadUserData();
    this.getTopics();
  }

  /**
   * Submit user's informations modification and refresh session with a new token
   */
  public submit() {
    const pass: string = this.form.value.password ? this.form.value.password : ''
    if (this.form.value.name && this.form.value.email) {
      const newUser: NewUser = {
        name: this.form.value.name,
        email: this.form.value.email,
        password: pass
      };
      this.authService.updateMe(newUser).pipe(
        tap({
          next: jwt => {
            if (jwt.token) {
              this.sessionService.token = jwt.token;
            }
            else {
              console.log("No new token");
            }
          },
          error: err => this.notificationService.notifyError("Erreur", err.message)
        }),
        mergeMap(jwt => this.authService.getMe()),
        take(1),
      ).subscribe({
        next: user => {
          this.sessionService.logIn(user);
          this.loadUserData();
        },
        error: err => this.notificationService.notifyError("Erreur", err.message)
      });
    }
  }

  /**
   * Unsubscribe user to topic
   * @param ref Topic's reference
   */
  public unsubscribeTopic(ref: string) {
    console.log("unsub", ref)
    this.subsService.unsubscribe(this.userId, ref).subscribe({
      next: response => {
        console.log(response);
        this.getTopics();
      },
      error: err => this.notificationService.notifyError("Erreur", err.message)
    });
  }

  /**
   * Logout an send back to home pahe
   */
  public logout(): void {
    this.sessionService.logOut();
    this.router.navigate(['']);
  }

  /**
   * Load user's data and fill the form with
   */
  private loadUserData() {
    if (this.sessionService.user && this.sessionService.user.id) {
      this.user = this.sessionService.user;
      this.userId = this.sessionService.user.id;
      this.form.setValue({
        name: this.user.name || "",
        email: this.user.email || "",
        password: ''
      })
    }
  }

  /**
   * Get user's subscribed topics
   */
  private getTopics() {
    this.subsService.getUserSubscribtions(this.userId).pipe(take(1)).subscribe({
      next: topics => this.topics = topics,
      error: err => this.notificationService.notifyError("Erreur de chargement", err.message)
    });
  }

}
