import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FilmService {

  private apiUrl = `${environment.apiUrl}/films`; 

  constructor(private http: HttpClient) {}

  
}
