import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getComputer();
  }

  getComputer(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.computerService.getComputer(id)
      .subscribe(computer => this.computer = computer);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.computer) {
      this.computerService.updateComputer(this.computer)
        .subscribe(() => this.goBack());
    }
  }
}
