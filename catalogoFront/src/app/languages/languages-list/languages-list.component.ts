import { Component, OnInit } from '@angular/core'; 
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';  
import { Film } from 'src/app/films/film.model';
import { Language } from '../language.model';
import { LanguageService } from '../language.service';

@Component({
  selector: 'app-languages-list',
  imports: [CommonModule, RouterLink], 
  standalone: true,
  templateUrl: './languages-list.component.html',
  styleUrl: './languages-list.component.css'
})
export class LanguagesListComponent implements OnInit {


  languages: Language[] = [];
  loading: boolean = true;  
  errorMessage: string = ''; 
  languageId: number | null = null;  
  peliculas: Film[] = [];  
  
  constructor(private languageService: LanguageService) { }

  ngOnInit(): void {
    this.loadLanguages();
  }

  loadLanguages(): void {
  
    this.languageService.getLanguages().subscribe(
      data => {
        this.languages = data;  
        this.loading = false;    
      },
      error => {
        this.errorMessage = 'Hubo un error al cargar los idiomas';  
        this.loading = false; 
        console.error(error);  
      }
    );
  }

  getLanguageMovies(): void {
    this.languageService.getLanguageMovies(this.languageId!).subscribe(
      (films: Film[]) => {
        this.peliculas = films;  
      },
      (error) => {
        console.error('Error al obtener las películas del idioma:', error);
      }
    );
  }

  onDeleteLanguage(languageId: number, event: Event): void {
    event.stopPropagation(); 
  
    if (confirm('¿Estás seguro de que deseas eliminar esta categoría?')) {
      this.languageService.removeLanguage(languageId).subscribe(
        (response) => {
          console.log('Categoría eliminada:', response);
          alert('Categoría eliminada con éxito');
          this.loadLanguages(); 
        },
        (error) => {
          console.error('Error al eliminar categoría', error);
          alert('Hubo un error al eliminar la categoría');
        }
      );
    }
  }
  
}

