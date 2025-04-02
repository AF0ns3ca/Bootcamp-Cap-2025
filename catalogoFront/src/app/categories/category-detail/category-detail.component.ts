import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CategoryService } from '../category.service';  // Asegúrate de importar el servicio
import { Film } from 'src/app/films/film.model';  // Asegúrate de tener el modelo adecuado para Film
import { Category } from '../category.model';  // Asegúrate de tener el modelo adecuado para Category
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-category-detail',
  templateUrl: './category-detail.component.html',
  styleUrls: ['./category-detail.component.css'],
  standalone: true,  // Marca el componente como independiente
  imports: [CommonModule, RouterLink],  // Aquí puedes agregar otros módulos que necesites
})
export class CategoryDetailComponent implements OnInit {
  categoryId: number | null = null;
  categoryName: string = '';  // Nombre de la categoría
  peliculas: Film[] = [];
    // Películas que pertenecen a esta categoría

  constructor(
    private route: ActivatedRoute,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
    // Capturar el 'categoryId' de la URL
    this.route.params.subscribe(params => {
      this.categoryId = +params['id'];  // Asegúrate de que categoryId sea un número

      // Verificamos que tenemos un categoryId válido
      if (this.categoryId) {
        this.loadCategoryDetails();  // Cargar los detalles de la categoría
        this.getCategoryMovies();    // Cargar las películas de la categoría
      }
    });
  }

  loadCategoryDetails(): void {
    // Obtener los detalles de la categoría, como el nombre
    this.categoryService.getCategoryById(this.categoryId!).subscribe(
      (category: Category) => {
        this.categoryName = category.categoria;  // Asume que la respuesta tiene un campo 'name' para el nombre de la categoría
      },
      (error) => {
        console.error('Error al obtener los detalles de la categoría:', error);
      }
    );
  }

  getCategoryMovies(): void {
    console.log('Category ID:', this.categoryId);  // Verifica que categoryId sea correcto
    if (this.categoryId) {
      this.categoryService.getCategoryMovies(this.categoryId).subscribe(
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
