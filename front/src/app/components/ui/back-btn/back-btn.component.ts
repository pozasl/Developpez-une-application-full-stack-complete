import { Component } from '@angular/core';
import { Location } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-back-btn',
  standalone: true,
  imports: [MatIconModule, MatButtonModule],
  templateUrl: './back-btn.component.html',
  styleUrl: './back-btn.component.scss'
})
export class BackBtnComponent {

  constructor(private location: Location) { }

  public goBack() {
    this.location.back()
  }
  
}
  