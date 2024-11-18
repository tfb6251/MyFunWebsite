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
  styleUrls: ['./computer-basket.component.css']
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
  

  // Get initial list of all computers in cupboard and the user's basket
  getComputers(): void {
    this.computerService.getComputers()
      .subscribe(computers => {
        this.allComputers = computers.slice(0, 7);  // Limit to first 7 computers in cupboard
      });

    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    if(this.userService.id() == id) {
      this.userService.getUser(id).subscribe(user => {
        this.user = user;
        this.computers = user.basket.slice(0, 7);  // Limit to first 7 computers in basket      
      });
    } else {    
      const text = id;
      const div = this.el.nativeElement.querySelector('#result');
      this.renderer.setProperty(div, 'textContent', text);
    }
  }

  // Add a computer to the basket
  addComputer(id: number | undefined): void {
    if (id && this.user) {
      this.computerService.getComputer(id).subscribe(computer => {
        // Ensure the quantity is valid (fallback to 0 if undefined)
        if (computer.quantity === undefined || computer.quantity <= 0) {
          return;
        }

        // Find if the computer is already in the basket
        const basketComputer = this.user?.basket.find(c => c.id === id);

        if (basketComputer) {
          // If the computer is already in the basket, increase its quantity by 1
          basketComputer.quantity! += 1;
        } else {
          // If the computer is not in the basket, add it with quantity 1
          this.user?.basket.push({ ...computer, quantity: 1 });
        }

        // Decrease the quantity in the cupboard (inventory)
        computer.quantity = (computer.quantity || 0) - 1;

        // Ensure user is defined before updating
        if (this.user) {
          this.userService.updateUser(this.user).subscribe(updatedUser => {
            this.user = updatedUser;
            this.computers = updatedUser.basket.slice(0, 7);
            this.computerService.updateComputer(computer).subscribe(() => {
              this.computerService.getComputers().subscribe(updatedComputers => {
                this.allComputers = updatedComputers.slice(0, 7);
              });
            });
          });
        }
      });
    }
  }

  // Remove a computer from the basket
  removeComputer(id: number | undefined): void {
    if (id && this.user) {
      const basketComputer = this.user.basket.find(c => c.id === id);

      if (basketComputer) {
        if (basketComputer.quantity! > 1) {
          // If the computer's quantity in the basket is more than 1, decrease it by 1
          basketComputer.quantity! -= 1;

          // Increase the quantity in the cupboard (inventory)
          this.computerService.getComputer(id).subscribe(cupboardComputer => {
            cupboardComputer.quantity = (cupboardComputer.quantity || 0) + 1;

            // Ensure user is defined before updating
            if (this.user) {
              this.userService.updateUser(this.user).subscribe(updatedUser => {
                this.user = updatedUser;
                this.computers = updatedUser.basket.slice(0, 7);
                this.computerService.updateComputer(cupboardComputer).subscribe(() => {
                  this.computerService.getComputers().subscribe(updatedComputers => {
                    this.allComputers = updatedComputers.slice(0, 7);
                  });
                });
              });
            }
          });
        } else {
          // If the computer's quantity in the basket is 1, remove it completely from the basket
          const index = this.user.basket.findIndex(c => c.id === id);
          if (index !== -1) {
            this.user.basket.splice(index, 1); // Remove the computer from the basket
          }

          // Increase the quantity in the cupboard
          this.computerService.getComputer(id).subscribe(cupboardComputer => {
            cupboardComputer.quantity = (cupboardComputer.quantity || 0) + 1;

            // Ensure user is defined before updating
            if (this.user) {
              this.userService.updateUser(this.user).subscribe(updatedUser => {
                this.user = updatedUser;
                this.computers = updatedUser.basket.slice(0, 7);
                this.computerService.updateComputer(cupboardComputer).subscribe(() => {
                  this.computerService.getComputers().subscribe(updatedComputers => {
                    this.allComputers = updatedComputers.slice(0, 7);
                  });
                });
              });
            }
          });
        }
      }
    }
  }

  checkout(): void {
    // Early exit if there is no user
    if (!this.user) {
      console.warn('User is not defined!');
      return;
    }
  
    // If the basket is already empty, do nothing
    if (this.user.basket.length === 0) {
      console.log('The basket is already empty.');
      return;
    }
  
    // Simply clear the basket without modifying cupboard quantities
    this.user.basket = [];  // Empty the basket
  
    // Now update the user's basket in the backend
    this.userService.updateUser(this.user).subscribe(updatedUser => {
      this.user = updatedUser;
      this.computers = updatedUser.basket.slice(0, 7);  // Update local basket view
      this.computerService.getComputers().subscribe(updatedComputers => {
        this.allComputers = updatedComputers.slice(0, 7);  // Update cupboard view
      });
    });
  }
  
  
  filterComputers0(searchTerm: string): void {
    if (this.user) {
      if (searchTerm == "") {
        this.computers = this.user.basket;
      } else {
        this.computers = this.user.basket.filter(computer =>
          computer.name.includes(searchTerm));
      }
    }
  }

  filterComputers1(searchTerm: string): void {
    if (searchTerm == "") {
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
