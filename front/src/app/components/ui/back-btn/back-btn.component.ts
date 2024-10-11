import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-back-btn',
  standalone: true,
  imports: [MatIconModule, MatButtonModule],
  templateUrl: './back-btn.component.html',
  styleUrl: './back-btn.component.scss'
})
export class BackBtnComponent {

  constructor(private location: Location, private router: Router) { }

  public goBack() {
    if (window.history.length > 1) {
      console.log(window.history)
      this.location.back()
    } else {
      this.router.navigate(['/'])
    }
  }
  
}
  