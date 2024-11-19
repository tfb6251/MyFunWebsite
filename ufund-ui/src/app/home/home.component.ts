import { Component } from '@angular/core';
import { ComputerService } from '../computer.service';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { Computer } from '../computer';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  computers: Computer[] = [];


  constructor(
    private computerService: ComputerService,
    ) { }

  ngOnInit(): void {
    this.getComputers();
  }
  
  getComputers(): void {
    this.computerService.getComputers()
      .subscribe(computers => this.computers = computers.slice(0, 3));
  }
}
