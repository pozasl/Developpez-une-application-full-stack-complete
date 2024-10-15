import { Component } from '@angular/core';
import { BackBtnComponent } from 'src/app/components/ui/back-btn/back-btn.component';

/**
 * Not found page component
 */
@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [BackBtnComponent],
  templateUrl: './not-found.component.html',
  styleUrl: './not-found.component.scss'
})
export class NotFoundComponent {

}
