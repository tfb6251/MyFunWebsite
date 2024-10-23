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

  constructor(private computerService: ComputerService) { }

  ngOnInit(): void {
    this.getComputers();
  }

  getComputers(): void {
    this.computerService.getComputers()
      .subscribe(computers => this.computers = computers.slice(1, 5));
  }
}
