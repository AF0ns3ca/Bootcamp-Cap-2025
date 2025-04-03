import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from './category.model';  
import { Film } from '../films/film.model'; 

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiUrl = 'http://localhost:8001/category/v1'; 

  constructor(private http: HttpClient) {}


  getCategoryMovies(categoryId: number): Observable<Film[]> {
    return this.http.get<Film[]>(`${this.apiUrl}/${categoryId}/peliculas`); 
  }


  getCategoryById(categoryId: number): Observable<Category> {
    return this.http.get<any>(`${this.apiUrl}/${categoryId}`); 
  }


  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.apiUrl}`); 
  }


  removeCategory(categoryId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${categoryId}`);  
  }


  addCategory(category: Category): Observable<Category> {
    return this.http.post<Category>(this.apiUrl, category); 
  }


  updateCategory(categoryId: number, category: Category): Observable<Category> {
    return this.http.put<Category>(`${this.apiUrl}/${categoryId}`, category);
  }




}
