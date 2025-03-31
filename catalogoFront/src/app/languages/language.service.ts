

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

  getActors(): Observable<Language[]> {
    return this.http.get<Language[]>(this.apiUrl);  // Esta es la llamada HTTP
  }
}
