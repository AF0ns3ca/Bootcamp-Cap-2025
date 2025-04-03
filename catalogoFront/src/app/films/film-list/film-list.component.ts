import { Component, OnInit } from '@angular/core';
import { FilmService } from '../film.service';
import { Film } from '../film.model';
import { Pageable } from '../../core/model/Pageable';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router'; 
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, ReactiveFormsModule],
})

export class FilmListComponent implements OnInit {
  films: Film[] = [];
  pageable: Pageable = { pageNumber: 0, pageSize: 10 };
  totalElements: number = 0;
  totalPages: number = 0;
  currentPage: number = 0;

  constructor(private filmService: FilmService, private router: Router) {}

  ngOnInit(): void {
    this.loadFilms();
  }

  loadFilms(): void {
    this.filmService.getFilms(this.pageable).subscribe((data: any) => {
      this.films = data.content;
      this.totalElements = data.page.totalElements;
      this.totalPages = data.page.totalPages;
      this.currentPage = data.page.number;
    });
  }

  goToPreviousPage(): void {
    if (this.pageable.pageNumber > 0) {
      this.pageable.pageNumber--;
      this.loadFilms();
    }
  }

  goToNextPage(): void {
    if (this.pageable.pageNumber < this.totalPages - 1) {
      this.pageable.pageNumber++;
      this.loadFilms();
    }
  }

  goToFirstPage(): void {
    this.pageable.pageNumber = 0;
    this.loadFilms();
  }

  goToLastPage(): void {
    this.pageable.pageNumber = this.totalPages - 1;
    this.loadFilms();
  }

  goToPage(pageNumber: number): void {
    this.pageable.pageNumber = pageNumber;
    this.loadFilms();
  }

  onPageSizeChange(): void {
    this.pageable.pageNumber = 0; 
    this.loadFilms(); 
  }

  get pages() {
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }

  onDeleteFilm(id: number, event: Event): void {
    event.stopPropagation(); 
    if (confirm('¿Estás seguro de que deseas eliminar esta película?')) {
      this.filmService.deleteActor(id).subscribe(
        () => {
          this.loadFilms();
          alert('Pelicula eliminada con éxito');
          this.router.navigate(['/films']); 
        },
        (error) => {
          console.error('Error al eliminar la pelicula', error);
          alert('No se pudo eliminar la pelicula');
        }
      );
    }
  }

}
