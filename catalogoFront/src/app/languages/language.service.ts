

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Language } from './language.model'; 
import { Film } from '../films/film.model';

@Injectable({
  providedIn: 'root'  
})
export class LanguageService {

  private apiUrl = 'http://localhost:8001/language/v1'; 

  constructor(private http: HttpClient) { }
  

    getLanguageById(languageId: number): Observable<Language> {
      return this.http.get<any>(`${this.apiUrl}/${languageId}`); 
    }

    getLanguages(): Observable<Language[]> {
      return this.http.get<Language[]>(`${this.apiUrl}`);
    }
  

    removeLanguage(languageId: number): Observable<any> {
      return this.http.delete<any>(`${this.apiUrl}/${languageId}`); 
    }
  

    addLanguage(Language: Language): Observable<Language> {
      return this.http.post<Language>(this.apiUrl, Language);  
    }
  
    
    updateLanguage(languageId: number, Language: Language): Observable<Language> {
      return this.http.put<Language>(`${this.apiUrl}/${languageId}`, Language); 
    }

    getLanguageMovies(languageId: number): Observable<Film[]> {
        return this.http.get<Film[]>(`${this.apiUrl}/${languageId}/peliculas`);  
      }



}
