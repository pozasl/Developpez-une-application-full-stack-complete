import { Injectable } from '@angular/core';
import { AuthService, User } from '../core/modules/openapi';
import { BehaviorSubject,  first,  map,  merge,  mergeMap, Observable, of, take, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public logged = false;
  public user: User | undefined;
  private _token: string | null;

  private loggedSubject = new BehaviorSubject<boolean>(this.logged);
  private resuming = false;


  constructor(private authService: AuthService) {
    this._token = localStorage.getItem('token');
  };

  public set token(tokenStr: string) {
    localStorage.setItem('token', tokenStr);
    this._token = tokenStr;
  }

  public get token(): string | null {
    return this._token;
  }

  public $logged(): Observable<boolean> {
    const loggedObs = this.loggedSubject.asObservable();
    return this.resuming ? loggedObs : this.$resume().pipe(first(), mergeMap(() => loggedObs));
  }

  public $resume() {
      console.log("resuming...");
      this.resuming = true
      return this.authService.getMe().pipe(
        tap({
          next: user => {
            console.log("Session resumed", user);
            this.logIn(user);
          },
          error: (e) => {
            console.log("Couldn't resume session", e);
            this.logOut();
          },
        }),
        map(user => user != null)
      );    
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
