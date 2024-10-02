import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { ApiModule, AuthInfo, AuthService, JwtInfo, NewUser, ResponseMessage } from 'src/app/core/modules/openapi';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [FormsModule, MatCardModule, ReactiveFormsModule, RouterLink, MatFormFieldModule, MatIconModule, ApiModule]
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
    private authService: AuthService
  ) {}

  public submit() {
    const authInfo: AuthInfo = this.form.value as AuthInfo;
    this.authService.login(authInfo).subscribe({
      next: (response: JwtInfo) => {
        console.log(response);
        this.router.navigate(['/topics']);
      },
      error: (err) => {
        console.log(err);
        this.onError = true;
      }
    });
    }
}
