import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {
  errorMessage: string = 'An unexpected error occurred.';
  errorCode: string = 'Unknown Error';

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    // Get the error message and code from the query params, if available
    this.route.queryParams.subscribe(params => {
      this.errorMessage = params['message'] || this.errorMessage;
      this.errorCode = params['code'] || this.errorCode;
    });
  }
}
