

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
// import { Film } from './film.model';
import { Pageable } from '../core/model/Pageable';
import { FilmPage } from '../core/model/FilmPage';
import { Film } from './film.model';

@Injectable({
  providedIn: 'root'  // O, si no estás usando 'providedIn: root', también puedes proveer el servicio en el módulo
})
export class FilmService {

  // private apiUrl = 'http://localhost:8001/films/v1'; 
  // private apiUrl = 'http://localhost:8001/films/v1?mode=details'; // Ajusta la URL según tu API
  

  constructor(private http: HttpClient) {}
  private baseUrl = 'http://localhost:8001/films/v1';

  // getFilms(): Observable<Film[]> {
  //   return this.http.get<Film[]>(this.apiUrl);  // Esta es la llamada HTTP
  // }
  getFilms(pageable: Pageable): Observable<FilmPage> {
    const params = new HttpParams()
      .set('page', pageable.pageNumber.toString())
      .set('size', pageable.pageSize.toString())
  
    return this.http.get<FilmPage>(`${this.baseUrl}?mode=details`, { params });
  }

  getFilmDetails(id: number): Observable<Film> {
    return this.http.get<Film>(`${this.baseUrl}/${id}`);  // Debería devolver un solo objeto, no una lista
  }
}


