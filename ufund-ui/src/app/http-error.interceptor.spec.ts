import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { HttpErrorInterceptor } from './http-error.interceptor';

describe('HttpErrorInterceptor', () => {
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        { 
          provide: HTTP_INTERCEPTORS, 
          useClass: HttpErrorInterceptor, 
          multi: true 
        }
      ]
    });

    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('should be created', () => {
    const interceptor = TestBed.inject(HttpErrorInterceptor);
    expect(interceptor).toBeTruthy();
  });

  it('should handle 404 error', () => {
    //http request that will intentionally fail (404)
    httpClient.get('/nonexistent-url').subscribe({
      next: () => fail('should have failed with 404'),
      error: (error) => {
        expect(error.status).toBe(404);
        expect(error.error).toBeDefined();
      }
    });

    //mock the response
    const req = httpTestingController.expectOne('/nonexistent-url');
    req.flush('Not Found', { status: 404, statusText: 'Not Found' });

    httpTestingController.verify();
  });

  it('should handle 500 error', () => {
    //http request that will intentionally fail (500)
    httpClient.get('/server-error').subscribe({
      next: () => fail('should have failed with 500'),
      error: (error) => {
        expect(error.status).toBe(500);
        expect(error.error).toBeDefined();
      }
    });

    //mock the response
    const req = httpTestingController.expectOne('/server-error');
    req.flush('Internal Server Error', { status: 500, statusText: 'Internal Server Error' });

    httpTestingController.verify();
  });
});
