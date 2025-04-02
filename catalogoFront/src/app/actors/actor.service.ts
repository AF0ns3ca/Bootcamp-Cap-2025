

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, Observable, tap } from 'rxjs';  // Importa el modelo de Actor si lo tienes
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

  getActorDetails(id: number): Observable<any> {  // Cambia 'any' por el tipo de datos que esperas recibir
    return this.http.get<any>(`${this.apiUrl}/${id}`);  // Debería devolver un solo objeto, no una lista
  }

  getActorMovies(id: string): Observable<any> {
    const url = `${this.apiUrl}/${id}/pelis`;
    console.log(`Llamada a la API con la URL: ${url}`);
  
    return this.http.get<any>(url).pipe(
      tap(response => {
        console.log('Respuesta de la API:', response); // Asegúrate de que la respuesta sea un array de películas
      }),
      map(response => {
        // No es necesario hacer response.List.map, porque la respuesta ya es un array
        return response.map((item: any) => ({
          filmId: item.id,
          title: item.titulo,
        }));
      })
    );
  }
  
  

}

