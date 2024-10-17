import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';

/**
 * Generic Back button to go to previous page
 */
@Component({
  selector: 'app-back-btn',
  standalone: true,
  imports: [MatIconModule, MatButtonModule],
  templateUrl: './back-btn.component.html',
  styleUrl: './back-btn.component.scss'
})
export class BackBtnComponent {

  constructor(private router: Router) { }

  /**
   * Go back in history or to home page
   */
  public goBack() {
    console.log(window.history.length)
    if (window.history.length > 1) {
      window.history.back();
    } else {
      this.router.navigate(['/'])
    }
  }
  
}
  