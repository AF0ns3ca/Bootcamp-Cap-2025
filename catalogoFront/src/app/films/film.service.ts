

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Film } from './film.model';

@Injectable({
  providedIn: 'root'  // O, si no estás usando 'providedIn: root', también puedes proveer el servicio en el módulo
})
export class FilmService {

  private apiUrl = 'http://localhost:8001/films/v1';  // Ajusta la URL según tu API

  constructor(private http: HttpClient) { }

  getActors(): Observable<Film[]> {
    return this.http.get<Film[]>(this.apiUrl);  // Esta es la llamada HTTP
  }
}

