import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../category.service';  // Ajusta la ruta si es necesario
import { CommonModule } from '@angular/common';  // Asegúrate de importar CommonModule
import { Category } from '../category.model';
import { RouterLink } from '@angular/router';  // Si usas RouterLink para navegación
import { Film } from 'src/app/films/film.model';

@Component({
  selector: 'app-categories-list',
  templateUrl: './categories-list.component.html',
  styleUrls: ['./categories-list.component.css'],
  standalone: true,  // Marca el componente como independiente
  imports: [CommonModule, RouterLink]  // Importa CommonModule para usar directivas comunes (como *ngFor)
})
export class CategoriesListComponent implements OnInit {

  categories: Category[] = [];
  loading: boolean = true;  // Para manejar el estado de carga
  errorMessage: string = '';  // Para manejar errores de la API
  categoryId: number | null = null;  // Declaramos categoryId
  peliculas: Film[] = [];  // Declaramos peliculas como un array vacío de tipo Film
  
  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    // Llamada al servicio para obtener las categorías
    this.categoryService.getCategories().subscribe(
      data => {
        this.categories = data;  // Asigna los datos a la propiedad categories
        this.loading = false;     // Establece que la carga ha terminado
      },
      error => {
        this.errorMessage = 'Hubo un error al cargar las categorías';  // Mensaje de error
        this.loading = false;  // Asegúrate de cambiar el estado de carga
        console.error(error);  // Loguea el error
      }
    );
  }

  getCategoryMovies(): void {
    this.categoryService.getCategoryMovies(this.categoryId!).subscribe(
      (films: Film[]) => {
        this.peliculas = films;  // Asume que la respuesta es un array de películas
      },
      (error) => {
        console.error('Error al obtener las películas de la categoría:', error);
      }
    );
  }
}
