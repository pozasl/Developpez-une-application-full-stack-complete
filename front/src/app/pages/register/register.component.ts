import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatIconModule } from '@angular/material/icon';
import { ApiModule, AuthService, NewUser, ResponseMessage } from 'src/app/core/modules/openapi';
import { take } from 'rxjs';
import { NotificationService } from 'src/app/services/notification.service';
import { passwordStrengthReg } from 'src/app/shared/validators/passwordStrengthReg';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  imports: [FormsModule, MatCardModule, ReactiveFormsModule, RouterLink, ApiModule, MatInputModule, MatFormFieldModule, MatIconModule, MatButtonModule],
})
export class RegisterComponent {
  public hide = true;
  public onError = false;

  public form = this.fb.group({
    name: ['', [Validators.required]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.pattern(passwordStrengthReg)]]
  });

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private notificationService: NotificationService
  ) { }

  public submit() {
    const newUser: NewUser = this.form.value as NewUser;
    this.authService.register(newUser).pipe(take(1)).subscribe({
      next: (response: ResponseMessage) => {
        console.log(response);
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.notificationService.notifyError("Erreur d'enregistrement", err.message);
        this.onError = true;
      }
    });

  }
}
