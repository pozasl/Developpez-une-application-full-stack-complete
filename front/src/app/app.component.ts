import { Component } from '@angular/core';
import { SessionService } from './services/session.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent{
  title = 'MDD';

  constructor(
    private sessionService: SessionService,
    private router: Router
  ) {}

  public logout():void {
    this.sessionService.logOut();
    this.router.navigate(['']);
  } 
}
