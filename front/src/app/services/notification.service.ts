import { Injectable } from '@angular/core';
import { BehaviorSubject, filter, Observable } from 'rxjs';
import { AppError } from '../model/AppError';

/**
 * Notification service
 */
@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private error: AppError | null = null
  private errorSubject = new BehaviorSubject<AppError | null>(this.error);;

  /**
   * Notify an error
   * @param errorName the error name
   * @param errorMessage the error message
   */
  public notifyError(errorName:string, errorMessage: string) {
    this.error = { name: errorName, message: errorMessage} ;
    console.error(this.error);
    this.errorSubject.next(this.error);
  }

  /**
   * Return notified error
   * 
   * @returns error
   */
  public $error(): Observable<AppError> {
    return this.errorSubject.asObservable().pipe(filter(err=> err != null));
  }

}
