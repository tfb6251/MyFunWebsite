import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Telem } from './telem';
import { DistanceData } from './distanceData';
import { MessageService } from '../message.service';
import { interval, switchMap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TelemService {
  private telemsUrl = 'http://localhost:8080/telem';
  
  httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  
  constructor(
    private http: HttpClient,
    private messageService: MessageService     
  ) { }

  getDistance(): Observable<DistanceData> {
    // Poll every 500ms
    return interval(500).pipe(
      switchMap(() =>
        this.http.get<DistanceData>('http://localhost:3000/distance').pipe(
          catchError(
            this.handleError<DistanceData>('getDistance', {
              ft: 0,
              m: 0,
              in: 0,
              cm: 0,
              us: 0
            })
          )
        )
      )
    )
  }
  
    getTelems(): Observable<Telem[]> {
      return this.http.get<Telem[]>(this.telemsUrl)
        .pipe(
          tap(_ => this.log('fetched telems')),
          catchError(this.handleError<Telem[]>('getTelems', []))
        );
    }
  
    getTelemNo404<Data>(id: number): Observable<Telem> {
      const url = `${this.telemsUrl}/?id=${id}`;
      return this.http.get<Telem[]>(url)
        .pipe(
          map(telems => telems[0]), // returns a {0|1} element array
          tap(h => {
            const outcome = h ? 'fetched' : 'did not find';
            this.log(`${outcome} telem id=${id}`);
          }),
          catchError(this.handleError<Telem>(`getTelem id=${id}`))
        );
    }
  
    getTelem(id: number): Observable<Telem> {
      const url = `${this.telemsUrl}/${id}`;
      return this.http.get<Telem>(url).pipe(
        tap(_ => this.log(`fetched telem id=${id}`)),
        catchError(this.handleError<Telem>(`getTelem id=${id}`))
      );
    }
  
    searchTelems(term: string): Observable<Telem[]> {
      if (!term.trim()) {
        // if not search term, return empty telem array.
        return of([]);
      }
      return this.http.get<Telem[]>(`${this.telemsUrl}/?name=${term}`).pipe(
        tap(x => x.length ?
           this.log(`found telems matching "${term}"`) :
           this.log(`no telems matching "${term}"`)),
        catchError(this.handleError<Telem[]>('searchTelems', []))
      );
    }
  
    //////// Save methods //////////
  
    /** POST: add a new telem to the server */
    addTelem(telem: Telem): Observable<Telem> {
      return this.http.post<Telem>(this.telemsUrl, telem, this.httpOptions).pipe(
        tap((newTelem: Telem) => this.log(`added telem w/ id=${newTelem.Id}`)),
        catchError(this.handleError<Telem>('addTelem'))
      );
    }
  
    /** DELETE: delete the telem from the server */
    deleteTelem(id: number): Observable<Telem> {
      const url = `${this.telemsUrl}/${id}`;
  
      return this.http.delete<Telem>(url, this.httpOptions).pipe(
        tap(_ => this.log(`deleted telem id=${id}`)),
        catchError(this.handleError<Telem>('deleteTelem'))
      );
    }
  
    /** PUT: update the telem on the server */
    updateTelem(telem: Telem): Observable<any> {
      return this.http.put(this.telemsUrl, telem, this.httpOptions).pipe(
        tap(_ => this.log(`updated telem id=${telem.Id}`)),
        catchError(this.handleError<any>('updateTelem'))
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
  
    /** Log a TelemService message with the MessageService */
    private log(message: string) {
      this.messageService.add(`TelemService: ${message}`);
    }

}
