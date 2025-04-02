import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
// import { Film } from './film.model';
import { Pageable } from '../core/model/Pageable';
import { FilmPage } from '../core/model/FilmPage';
import { Film } from './film.model';
import { Actor } from '../actors/actor.model';

@Injectable({
  providedIn: 'root', // O, si no estás usando 'providedIn: root', también puedes proveer el servicio en el módulo
})
export class FilmService {
  // private apiUrl = 'http://localhost:8001/films/v1';
  // private apiUrl = 'http://localhost:8001/films/v1?mode=details'; // Ajusta la URL según tu API

  constructor(private http: HttpClient) {}
  private baseUrl = 'http://localhost:8001/films/v1';
  private lanUrl = 'http://localhost:8001/language/v1'; // Cambia la URL según corresponda

  // getFilms(): Observable<Film[]> {
  //   return this.http.get<Film[]>(this.apiUrl);  // Esta es la llamada HTTP
  // }
  getFilms(pageable: Pageable): Observable<FilmPage> {
    const params = new HttpParams()
      .set('page', pageable.pageNumber.toString())
      .set('size', pageable.pageSize.toString());

    return this.http.get<FilmPage>(`${this.baseUrl}?mode=details`, { params });
  }

  getFilmDetails(id: number): Observable<Film> {
    return this.http.get<Film>(`${this.baseUrl}/${id}`); // Debería devolver un solo objeto, no una lista
  }

  getActorsInFilm(id: number): Observable<Actor[]> {
    return this.http.get<Actor[]>(`${this.baseUrl}/${id}/reparto`);
  }

  getLanguages(): Observable<any[]> {
    return this.http.get<any[]>(`${this.lanUrl}`); // Cambia la URL según corresponda
  }

  addFilm(film: Film): Observable<Film> {
    return this.http.post<Film>(this.baseUrl, film); // Envia el objeto actor al backend
  }

  updateFilm(id: number, film: Film): Observable<Film> {
    const url = `${this.baseUrl}/${id}`; // El ID del actor debe ir en la URL
    return this.http.put<Film>(url, film); // Se utiliza PUT para actualizar
  }
}
