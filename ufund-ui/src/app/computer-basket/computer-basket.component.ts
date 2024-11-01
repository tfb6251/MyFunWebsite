import { Component, ElementRef, Renderer2 } from '@angular/core';
import { Computer } from '../computer';
import { ComputerService } from '../computer.service';
import { User } from '../user';
import { UserService } from '../user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-computer-basket',
  templateUrl: './computer-basket.component.html',
  styleUrl: './computer-basket.component.css'
})
export class ComputerBasketComponent {


  user: User | undefined;
  computers: Computer[] = [];
  allComputers: Computer[] = [];
  searchTerm0: string = "";
  searchTerm1: string = "";


  constructor(
    private computerService: ComputerService,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private location: Location,
    private el: ElementRef,
    private renderer: Renderer2
  ) { }

  ngOnInit(): void {
    this.getComputers();
  }

  getComputers(): void {
    this.computerService.getComputers()
      .subscribe(computers => this.allComputers = computers.slice(0, 7));
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.userService.getUser(id)
        .subscribe(user => {
          this.user = user
          this.computers = user.basket.slice(0,7);
        });
  }

  addComputer(id: number | undefined): void {
    if(id)  {
      this.computerService.getComputer(id).subscribe(computer => {
        this.user?.basket.push(computer);
        if (this.user) {
          this.userService.updateUser(this.user).subscribe(user => {
            this.user = user;
            this.computers = user.basket.slice(0,7);
            this.computerService.deleteComputer(id).subscribe(() => {
              this.computerService.getComputers().subscribe(computers => {
                this.allComputers = computers.slice(0,7);;
              });
            });
          });
        } 
      });
    }
  }

  removeComputer(id: number | undefined): void {  
    // Find the index of the computer with the matching ID
    if(id) {
      let index = this.user?.basket.findIndex(computer => computer.id == id);
      if (index != -1 && index != undefined) {
        let remo = this.user?.basket.splice(index, 1);
        if (this.user) {
          this.userService.updateUser(this.user).subscribe(user => {
            this.user = user;
            this.computers = user.basket.slice(0,7);;
            let computer = remo?.pop(); 
            if (computer) { 
              this.computerService.addComputer(computer).subscribe(() => {
                this.computerService.getComputers().subscribe(computers => {
                  this.allComputers = computers.slice(0,7);;
                });  
              });          
            }
          });
        }
      }
    }
  }

  filterComputers0(searchTerm: string): void {
    if(this.user) {
      if(searchTerm == "")  {
        this.computers = this.user.basket;        
      } else {
        this.computers = this.user.basket.filter(computer =>
          computer.name.includes(searchTerm));
      }
    }
  }

  filterComputers1(searchTerm: string): void {
    if(searchTerm == "") {
      this.computerService.getComputers().subscribe(computers => {
        this.allComputers = computers;
      });
    } else {
      this.computerService.searchComputers(searchTerm).subscribe(computers => {
        this.allComputers = computers;
      });
    }
  }
}
