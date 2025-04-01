

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';  // Importa el modelo de Actor si lo tienes
import { Pageable } from '../core/model/Pageable';
import { ActorPage } from '../core/model/ActorPage';
@Injectable({
  providedIn: 'root'  // O, si no estás usando 'providedIn: root', también puedes proveer el servicio en el módulo
})
export class ActorService {

  private apiUrl = 'http://localhost:8001/actores/v1';  // Ajusta la URL según tu API

  constructor(private http: HttpClient) { }

  getActors(pageable: Pageable): Observable<ActorPage> {
    const params = new HttpParams()
      .set('page', pageable.pageNumber.toString())
      .set('size', pageable.pageSize.toString())
  
    return this.http.get<ActorPage>(this.apiUrl, { params });
  }
}

