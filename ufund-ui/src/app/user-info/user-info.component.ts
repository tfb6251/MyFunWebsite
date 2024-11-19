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
          id: undefined,
          name: "",
          password: "",
          basket: []
        };
        this.user = newUser;
    } else {
      const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
      if(this.userService.id() == id) {
        this.userService.getUser(id).subscribe(user => this.user = user);
      } else {    
        this.router.navigate(['/home']);
      }
    }
  }

  delete(): void {
    if(this.user) {
      this.userService.deleteUser(this.user.id!)
      .subscribe(user => {
        this.user = user;
        this.location.back(); 
      }); 
    }
  }

  goBack(): void {
    this.location.back(); 
  }

  save(): void {
    if (this.user && this.user?.name != "" && this.user?.password != "") {      
      this.userService.addUser(this.user)
      .subscribe(user => this.user = user);
      this.location.back();      
    } else {
      const text = "Please Enter Name and Password";
      const div = this.el.nativeElement.querySelector('#result');
      this.renderer.setProperty(div, 'textContent', text);
    }
  }
}
