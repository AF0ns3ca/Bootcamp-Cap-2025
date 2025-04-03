import { Component, OnInit } from '@angular/core'; // Ajusta la ruta si es necesario
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';  // Si usas RouterLink para navegación
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
  loading: boolean = true;  // Para manejar el estado de carga
  errorMessage: string = '';  // Para manejar errores de la API
  languageId: number | null = null;  // Declaramos categoryId
  peliculas: Film[] = [];  // Declaramos peliculas como un array vacío de tipo Film
  
  constructor(private languageService: LanguageService) { }

  ngOnInit(): void {
    this.loadLanguages();
  }

  loadLanguages(): void {
    // Llamada al servicio para obtener las categorías
    this.languageService.getLanguages().subscribe(
      data => {
        this.languages = data;  // Asigna los datos a la propiedad categories
        this.loading = false;     // Establece que la carga ha terminado
      },
      error => {
        this.errorMessage = 'Hubo un error al cargar los idiomas';  // Mensaje de error
        this.loading = false;  // Asegúrate de cambiar el estado de carga
        console.error(error);  // Loguea el error
      }
    );
  }

  getLanguageMovies(): void {
    this.languageService.getLanguageMovies(this.languageId!).subscribe(
      (films: Film[]) => {
        this.peliculas = films;  // Asume que la respuesta es un array de películas
      },
      (error) => {
        console.error('Error al obtener las películas del idioma:', error);
      }
    );
  }

  onDeleteLanguage(languageId: number, event: Event): void {
    event.stopPropagation();  // Evita que el evento de clic se propague
  
    if (confirm('¿Estás seguro de que deseas eliminar esta categoría?')) {
      this.languageService.removeLanguage(languageId).subscribe(
        (response) => {
          console.log('Categoría eliminada:', response);
          alert('Categoría eliminada con éxito');
          this.loadLanguages();  // Recargar la lista de categorías
        },
        (error) => {
          console.error('Error al eliminar categoría', error);
          alert('Hubo un error al eliminar la categoría');
        }
      );
    }
  }
  
}

