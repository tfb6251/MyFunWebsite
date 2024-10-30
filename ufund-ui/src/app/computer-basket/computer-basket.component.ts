import { Component, ElementRef, Renderer2 } from '@angular/core';
import { Computer } from '../computer';
import { ComputerService } from '../computer.service';
import { User } from '../user';
import { UserService } from '../user.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-computer-basket',
  templateUrl: './computer-basket.component.html',
  styleUrl: './computer-basket.component.css'
})
export class ComputerBasketComponent {

  user: User | undefined;
  computers: Computer[] = [];
  allComputers: Computer[] = [];

  constructor(
    private computerService: ComputerService,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private el: ElementRef,
    private renderer: Renderer2
  ) { }

  ngOnInit(): void {
    this.getComputers();
  }

  getComputers(): void {
    this.computerService.getComputers()
      .subscribe(computers => this.allComputers = computers.slice(0, 4));
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.userService.getUser(id)
        .subscribe(user => {
          this.user = user
          this.computers = user.basket
        });
  }

  addComputer(id: number): void {  
    this.computerService.getComputer(id).subscribe(computer => {
      this.user?.basket.push(computer);
      if (this.user) {
        this.userService.updateUser(this.user)
        .subscribe(user => this.user = user);
      } 
    });
  }
}
