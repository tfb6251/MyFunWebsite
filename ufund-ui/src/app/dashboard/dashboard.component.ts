import { Component, ElementRef, OnInit, Renderer2 } from '@angular/core';
import { Computer } from '../computer/computer';
import { ComputerService } from '../computer/computer.service';
import { UserService } from '../user/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  computers: Computer[] = [];
  searchTerm: string = "";
  isAdmin: Boolean = false;
  id: number | undefined;

  constructor(
    private computerService: ComputerService,
     private userService: UserService,
     private el: ElementRef, 
     private renderer: Renderer2,
     private router: Router
    ) { }

  ngOnInit(): void {
    this.getComputers();
    this.isAdmin = this.userService.isAdmined();
    this.id = this.userService.id();
  }

  getComputers(): void {
    this.computerService.getComputers()
      .subscribe(computers => this.computers = computers);
  }

  filterComputers(searchTerm: string): void {
    if(searchTerm == "") {
      this.computerService.getComputers().subscribe(computers => {
        this.computers = computers;
        const text = "";
        const div = this.el.nativeElement.querySelector('#result');
        this.renderer.setProperty(div, 'textContent', text);
      });
    } else {
      this.computerService.searchComputers(searchTerm).subscribe(computers => {
        if (computers.length == 0) {
          const text = "No Computer Needs Found Under That Name";
          const div = this.el.nativeElement.querySelector('#result');
          this.renderer.setProperty(div, 'textContent', text);          
        } else {
          const text = "";
          const div = this.el.nativeElement.querySelector('#result');
          this.renderer.setProperty(div, 'textContent', text);
        }
        this.computers = computers;
      });
    }
  }

  delete(computer: Computer): void {
    if(computer && computer.id) {
      this.computerService.deleteComputer(computer.id).subscribe(computer => {
        this.computerService.getComputers().subscribe(computers => {
          this.computers = computers;
        });
      })
    }
  }
}
