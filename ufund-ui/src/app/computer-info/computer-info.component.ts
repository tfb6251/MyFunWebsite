import { Component, ElementRef, HostListener, OnInit, Renderer2 } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

import { UserService } from '../user/user.service';
import { ComputerService } from '../computer/computer.service';
import { Computer } from '../computer/computer';

@Component({
  selector: 'app-computer-info',
  templateUrl: './computer-info.component.html',
  styleUrl: './computer-info.component.css'
})
export class ComputerInfoComponent implements OnInit{
  @HostListener('window:keyup.enter')
  handleEnterKey(): void {
    if (this.isAdmin) {
      this.save();
    } else {
      this.addBasket();
    }
  }


  computer: Computer | undefined;
  isNew: boolean = false;
  isAdmin: boolean = false;


  constructor(
    private route: ActivatedRoute,
    private computerService: ComputerService,
    private userService: UserService,
    private location: Location,
    private el: ElementRef, 
    private renderer: Renderer2,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getComputer();
    this.isAdmin = this.userService.isAdmined();
  }

  getComputer(): void {
    if(this.router.url.includes('/new')) {
        this.isNew = true
        const newComputer: Computer = {      
          id: undefined,
          name: "",
          cost: undefined,
          quantity: undefined,
          brand: "",
          description: ""
        };
        this.computer = newComputer;
    } else {
      const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
      this.computerService.getComputer(id)
        .subscribe(computer => this.computer = computer);
    }
  }

  delete(): void {
    if(this.computer) {
      this.computerService.deleteComputer(this.computer.id!)
      .subscribe(computer => {
        this.computer = computer;
        this.location.back();  
      });    
    }
  }

  goBack(): void {
    this.location.back();      
  }

  save(): void {
    if (this.computer && this.computer.cost != null && this.computer.quantity != null) {
      if (this.computer.name != "") {
        if (this.computer.cost > 0) {
          if (this.computer.quantity > 0) {
            if (this.computer.brand != "") {
              if (this.computer.description != "") {
                this.computerService.addComputer(this.computer)
                .subscribe(computer => this.computer = computer);
                this.location.back(); 
              } else {
                const text = "Please Enter Valid Description";
                const div = this.el.nativeElement.querySelector('#result');
                this.renderer.setProperty(div, 'textContent', text);
              }
            } else {
              const text = "Please Enter Valid Brand";
              const div = this.el.nativeElement.querySelector('#result');
              this.renderer.setProperty(div, 'textContent', text);
            }
          } else {
            const text = "Please Enter Valid Quantity";
            const div = this.el.nativeElement.querySelector('#result');
            this.renderer.setProperty(div, 'textContent', text);
          }
        } else {
          const text = "Please Enter Valid Cost";
          const div = this.el.nativeElement.querySelector('#result');
          this.renderer.setProperty(div, 'textContent', text);
        }
      } else {
        const text = "Please Enter Valid Name";
        const div = this.el.nativeElement.querySelector('#result');
        this.renderer.setProperty(div, 'textContent', text);
      }
    }
  } 

  addBasket() {
    if (this.computer && this.computer.cost != null && this.computer.quantity != null) {
        if (this.computer.cost > 0) {
          if (this.computer.quantity > 0) {          
            if (this.computer.brand != "") {
              if (this.computer.description != "") {
                this.computerService.updateComputer(this.computer)
                .subscribe(computer => this.computer = computer);
                this.location.back(); 
              } else {
                const text = "Please Enter Valid Description";
                const div = this.el.nativeElement.querySelector('#result');
                this.renderer.setProperty(div, 'textContent', text);
              }
            } else {
              const text = "Please Enter Valid Brand";
              const div = this.el.nativeElement.querySelector('#result');
              this.renderer.setProperty(div, 'textContent', text);
            }
          } else {
            const text = "Please Enter Valid Quantity";
            const div = this.el.nativeElement.querySelector('#result');
            this.renderer.setProperty(div, 'textContent', text);
          }
        } else {
          const text = "Please Enter Valid Cost";
          const div = this.el.nativeElement.querySelector('#result');
          this.renderer.setProperty(div, 'textContent', text);
        }
    }
  }
}
