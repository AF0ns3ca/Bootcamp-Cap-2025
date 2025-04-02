import { Component, OnInit } from '@angular/core';
import { FilmService } from '../film.service';
import { Film } from '../film.model';
import { Pageable } from '../../core/model/Pageable';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router'; // Importamos Router para la navegación

@Component({
  selector: 'app-film-list',
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.css'],
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
})

export class FilmListComponent implements OnInit {
  films: Film[] = [];
  pageable: Pageable = { pageNumber: 0, pageSize: 10 };
  totalElements: number = 0;
  totalPages: number = 0;
  currentPage: number = 0;

  constructor(private filmService: FilmService) {}

  ngOnInit(): void {
    this.loadFilms(); // Cargar las películas al inicio
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
    this.pageable.pageNumber = 0; // Restablecer a la primera página al cambiar el tamaño de la página
    this.loadFilms(); // Recargar las películas con el nuevo tamaño de página
  }

  get pages() {
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }
}
