import { Component, OnInit } from '@angular/core';
import { SessionService } from './services/session.service';
import { NavigationStart, Router } from '@angular/router';
import { filter, map, Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit{
  public $logged!: Observable<Boolean>;
  public $showNav!: Observable<Boolean>;
  title = 'MDD';

  constructor(
    private sessionService: SessionService,
    private router: Router,
  ) {}
  ngOnInit(): void {
    this.$logged = this.sessionService.$logged();
    this.$showNav = this.router.events.pipe(
      filter(e => e instanceof NavigationStart),
      map(e => e.url != '/'));
  }

  public logout():void {
    this.sessionService.logOut();
    this.router.navigate(['']);
  } 
  
}
