import { Component, ElementRef, OnInit, Renderer2, Injectable } from '@angular/core';
import { Telem } from '../telem/telem';
import { TelemService } from '../telem/telem.service';
import { UserService } from '../user/user.service';
import { Router } from '@angular/router';

import { HttpClient } from '@angular/common/http';
import { Observable, interval, of, switchMap } from 'rxjs'; 
import { DistanceData } from '../telem/distanceData';


@Component({
  selector: 'app-disp-telem',
  templateUrl: './disp-telem.component.html',
  styleUrl: './disp-telem.component.css'
})
export class DispTelemComponent implements OnInit {
  isAdmin: Boolean = false;
  id: number | undefined;
  data: DistanceData = {
    ft: 0,
    m: 0,
    in: 0,
    cm: 0,
    us: 0
  };
  errorMessage: string = '';

  constructor(
    private telemService: TelemService,
    private userService: UserService) {}

  ngOnInit() {
    this.isAdmin = this.userService.isAdmined();
    this.id = this.userService.id();
    this.telemService.getDistance().subscribe(d => {
    this.data = d;
  });
  }
}
