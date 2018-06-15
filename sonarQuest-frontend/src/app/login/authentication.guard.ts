import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {AuthenticationService} from './authentication.service';

@Injectable()
export class AuthenticationGuard implements CanActivate {

  constructor(private router: Router,
              private authService: AuthenticationService) {
  }


  canActivate(next: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): boolean {
    return this.authenticate();
  }

  authenticate(): boolean {
    const isLoggedIn = this.authService.isLoggedIn();

    if (!isLoggedIn) {
      this.router.navigate(['/'], { skipLocationChange: true });
      return false;
    }

    return true;
  }
}
