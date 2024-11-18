import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, CanActivate} from '@angular/router';
import { UserService } from './user.service';  // Your existing service to track user

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private userService: UserService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const requiresAdmin = route.data['adminOnly'] as boolean;
    if (requiresAdmin) {
      if(this.userService.isAdmined()) {
        return true;
      } else {
        this.router.navigate(['/login']);
        return false;
      }
    } else if (this.userService.isLoggedIn() || this.userService.isAdmined()) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
