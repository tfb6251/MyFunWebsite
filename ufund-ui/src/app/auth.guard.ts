import { Injectable } from '@angular/core';
import { Location } from '@angular/common';
import { Router, ActivatedRouteSnapshot, CanActivate, ActivatedRoute, RouterStateSnapshot} from '@angular/router';
import { UserService } from './user.service';  // Your existing service to track user

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private userService: UserService, 
    private router: Router,
    private route: ActivatedRoute,
    private location: Location) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const requiresAdmin = route.data['adminOnly'] as boolean;
    const requiresId = route.data['idOnly'] as boolean;


    if (requiresAdmin) {
      if(this.userService.isAdmined()) {
        return true;
      } else {
        this.router.navigate(['/login']);
        return false;
      }
    } else if (this.userService.isAdmined()) {
      return true;
    } else if (requiresId) {
      const id = parseInt(route.paramMap.get('id')!, 10); //no snapshot snapshot is active route without is future
      // alert(`/user/${this.userService.id()}/basket`);
      if(this.userService.id() == id) {
        return true;
      } else {
        this.router.navigate([`/user/${this.userService.id()}/basket`]);
      }
        return false;
    } else if (this.userService.isLoggedIn()) {
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}
