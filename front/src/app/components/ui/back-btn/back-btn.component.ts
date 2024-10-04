import { Component } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-back-btn',
  standalone: true,
  imports: [],
  templateUrl: './back-btn.component.html',
  styleUrl: './back-btn.component.scss'
})
export class BackBtnComponent {

  constructor(private location: Location) { }

  public goBack() {
    this.location.back()
  }
}
