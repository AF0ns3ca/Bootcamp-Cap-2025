

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Actor } from './actor.model';  // Importa el modelo de Actor si lo tienes

@Injectable({
  providedIn: 'root'  // O, si no estás usando 'providedIn: root', también puedes proveer el servicio en el módulo
})
export class ActorService {

  private apiUrl = 'http://localhost:8001/actores/v1';  // Ajusta la URL según tu API

  constructor(private http: HttpClient) { }

  getActors(): Observable<Actor[]> {
    return this.http.get<Actor[]>(this.apiUrl);  // Esta es la llamada HTTP
  }
}

