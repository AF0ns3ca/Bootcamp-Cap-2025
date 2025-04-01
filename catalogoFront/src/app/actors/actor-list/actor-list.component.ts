import { Component, OnInit } from '@angular/core';
import { ActorService } from '../actor.service';  // Ajusta la ruta si es necesario
import { CommonModule } from '@angular/common';  // Asegúrate de importar CommonModule
import { Actor } from '../actor.model';
import { FormsModule } from '@angular/forms';
import { Pageable } from 'src/app/core/model/Pageable';

@Component({
  selector: 'app-actor-list',  // Este es el nombre del componente en HTML
  templateUrl: './actor-list.component.html',
  styleUrls: ['./actor-list.component.css'],
  standalone: true,  // Marca el componente como independiente
  imports: [CommonModule, FormsModule]  // Importa CommonModule para usar directivas comunes (como *ngFor)
})
export class ActorListComponent implements OnInit {
  actors: Actor[] = [];
  pageable: Pageable = { pageNumber: 0, pageSize: 12 };
    totalElements: number = 0;
    totalPages: number = 0;
    currentPage: number = 0;

  constructor(private actorService: ActorService) { }

  ngOnInit(): void {
    this.loadActors(); // Cargar las películas al inicio
  }

  loadActors(): void {
    this.actorService.getActors(this.pageable).subscribe((data: any) => {
      this.actors = data.content;
      this.totalElements = data.page.totalElements;
      this.totalPages = data.page.totalPages;
      this.currentPage = data.page.number;
    });
  }

  goToPreviousPage(): void {
    if (this.pageable.pageNumber > 0) {
      this.pageable.pageNumber--;
      this.loadActors();
    }
  }

  goToNextPage(): void {
    if (this.pageable.pageNumber < this.totalPages - 1) {
      this.pageable.pageNumber++;
      this.loadActors();
    }
  }

  goToFirstPage(): void {
    this.pageable.pageNumber = 0;
    this.loadActors();
  }

  goToLastPage(): void {
    this.pageable.pageNumber = this.totalPages - 1;
    this.loadActors();
  }

  goToPage(pageNumber: number): void {
    this.pageable.pageNumber = pageNumber;
    this.loadActors();
  }

  onPageSizeChange(): void {
    this.pageable.pageNumber = 0; // Restablecer a la primera página al cambiar el tamaño de la página
    this.loadActors(); // Recargar las películas con el nuevo tamaño de página
  }

  get pages() {
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }
}

