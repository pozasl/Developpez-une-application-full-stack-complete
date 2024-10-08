import { Component, OnInit } from '@angular/core';
import { SessionService } from './services/session.service';
import { NavigationStart, Router } from '@angular/router';
import { filter, first, map, Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  public $showNav!: Observable<boolean>;
  title = 'MDD';

  constructor(
    private sessionService: SessionService,
    private router: Router,
  ) { }


  ngOnInit(): void {
    // this.sessionService.resume();
    this.$showNav = this.router.events.pipe(
      filter(e => e instanceof NavigationStart),
      map(e => e.url != '/'));
  }

  public $isLogged():Observable<boolean> {
    return this.sessionService.$logged()
  }
}