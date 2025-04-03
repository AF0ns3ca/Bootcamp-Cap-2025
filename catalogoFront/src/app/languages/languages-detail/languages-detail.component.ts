import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router'; // Asegúrate de importar el servicio
import { Film } from 'src/app/films/film.model';   // Asegúrate de tener el modelo adecuado para Category
import { CommonModule } from '@angular/common';
import { Language } from '../language.model';
import { LanguageService } from '../language.service';

@Component({
  selector: 'app-languages-detail',
  templateUrl: './languages-detail.component.html',
  styleUrl: './languages-detail.component.css',
  standalone: true,  // Marca el componente como independiente
  imports: [CommonModule, RouterLink],  // Aquí puedes agregar otros módulos que necesites
})
export class LanguagesDetailComponent implements OnInit{

  languageId: number | null = null;
  languageName: string = '';  // Nombre de la categoría
  peliculas: Film[] = [];
    // Películas que pertenecen a esta categoría

  constructor(
    private route: ActivatedRoute,
    private languageService: LanguageService
  ) {}

  ngOnInit(): void {
    // Capturar el 'languageId' de la URL
    this.route.params.subscribe(params => {
      this.languageId = +params['id'];  // Asegúrate de que languageId sea un número

      // Verificamos que tenemos un languageId válido
      if (this.languageId) {
        this.loadLanguageDetails();  // Cargar los detalles de la categoría
        this.getLanguageMovies();    // Cargar las películas de la categoría
      }
    });
  }

  loadLanguageDetails(): void {
    // Obtener los detalles de la categoría, como el nombre
    this.languageService.getLanguageById(this.languageId!).subscribe(
      (language: Language) => {
        this.languageName = language.idioma;  // Asume que la respuesta tiene un campo 'name' para el nombre de la categoría
      },
      (error) => {
        console.error('Error al obtener los detalles de la categoría:', error);
      }
    );
  }

  getLanguageMovies(): void {
    console.log('Language ID:', this.languageId);  // Verifica que languageId sea correcto
    if (this.languageId) {
      this.languageService.getLanguageMovies(this.languageId).subscribe(
        (films: Film[]) => {
          console.log('Datos de películas recibidos:', films);  // Log de los datos
          this.peliculas = films;
        },
        (error) => {
          console.error('Error al obtener las películas de la categoría:', error);
        }
      );
    }
  }
  
}
