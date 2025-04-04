import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
// import { Film } from './film.model';
import { Pageable } from '../core/model/Pageable';
import { FilmPage } from '../core/model/FilmPage';
import { Film } from './film.model';
import { Actor } from '../actors/actor.model';

@Injectable({
  providedIn: 'root', 
})
export class FilmService {
  // private apiUrl = 'http://localhost:8001/films/v1';
  // private apiUrl = 'http://localhost:8001/films/v1?mode=details'; // Ajusta la URL según tu API

  constructor(private http: HttpClient) {}
  private baseUrl = 'http://localhost:8001/films/v1';
  private lanUrl = 'http://localhost:8001/language/v1';
  private catUrl = 'http://localhost:8001/category/v1';

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
    return this.http.get<Film>(`${this.baseUrl}/${id}`); 
  }

  getActorsInFilm(id: number): Observable<Actor[]> {
    return this.http.get<Actor[]>(`${this.baseUrl}/${id}/reparto`);
  }

  getCategoriesInFilm(id: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/${id}/categorias`);
  }

  addCategoryToFilm(filmId: number, categoryId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/${filmId}/category/${categoryId}`, {});
  }

  removeCategoryFromFilm(filmId: number, categoryId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/${filmId}/category/${categoryId}`);
  }

  addActorToFilm(filmId: number, actorId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/${filmId}/actors/${actorId}`, {});
  }

  removeActorFromFilm(filmId: number, actorId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}/${filmId}/actors/${actorId}`);
  }

  getLanguages(): Observable<any[]> {
    return this.http.get<any[]>(`${this.lanUrl}`); 
  }

  addFilm(film: Film): Observable<Film> {
    return this.http.post<Film>(this.baseUrl, film); 
  }

  updateFilm(id: number, film: Film): Observable<Film> {
    const url = `${this.baseUrl}/${id}`;
    return this.http.put<Film>(url, film); 
  }

  deleteActor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  getNovedades(fecha: string): Observable<Film[]> {
    console.log('Petición al endpoint con fecha:', fecha);
    return this.http.get<Film[]>(`${this.baseUrl}/novedades?fecha=${fecha}`);
  }
  
}
