import { HttpEvent, HttpHandlerFn, HttpRequest } from "@angular/common/http";
import { Observable } from "rxjs";
import { SessionService } from "../services/session.service";
import { inject } from "@angular/core";

/**
 * Interceptor function to inject stored Jwt token in header
 */
export function jwtInterceptor(req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> {

  const token = inject(SessionService).token;

  if (!token) {
    return next(req);
  }

  const newReq = req.clone({
    headers: req.headers.append('Authorization', `Bearer ${token}`)
  })
  return next(newReq);
}