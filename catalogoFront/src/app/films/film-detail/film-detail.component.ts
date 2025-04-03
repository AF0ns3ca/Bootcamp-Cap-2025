// film-detail.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FilmService } from '../film.service';
import { Actor } from '../../actors/actor.model';  
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChangeDetectorRef } from '@angular/core';
import { Category } from 'src/app/categories/category.model';
import { CategoryService } from 'src/app/categories/category.service';


@Component({
  selector: 'app-film-detail',
  templateUrl: './film-detail.component.html',
  styleUrls: ['./film-detail.component.css'],
  standalone: true,  
  imports: [CommonModule, RouterLink, ReactiveFormsModule, FormsModule], 
})

export class FilmDetailComponent implements OnInit {
  filmId: number | null = null;
  film: any; 
  actors: Actor[] = []; 
  actorId: number | null = null;
  categories: Category[] = []; 
  categoriesAll: Category[] = [];
  categoryId: number | null = null;  
  

  constructor(
    private route: ActivatedRoute,
    private filmService: FilmService,
    private categoryService: CategoryService,
    private cdRef: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.filmId = +params['id'];
      if (this.filmId !== null) {
        this.loadFilmDetails();
        this.loadActors();
        this.loadCategories();
      }
      this.loadAllCategories();
    });
  }

  loadFilmDetails(): void {
    if (this.filmId !== null) {
      this.filmService.getFilmDetails(this.filmId).subscribe((data) => {
        this.film = data;
        console.log('Detalles de la película:', this.film);
      });
    }
  }
  

  loadActors(): void {
    if (this.filmId !== null) {
      this.filmService.getActorsInFilm(this.filmId).subscribe((actors: Actor[]) => {
        this.actors = actors;
        console.log('Actores en la película:', this.actors);
      });
    }
  }

  loadCategories(): void {
    if (this.filmId !== null) {
      this.filmService.getCategoriesInFilm(this.filmId).subscribe((categories: Category[]) => {
        this.categories = categories;
        console.log('Categorías en la película:', this.categories);
      });
    }
  }

  loadAllCategories(): void {
    this.categoryService.getCategories().subscribe((categoriesAll: Category[]) => {
      this.categoriesAll = categoriesAll;
      console.log('Todas las categorías:', this.categoriesAll); 
    });
  }

  addActorToFilm(): void {
    if (this.filmId && this.actorId && !isNaN(this.filmId) && !isNaN(this.actorId)) {
      this.filmService.addActorToFilm(this.filmId, this.actorId).subscribe(
        (response) => {
          console.log('Respuesta recibida:', response);
          if (response && response.message) {
            alert(response.message);
            this.loadFilmDetails(); 
            this.loadActors();
          } else {
            console.error('Respuesta inesperada:', response);
            alert('Hubo un problema al añadir el actor.');
          }
        },
        (error) => {
          console.error('Error al añadir actor', error);
          alert('Error al añadir actor');
        }
      );
    } else {
      alert('Por favor, asegúrese de que tanto el ID de película como el ID de actor son válidos.');
    }
  }

  confirmDeleteActor(actorId: number): void {
    const confirmation = confirm('¿Estás seguro de que deseas eliminar a este actor de la película?');
    if (confirmation) {
      this.removeActorFromFilm(actorId);
    }
  }

  removeActorFromFilm(actorId: number): void {
    if (this.filmId && actorId) {
      this.filmService.removeActorFromFilm(this.filmId, actorId).subscribe(
        (response) => {
          console.log('Respuesta recibida:', response);
          alert(response.message); 
          this.loadActors(); 
        },
        (error) => {
          console.error('Error al eliminar actor', error);
          alert('Error al eliminar actor');
        }
      );
    } else {
      alert('Por favor, asegúrese de que tanto el ID de película como el ID de actor son válidos.');
    }
  }


  addCategoryToFilm(): void {
    if (this.filmId && this.categoryId && !isNaN(this.filmId) && !isNaN(this.categoryId)) {
        this.filmService.addCategoryToFilm(this.filmId, this.categoryId).subscribe(
            (response) => {
                console.log('Respuesta recibida:', response);
                if (response && response.message) {
                    alert(response.message); 
                    this.loadFilmDetails(); 
                    this.loadCategories() 
                } else {
                    console.error('Respuesta inesperada:', response);
                    alert('Hubo un problema al añadir la categoría.');
                }
            },
            (error) => {
                console.error('Error al añadir categoría', error);
                alert('Error al añadir categoría');
            }
        );
    } else {
        alert('Por favor, asegúrese de que tanto el ID de película como el ID del actor son válidos.');
    }
}

confirmDeleteCategory(categoryId: number): void {
  const confirmation = confirm('¿Estás seguro de que deseas eliminar esta categoria de la peliculas?');
  if (confirmation) {
    this.removeCategoryFromFilm(categoryId);
  }
}

removeCategoryFromFilm(categoryId: number): void {
  if (this.filmId && categoryId) {
    this.filmService.removeCategoryFromFilm(this.filmId, categoryId).subscribe(
      (response) => {
        console.log('Respuesta recibida:', response);
        alert(response.message);
        this.loadCategories(); 
      },
      (error) => {
        console.error('Error al eliminar la categoria', error);
        alert('Error al eliminar la categoria');
      }
    );
  } else {
    alert('Por favor, asegúrese de que tanto el ID de película como el ID de la categoria son válidos.');
  }
}

  
  
  

  
  
}
