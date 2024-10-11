import { Injectable } from '@angular/core';
import { AuthService, User } from '../core/modules/openapi';
import { BehaviorSubject, distinct, map, mergeMap, Observable, skip, take, tap } from 'rxjs';
import { NotificationService } from './notification.service';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public logged = false;
  public user: User | undefined;
  private _token: string | null;
  private _resuming!: boolean;

  private loggedSubject = new BehaviorSubject<boolean>(this.logged);


  constructor(
    private authService: AuthService,
    private notificationService: NotificationService) {
    this._token = localStorage.getItem('token');
    this._resuming = this._token != null;
  };

  public get resuming(): boolean {
    return this._resuming;
  }

  public set token(tokenStr: string) {
    localStorage.setItem('token', tokenStr);
    this._token = tokenStr;
  }

  public get token(): string | null {
    return this._token;
  }

  public $logged(): Observable<boolean> {
    return this.loggedSubject.asObservable();
  }

  public resume() {
    console.log("resuming...");
    return this.authService.getMe().pipe(take(1))
      .subscribe({
        next: user => {
          console.info("Session resumed");
          this._resuming = false;
          this.logIn(user);
        },
        error: (e) => {
          console.info("Couldn't resume session", e);
          this._resuming = false;
          this.logOut();
        },
      });
  }

  public logIn(user: User): void {
    console.log("logging in");
    this.user = user;
    this.logged = true;
    this.next();
  }

  public logOut(): void {
    console.log("logging out");
    this._token = null;
    localStorage.removeItem('token');
    this.user = undefined;
    this.logged = false;
    this.next();
  }

  private next() {
    this.loggedSubject.next(this.logged);
  }


}
