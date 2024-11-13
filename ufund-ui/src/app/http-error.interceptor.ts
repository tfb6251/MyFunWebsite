import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

  constructor(private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        let errorMessage = 'An unexpected error occurred.';
        let errorCode = 'Unknown Error';

        if (error.status === 404) {
          errorMessage = 'The page you are looking for does not exist.';
          errorCode = '404 Not Found';
        } else if (error.status === 500) {
          errorMessage = 'Internal server error. Please try again later.';
          errorCode = '500 Internal Server Error';
        } else if (error.status === 0) {
          errorMessage = 'Network error. Please check your internet connection.';
          errorCode = 'Network Error';
        }

        // Redirect to error page with error message and code
        this.router.navigate(['/error'], { queryParams: { message: errorMessage, code: errorCode } });

        return throwError(error); // rethrow the error after redirect
      })
    );
  }
}
