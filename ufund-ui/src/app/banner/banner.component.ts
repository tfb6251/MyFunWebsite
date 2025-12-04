import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user/user.service';

@Component({
  selector: 'app-banner',
  templateUrl: './banner.component.html',
  styleUrl: './banner.component.css'
})
export class BannerComponent implements OnInit{
  isAdmin: boolean = false;
  constructor(private router: Router,
    private userService: UserService,
  ) {}

  ngOnInit(): void {

    this.isAdmin = this.userService.isAdmined();
  }
  logout(): void {
    this.userService.logout();
    this.router.navigate(['/login']);
  }
}
