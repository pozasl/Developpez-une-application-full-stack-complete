import { Component, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from '@angular/material/icon';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiModule, UsersService, User, SubscribtionsService, Topic, NewUser, AuthService } from 'src/app/core/modules/openapi';
import { first, Observable } from 'rxjs';
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
export class MeComponent implements OnInit{
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
    private usersService: UsersService,
    private sessionService: SessionService,
    private subsService: SubscribtionsService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if ( this.sessionService.user && this.sessionService.user.id)
    {
      this.userId = this.sessionService.user.id;
      this.usersService.getUserById(this.userId).pipe(first()).subscribe( u => {
        this.user = u;
        this.form.value.name = u.name;
        this.form.value.email = u.email;
        this.form.value.password = "";
      });
      this.getTopics();
    }
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
        password : pass
      };
      this.usersService.updateUserById(this.userId, newUser).pipe(first()).subscribe( u => {
        this.user = u;
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
   * Get user's subscribed topics
   */
  private getTopics() {
    this.$topics = this.subsService.getUserSubscribtions(this.userId);
  }

  /**
   * 
   */
  public logout():void {
    this.sessionService.logOut();
    this.router.navigate(['']);
  } 

}
