

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, Observable, tap } from 'rxjs';  
import { Pageable } from '../core/model/Pageable';
import { ActorPage } from '../core/model/ActorPage';
import { Actor } from './actor.model';
@Injectable({
  providedIn: 'root'  
})
export class ActorService {

  private apiUrl = 'http://localhost:8001/actores/v1'; 

  constructor(private http: HttpClient) { }

  getActors(pageable: Pageable): Observable<ActorPage> {
    const params = new HttpParams()
      .set('page', pageable.pageNumber.toString())
      .set('size', pageable.pageSize.toString())
  
    return this.http.get<ActorPage>(this.apiUrl, { params });
  }

  getActorDetails(id: number): Observable<any> {  
    return this.http.get<any>(`${this.apiUrl}/${id}`);  
  }

  getActorMovies(id: string): Observable<any> {
    const url = `${this.apiUrl}/${id}/pelis`;
    console.log(`Llamada a la API con la URL: ${url}`);
  
    return this.http.get<any>(url).pipe(
      tap(response => {
        console.log('Respuesta de la API:', response); 
      }),
      map(response => {
        
        return response.map((item: any) => ({
          filmId: item.id,
          title: item.titulo,
        }));
      })
    );
  }

  addActor(actor: Actor): Observable<Actor> {
    return this.http.post<Actor>(this.apiUrl, actor); 
  }

  updateActor(id: number, actor: Actor): Observable<Actor> {
    const url = `${this.apiUrl}/${id}`;  
    return this.http.put<Actor>(url, actor);  
  }
  
  
  deleteActor(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
  

}

