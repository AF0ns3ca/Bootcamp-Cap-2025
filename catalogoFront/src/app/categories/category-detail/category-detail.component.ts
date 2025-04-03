import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CategoryService } from '../category.service';  
import { Film } from 'src/app/films/film.model';  
import { Category } from '../category.model';  
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-category-detail',
  templateUrl: './category-detail.component.html',
  styleUrls: ['./category-detail.component.css'],
  standalone: true,  
  imports: [CommonModule, RouterLink], 
})
export class CategoryDetailComponent implements OnInit {
  categoryId: number | null = null;
  categoryName: string = '';  
  peliculas: Film[] = [];
   

  constructor(
    private route: ActivatedRoute,
    private categoryService: CategoryService
  ) {}

  ngOnInit(): void {
   
    this.route.params.subscribe(params => {
      this.categoryId = +params['id'];  

      
      if (this.categoryId) {
        this.loadCategoryDetails();  
        this.getCategoryMovies();    
      }
    });
  }

  loadCategoryDetails(): void {
    
    this.categoryService.getCategoryById(this.categoryId!).subscribe(
      (category: Category) => {
        this.categoryName = category.categoria;  
      },
      (error) => {
        console.error('Error al obtener los detalles de la categoría:', error);
      }
    );
  }

  getCategoryMovies(): void {
    console.log('Category ID:', this.categoryId);  
    if (this.categoryId) {
      this.categoryService.getCategoryMovies(this.categoryId).subscribe(
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
