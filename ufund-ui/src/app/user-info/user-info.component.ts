import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

import { UserService } from '../user.service';
import { User } from '../user';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrl: './user-info.component.css'
})
export class UserInfoComponent {
  user: User | undefined;

  constructor(
    private el: ElementRef, 
    private renderer: Renderer2,
    private route: ActivatedRoute,
    private userService: UserService,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getUser();
  }

  getUser(): void {
    if(this.router.url.includes('/new')) {
        const newUser: User = {      
          id: 0,
          name: "",
          password: "",
          basket: []
        };
        this.userService.addUser(newUser)
        .subscribe(user => this.user = user);
    } else {
      const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
      this.userService.getUser(id)
        .subscribe(user => this.user = user);
    }
  }

  delete(): void {
    if(this.user) {
      this.userService.deleteUser(this.user.id)
      .subscribe(user => {
        this.user = user;
        this.location.back(); 
      }); 
    }
  }

  goBack(): void {
    if(this.router.url.includes('/new')) {
      this.delete();
    } else {
      this.location.back();      
    }
  }

  save(): void {
    if (this.user?.name != "" && this.user?.name) {      
      this.userService.updateUser(this.user)
      .subscribe(user => this.user = user);
      this.location.back();      
    } else {
      const text = "Please Enter Name and Password";
      const div = this.el.nativeElement.querySelector('#result');
      this.renderer.setProperty(div, 'textContent', text);
    }
  }
}
