import { HttpEvent, HttpHandlerFn, HttpHeaders, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";
import { SessionService } from "../services/session.service";
import { inject } from "@angular/core";

/**
 * Interceptor functor to inject stored Jwt token in header
 */

export function jwtInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {

  const token = inject(SessionService).token;

  if (!token) {
    console.log("No token to inject");
    return next(req);
  }

  console.log("Injecting token...");

  const newReq = req.clone({
    headers: req.headers.append('Authorization', `Bearer ${token}`)
  })
  return next(newReq);
}