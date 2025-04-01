

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from './category.model';  // Importa el modelo de Actor si lo tienes

@Injectable({
  providedIn: 'root'  // O, si no estás usando 'providedIn: root', también puedes proveer el servicio en el módulo
})
export class CategoryService {

  private apiUrl = 'http://localhost:8001/category/v1';  // Ajusta la URL según tu API

  constructor(private http: HttpClient) { }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.apiUrl);  // Esta es la llamada HTTP
  }
}
