import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

import { ComputerService } from '../computer.service';
import { Computer } from '../computer';

@Component({
  selector: 'app-computer-info',
  templateUrl: './computer-info.component.html',
  styleUrl: './computer-info.component.css'
})
export class ComputerInfoComponent implements OnInit{
  computer: Computer | undefined;

  constructor(
    private route: ActivatedRoute,
    private computerService: ComputerService,
    private location: Location,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getComputer();

  }

  getComputer(): void {
    if(this.router.url.includes('/new')) {
        const newComputer: Computer = {      
          id: 0,
          name: "",
          cost: 0,
          quantity: 0,
          brand: ""
        };
        this.computerService.addComputer(newComputer)
        .subscribe(computer => this.computer = computer);
    } else {
      const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
      this.computerService.getComputer(id)
        .subscribe(computer => this.computer = computer);
    }
  }

  delete(): void {
    if(this.computer) {
      this.computerService.deleteComputer(this.computer.id)
        .subscribe(computer => this.computer = computer);
        this.location.back();  
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
    if (this.computer) {
      const id = this.computer.id;
      this.computerService.updateComputer(this.computer)
      .subscribe(computer => this.computer = computer);
      this.location.back();      
    }
  }
}
