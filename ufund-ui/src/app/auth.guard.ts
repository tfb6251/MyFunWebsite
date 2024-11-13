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
    // Replace this with your actual authentication logic
    if (requiresAdmin) { // Define isLoggedIn in your UserService
      if(this.userService.isAdmined()) {
        return true;
      } else {
        this.router.navigate(['/login']);
        return false;
      }
    } else if (this.userService.isLoggedIn()) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
