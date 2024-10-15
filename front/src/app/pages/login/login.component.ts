import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from '@angular/material/icon';
import { AuthInfo, AuthService, User, UsersService } from 'src/app/core/modules/openapi';
import { SessionService } from 'src/app/services/session.service';
import { mergeMap, take} from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import { NotificationService } from 'src/app/services/notification.service';

/**
 * Login page component
 */
@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [FormsModule, MatCardModule, ReactiveFormsModule, RouterLink, MatInputModule, MatFormFieldModule, MatIconModule, MatButtonModule]
})
export class LoginComponent {
  public hide = true;
  public onError = false;

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.min(6)]]
  });

  constructor(
    private fb: FormBuilder, 
    private router: Router,
    private authService: AuthService,
    private sessionService: SessionService,
    private usersService: UsersService,
    private notificationservice: NotificationService,
  ) {}

  /**
   * Log the user
   */
  public submit() {
    const authInfo: AuthInfo = this.form.value as AuthInfo;
    this.authService.login(authInfo).pipe(
      take(1),
      mergeMap(jwtInfo => {
        if (jwtInfo && jwtInfo.token && jwtInfo.userId) {
          this.sessionService.token = jwtInfo.token;
          return this.usersService.getUserById(jwtInfo.userId);
        }
        throw new Error("Empty jwt token recieved");
      }),
    ).subscribe({
      next: (user: User) => {
        console.log(user);
        if (user) {
          this.sessionService.logIn(user);
          this.router.navigate(['/topics']);
        }
        else {
          this.notificationservice.notifyError("Login failed", "Empty user recieved");
        }
      },
      error: (err: Error) => {
        this.onError = true;
        this.notificationservice.notifyError("Login failed", err.message);
      }
    })
  }
}
