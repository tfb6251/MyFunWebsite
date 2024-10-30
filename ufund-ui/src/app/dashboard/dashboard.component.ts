import { Component, OnInit } from '@angular/core';
import { Computer } from '../computer';
import { ComputerService } from '../computer.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  computers: Computer[] = [];
  searchTerm: string = "";

  constructor(private computerService: ComputerService) { }

  ngOnInit(): void {
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
}
