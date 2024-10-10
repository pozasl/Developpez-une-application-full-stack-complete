import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from "@angular/router";
import { SessionService } from "../services/session.service";
import { catchError, map, of, take } from "rxjs";

@Injectable({ providedIn: 'root' })
export class UnauthGuard implements CanActivate {

  constructor(
    private router: Router,
    private sessionService: SessionService
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {
    return this.sessionService.$logged().pipe(
      take(1),
      map(isLogged => {
        if (isLogged) {
          this.router.navigate(['/topics']);
        }
        return !isLogged
      }),
      catchError((err) => {
        return of(true);
      })
    );
  }

}