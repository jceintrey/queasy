import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { AuthService } from "../services/auth.service";
import { catchError, map, Observable, of } from "rxjs";

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) { }

  canActivate(): Observable<boolean> {
    console.log("can activate function");

    return this.auth.getCurrentUser().pipe(
      map(user => !!user),
      catchError(() => {
        this.router.navigate(['/login']);
        return of(false);
      })
    );
  }
}
