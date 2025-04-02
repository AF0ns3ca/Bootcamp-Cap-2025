import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from './category.model';  // Asegúrate de tener el modelo de Category
import { Film } from '../films/film.model';  // Modelo Film si lo tienes

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiUrl = 'http://localhost:8001/category/v1';  // API base para categorías

  constructor(private http: HttpClient) {}

  // Método para obtener las películas de una categoría por su ID
  getCategoryMovies(categoryId: number): Observable<Film[]> {
    return this.http.get<Film[]>(`${this.apiUrl}/${categoryId}/peliculas`);  // Devuelve un array de películas
  }

  // Método para obtener los detalles de la categoría (por ejemplo, el nombre)
  getCategoryById(categoryId: number): Observable<Category> {
    return this.http.get<Category>(`${this.apiUrl}/${categoryId}`);  // Devuelve el detalle de la categoría
  }

  // Método para obtener todas las categorías
  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiUrl}`);  // Devuelve un array de categorías
  }
}
