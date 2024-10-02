import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { ApiModule, AuthService, NewUser, ResponseMessage } from 'src/app/core/modules/openapi';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  imports: [FormsModule, MatCardModule, ReactiveFormsModule, RouterLink, MatFormFieldModule, MatIconModule, ApiModule],
})
export class RegisterComponent {
  public hide = false;
  public onError = false;

  public form = this.fb.group({
    name: ['', [Validators.required, Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.min(3)]]
  });

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) { }

  public submit() {
    const newUser: NewUser = this.form.value as NewUser;
    this.authService.register(newUser).subscribe({
      next: (response: ResponseMessage) => {
        console.log(response);
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.log(err);
        this.onError = true;
      }
    });

  }
}
