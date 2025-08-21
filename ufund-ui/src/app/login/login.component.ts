import { Component, ElementRef, Renderer2, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { UserService } from '../user.service';
import { User } from '../user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(
    private el: ElementRef, 
    private renderer: Renderer2,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  username: string | undefined;
  password: string | undefined;
  users: User[] = [];
  

  onSubmit() {
    if (this.username && this.password) {
      this.userService.searchUsers(this.username).subscribe(users => {
        this.users = users
        if (this.username == 'admin' && this.password == "admin") {
          this.userService.login(true, NaN);
          this.router.navigate(['/home']);
        } else if (this.users.length >= 1) {
          for(let i = 0; this.users.length > i ; i++) {
            if(this.users[i].name == this.username &&
              this.users[i].password == this.password && this.users[i].id) {
              this.userService.login(false, this.users[i].id!);
              this.router.navigate(['/home']);
            }
          }
          const text = "Please Enter Valid Username and Password";
          const div = this.el.nativeElement.querySelector('#result');
          this.renderer.setProperty(div, 'textContent', text);
        } else {
          const text = "Please Enter Valid Username and Password";
          const div = this.el.nativeElement.querySelector('#result');
          this.renderer.setProperty(div, 'textContent', text);
        }   
      });
    } else {
      const text = "Please Enter Name and Password";
      const div = this.el.nativeElement.querySelector('#result');
      this.renderer.setProperty(div, 'textContent', text);
    }
  }

  ngOnInit() {
    console.log('ngOnInit called'); // verify component initialization
  }

  ngAfterViewInit() {
    console.log('ngAfterViewInit called'); // verify view initialization
    window.scrollTo(8, 40); // attempt to scroll
    this.renderer.setStyle(document.body, 'overflow', 'hidden');
  }
}
