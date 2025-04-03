

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Language } from './language.model';  // Importa el modelo de Actor si lo tienes

@Injectable({
  providedIn: 'root'  // O, si no estás usando 'providedIn: root', también puedes proveer el servicio en el módulo
})
export class LanguageService {

  private apiUrl = 'http://localhost:8001/language/v1';  // Ajusta la URL según tu API

  constructor(private http: HttpClient) { }
  
    // Método para obtener los detalles de la categoría (por ejemplo, el nombre)
    getLanguageById(languageId: number): Observable<Language> {
      return this.http.get<any>(`${this.apiUrl}/${languageId}`);  // Devuelve el detalle de la categoría
    }
  
    // Método para obtener todas las categorías
    getCategories(): Observable<Language[]> {
      return this.http.get<Language[]>(`${this.apiUrl}`);  // Devuelve un array de categorías
    }
  
    // Método para eliminar una categoría
    removeLanguage(languageId: number): Observable<any> {
      return this.http.delete<any>(`${this.apiUrl}/${languageId}`);  // Devuelve la respuesta de la eliminación
    }
  
    // Método para crear una nueva categoría
    addLanguage(Language: Language): Observable<Language> {
      return this.http.post<Language>(this.apiUrl, Language);  // Devuelve la categoría creada
    }
  
    // Método para actualizar una categoría
    updateLanguage(languageId: number, Language: Language): Observable<Language> {
      return this.http.put<Language>(`${this.apiUrl}/${languageId}`, Language);  // Devuelve la categoría actualizada
    }



}
