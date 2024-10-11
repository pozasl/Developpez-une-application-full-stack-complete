import { Injectable } from '@angular/core';
import { BehaviorSubject, filter, Observable } from 'rxjs';
import { AppError } from '../model/AppError';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private error: AppError | null = null
  private errorSubject = new BehaviorSubject<AppError | null>(this.error);;

  constructor() { }

  public notifyError(errorName:string, errorMessage: string) {
    this.error = { name: errorName, message: errorMessage} ;
    console.error(this.error);
    this.errorSubject.next(this.error);
  }

  public $error(): Observable<AppError> {
    return this.errorSubject.asObservable().pipe(filter(err=> err != null));
  }

}
