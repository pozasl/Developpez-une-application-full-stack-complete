import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from '@angular/material/icon';
import { ApiModule, AuthInfo, AuthService, JwtInfo, NewUser, ResponseMessage, User, UsersService } from 'src/app/core/modules/openapi';
import { SessionService } from 'src/app/services/session.service';
import { first, mergeMap} from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [FormsModule, MatCardModule, ReactiveFormsModule, RouterLink, MatInputModule, MatFormFieldModule, MatIconModule, ApiModule]
})
export class LoginComponent {
  public hide = false;
  public onError = false;

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.min(3)]]
  });

  constructor(
    private fb: FormBuilder, 
    private router: Router,
    private authService: AuthService,
    private sessionService: SessionService,
    private usersService: UsersService,
  ) {}

  public submit() {
    const authInfo: AuthInfo = this.form.value as AuthInfo;
    this.authService.login(authInfo).pipe(
      first(),
      mergeMap(jwtInfo => {
        if (jwtInfo && jwtInfo.token && jwtInfo.userId) {
          localStorage.setItem('token', jwtInfo.token);
          console.log("got token", jwtInfo.token);
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
        else console.error("Empty user recieved");
      },
      error: (err) => {
        console.log(err);
        this.onError = true;
      }
    })
  }
}
