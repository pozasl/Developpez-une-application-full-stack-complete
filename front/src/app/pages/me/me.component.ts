import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from '@angular/material/icon';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiModule, User, SubscribtionsService, Topic, NewUser, AuthService } from 'src/app/core/modules/openapi';
import { mergeMap, Observable, take, tap } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';
import { BackBtnComponent } from 'src/app/components/ui/back-btn/back-btn.component';
import { AsyncPipe } from '@angular/common';
import { Router } from '@angular/router';

/**
 * User's informations page with subscribed topics
 */
@Component({
  selector: 'app-me',
  standalone: true,
  imports: [FormsModule, MatCardModule, ReactiveFormsModule, MatInputModule, MatFormFieldModule, MatIconModule, ApiModule, BackBtnComponent, AsyncPipe],
  templateUrl: './me.component.html',
  styleUrl: './me.component.scss'
})
export class MeComponent implements OnInit {
  public onError: Boolean = false;
  public hide: Boolean = true;
  private userId!: number;
  public user!: User;
  public $topics!: Observable<Topic[]>;

  public form = this.fb.group({
    name: ['', [Validators.required, Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: [''],
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private sessionService: SessionService,
    private subsService: SubscribtionsService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadUserData();
    this.getTopics();
  }

  /**
   * Submit user's informations modification
   */
  public submit() {
    const pass: string = this.form.value.password ? this.form.value.password : ""
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
          error: console.log
        }),
        mergeMap(jwt => this.authService.getMe()),
        take(1),
      ).subscribe({
        next: user => {
          this.sessionService.logIn(user);
          this.loadUserData();
        },
        error: console.log
      });
    }
  }

  /**
   * Unsubscribe user to topic
   * @param ref Topic's reference
   */
  public unsubscribeTopic(ref: string) {
    console.log("unsub", ref)
    this.subsService.unsubscribe(this.userId, ref).subscribe(response => {
      this.getTopics();
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
    this.$topics = this.subsService.getUserSubscribtions(this.userId);
  }

}
