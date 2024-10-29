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
  name: string | undefined;
  user: User | undefined;
  users: User[] = [];

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(): void {
    this.userService.getUsers()
      .subscribe(users => this.users = users.slice(0, 4));
  } 

  onSubmit() {
    if (this.username) {
      this.userService.getUserN(this.username).subscribe(user => {this.user = user});

        if (this.username == 'admin') {
          this.router.navigate(['/dashboard']);
        } else if (this.username.toString() == this.user?.name.toString()) {
          this.router.navigate(['/dashboard']);
        } else {
          this.userService.getUser(1).subscribe(user => {this.name = user.name;
          const text = this.name + "Ping";
          const div = this.el.nativeElement.querySelector('#hello');

          this.renderer.setProperty(div, 'textContent', text);
          });
        }
    }
  }
}
