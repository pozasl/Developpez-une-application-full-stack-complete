import { Injectable } from '@angular/core';
import { User } from '../core/modules/openapi';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public logged = false;
  public user: User | undefined;

  private loggedSubject = new BehaviorSubject<boolean>(this.logged);

  public $logged(): Observable<Boolean> {
    return this.loggedSubject.asObservable();
  }

  public logIn(user: User): void {
    this.user = user;
    this.logged = true;
    this.next();
  }

  public logOut(): void {
    localStorage.removeItem('token');
    this.user = undefined;
    this.logged = false;
    this.next();
  }

  private next() {
    this.loggedSubject.next(this.logged);
  }

}
