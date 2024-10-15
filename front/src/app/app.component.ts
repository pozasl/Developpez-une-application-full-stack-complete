import { Component, OnInit } from '@angular/core';
import { SessionService } from './services/session.service';
import { NavigationStart, Router } from '@angular/router';
import { filter, map, Observable } from 'rxjs';
import { ErrorDialogComponent } from './components/ui/error-dialog/error-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { NotificationService } from './services/notification.service';
import { AppError } from './model/AppError';

/**
 * Main component
 */
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
    private notificationService: NotificationService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {

    this.notificationService.$error().subscribe(err => this.openErrorDialog(err));

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

  private openErrorDialog(error: AppError) {
    this.dialog.open(ErrorDialogComponent, {data : error});
  }
}