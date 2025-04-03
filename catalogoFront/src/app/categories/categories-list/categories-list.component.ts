import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../category.service';  
import { CommonModule } from '@angular/common';  
import { Category } from '../category.model';
import { RouterLink } from '@angular/router';  
import { Film } from 'src/app/films/film.model';

@Component({
  selector: 'app-categories-list',
  templateUrl: './categories-list.component.html',
  styleUrls: ['./categories-list.component.css'],
  standalone: true,  
  imports: [CommonModule, RouterLink] 
})
export class CategoriesListComponent implements OnInit {

  categories: Category[] = [];
  loading: boolean = true; 
  errorMessage: string = '';  
  categoryId: number | null = null;  
  peliculas: Film[] = [];  
  
  constructor(private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.loadCategories();
  }

  loadCategories(): void {
    
    this.categoryService.getCategories().subscribe(
      data => {
        this.categories = data;  
        this.loading = false;     
      },
      error => {
        this.errorMessage = 'Hubo un error al cargar las categorías';  
        this.loading = false;  
        console.error(error);  
      }
    );
  }

  getCategoryMovies(): void {
    this.categoryService.getCategoryMovies(this.categoryId!).subscribe(
      (films: Film[]) => {
        this.peliculas = films;  
      },
      (error) => {
        console.error('Error al obtener las películas de la categoría:', error);
      }
    );
  }

  onDeleteCategory(categoryId: number, event: Event): void {
    event.stopPropagation(); 
  
    if (confirm('¿Estás seguro de que deseas eliminar esta categoría?')) {
      this.categoryService.removeCategory(categoryId).subscribe(
        (response) => {
          console.log('Categoría eliminada:', response);
          alert('Categoría eliminada con éxito');
          this.loadCategories();  
        },
        (error) => {
          console.error('Error al eliminar categoría', error);
          alert('Hubo un error al eliminar la categoría');
        }
      );
    }
  }
  
}
