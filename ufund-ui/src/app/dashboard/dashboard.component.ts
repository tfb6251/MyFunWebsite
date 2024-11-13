import { Component, OnInit } from '@angular/core';
import { Computer } from '../computer';
import { ComputerService } from '../computer.service';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  computers: Computer[] = [];
  searchTerm: string = "";
  loggedIn = false;
  isAdmin = false;

  constructor(
    private computerService: ComputerService,
     private userService: UserService,
     private router: Router
    ) { }

  ngOnInit(): void {
    this.userService.loggedIn$.subscribe(loggedIn => this.loggedIn = loggedIn);
    this.userService.isAdmin$.subscribe(isAdmin => this.isAdmin = isAdmin);
    this.getComputers();
  }

  getComputers(): void {
    this.computerService.getComputers()
      .subscribe(computers => this.computers = computers.slice(0, 7));
  }

  filterComputers(searchTerm: string): void {
    if(searchTerm == "") {
      this.computerService.getComputers().subscribe(computers => {
        this.computers = computers.slice(0, 7);
      });
    } else {
      this.computerService.searchComputers(searchTerm).subscribe(computers => {
        this.computers = computers.slice(0, 7);
      });
    }
  }

  logout(): void {
    this.userService.logout();
    this.router.navigate(['/login']);
  }
}
