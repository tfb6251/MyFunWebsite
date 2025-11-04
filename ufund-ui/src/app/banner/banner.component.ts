import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user/user.service';

@Component({
  selector: 'app-banner',
  templateUrl: './banner.component.html',
  styleUrl: './banner.component.css'
})
export class BannerComponent {
  constructor(private router: Router,
    private userService: UserService,
  ) {}

  logout(): void {
    this.userService.logout();
    this.router.navigate(['/login']);
  }
}
