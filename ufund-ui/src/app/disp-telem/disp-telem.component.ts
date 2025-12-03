import { Component, ElementRef, OnInit, Renderer2, Injectable } from '@angular/core';
import { Telem } from '../telem/telem';
import { TelemService } from '../telem/telem.service';
import { UserService } from '../user/user.service';
import { Router } from '@angular/router';

import { HttpClient } from '@angular/common/http';
import { Observable, interval, switchMap } from 'rxjs'; 


@Component({
  selector: 'app-disp-telem',
  templateUrl: './disp-telem.component.html',
  styleUrl: './disp-telem.component.css'
})
export class DispTelemComponent implements OnInit {

  distance: string = '';

  constructor(private telemService: TelemService) {}

  ngOnInit() {
    this.telemService.getDistance().subscribe(data => {
      this.distance = data.distance;
    });
  }

    // getUser(): void {
    //   if(this.router.url.includes('/new')) {
    //       const newUser: User = {      
    //         id: undefined,
    //         name: "",
    //         password: "",
    //         basket: []
    //       };
    //       this.user = newUser;
    //   } else {
    //     const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    //     if(this.userService.id() == id) {
    //       this.userService.getUser(id).subscribe(user => this.user = user);
    //     } else {    
    //       this.router.navigate(['/home']);
    //     }
    //   }
    // }
}
