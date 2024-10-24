import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Computer } from './computer';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class ComputerService {
  private computersUrl = 'http://localhost:8080/computer';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService     
  ) { }

  /** GET computers from the server */
  getComputers(): Observable<Computer[]> {
    return this.http.get<Computer[]>(this.computersUrl)
      .pipe(
        tap(_ => this.log('fetched computers')),
        catchError(this.handleError<Computer[]>('getComputers', []))
      );
  }

  /** GET computer by id. Return `undefined` when id not found */
  getComputerNo404<Data>(id: number): Observable<Computer> {
    const url = `${this.computersUrl}/?id=${id}`;
    return this.http.get<Computer[]>(url)
      .pipe(
        map(computers => computers[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} computer id=${id}`);
        }),
        catchError(this.handleError<Computer>(`getComputer id=${id}`))
      );
  }

  /** GET computer by id. Will 404 if id not found */
  getComputer(id: number): Observable<Computer> {
    const url = `${this.computersUrl}/${id}`;
    return this.http.get<Computer>(url).pipe(
      tap(_ => this.log(`fetched computer id=${id}`)),
      catchError(this.handleError<Computer>(`getComputer id=${id}`))
    );
  }

  /* GET computers whose name contains search term */
  searchComputers(term: string): Observable<Computer[]> {
    if (!term.trim()) {
      // if not search term, return empty computer array.
      return of([]);
    }
    return this.http.get<Computer[]>(`${this.computersUrl}/?name=${term}`).pipe(
      tap(x => x.length ?
         this.log(`found computers matching "${term}"`) :
         this.log(`no computers matching "${term}"`)),
      catchError(this.handleError<Computer[]>('searchComputers', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new computer to the server */
  addComputer(computer: Computer): Observable<Computer> {
    return this.http.post<Computer>(this.computersUrl, computer, this.httpOptions).pipe(
      tap((newComputer: Computer) => this.log(`added computer w/ id=${newComputer.name}`)),
      catchError(this.handleError<Computer>('addComputer'))
    );
  }

  /** DELETE: delete the computer from the server */
  deleteComputer(id: number): Observable<Computer> {
    const url = `${this.computersUrl}/${id}`;

    return this.http.delete<Computer>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted computer id=${id}`)),
      catchError(this.handleError<Computer>('deleteComputer'))
    );
  }

  /** PUT: update the computer on the server */
  updateComputer(computer: Computer): Observable<any> {
    return this.http.put(this.computersUrl, computer, this.httpOptions).pipe(
      tap(_ => this.log(`updated computer id=${computer.name}`)),
      catchError(this.handleError<any>('updateComputer'))
    );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a ComputerService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`ComputerService: ${message}`);
  }

}
