import { Component, OnInit } from '@angular/core';
import { SessionService } from './services/session.service';
import { NavigationStart, Router } from '@angular/router';
import { filter, map, Observable, take } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  public showNav!: boolean;
  public hideSmallMedia!: boolean
  public title = 'MDD';

  constructor(
    private sessionService: SessionService,
    private router: Router,
  ) { }


  ngOnInit(): void {

    if(this.sessionService.resuming) {
      this.sessionService.resume();
    }
    this.router.events.pipe(
      filter(e => e instanceof NavigationStart),
      map(e => e.url)).subscribe({
        next: url => {
          // Don't show nav at homepage
          this.showNav = url != "/";
          // Add css class switch for media sized show condition
          this.hideSmallMedia = ["/login", "/register"].indexOf(url) >= 0;
        }
      });
  }

  public $isLogged(): Observable<boolean> {
    return this.sessionService.$logged()
  }
}