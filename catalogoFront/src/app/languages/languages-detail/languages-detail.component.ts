import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { Film } from 'src/app/films/film.model';  
import { CommonModule } from '@angular/common';
import { Language } from '../language.model';
import { LanguageService } from '../language.service';

@Component({
  selector: 'app-languages-detail',
  templateUrl: './languages-detail.component.html',
  styleUrl: './languages-detail.component.css',
  standalone: true,  
  imports: [CommonModule, RouterLink],  
})
export class LanguagesDetailComponent implements OnInit{

  languageId: number | null = null;
  languageName: string = '';  
  peliculas: Film[] = [];
   

  constructor(
    private route: ActivatedRoute,
    private languageService: LanguageService
  ) {}

  ngOnInit(): void {
   
    this.route.params.subscribe(params => {
      this.languageId = +params['id'];  

      
      if (this.languageId) {
        this.loadLanguageDetails();  
        this.getLanguageMovies();    
      }
    });
  }

  loadLanguageDetails(): void {
   
    this.languageService.getLanguageById(this.languageId!).subscribe(
      (language: Language) => {
        this.languageName = language.idioma;  
      },
      (error) => {
        console.error('Error al obtener los detalles de la categoría:', error);
      }
    );
  }

  getLanguageMovies(): void {
    console.log('Language ID:', this.languageId);  
    if (this.languageId) {
      this.languageService.getLanguageMovies(this.languageId).subscribe(
        (films: Film[]) => {
          console.log('Datos de películas recibidos:', films);  
          this.peliculas = films;
        },
        (error) => {
          console.error('Error al obtener las películas de la categoría:', error);
        }
      );
    }
  }
  
}
