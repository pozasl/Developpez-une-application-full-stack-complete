import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  imports: [FormsModule, MatCardModule, ReactiveFormsModule, RouterLink, MatFormFieldModule, MatIconModule]
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
    private router: Router
  ) {}

  public submit() {

  }
}
