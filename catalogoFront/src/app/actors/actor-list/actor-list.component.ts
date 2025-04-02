import { Component, OnInit } from '@angular/core';
import { ActorService } from '../actor.service'; // Ajusta la ruta si es necesario
import { CommonModule } from '@angular/common'; // Asegúrate de importar CommonModule
import { Actor } from '../actor.model';
import { FormsModule } from '@angular/forms';
import { Pageable } from 'src/app/core/model/Pageable';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-actor-list', // Este es el nombre del componente en HTML
  templateUrl: './actor-list.component.html',
  styleUrls: ['./actor-list.component.css'],
  standalone: true, // Marca el componente como independiente
  imports: [CommonModule, FormsModule, RouterModule], // Importa CommonModule para usar directivas comunes (como *ngFor)
})
export class ActorListComponent implements OnInit {
  actors: Actor[] = [];
  pageable: Pageable = { pageNumber: 0, pageSize: 10 };
  totalElements: number = 0;
  totalPages: number = 0;
  currentPage: number = 0;
  

  constructor(private actorService: ActorService, private router: Router) {}

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

  onDeleteActor(id: number, event: Event): void {
    event.stopPropagation(); // Detiene la propagación del evento
    if (confirm('¿Estás seguro de que deseas eliminar a este actor?')) {
      this.actorService.deleteActor(id).subscribe(
        () => {
          this.loadActors(); // Recarga la lista de actores
          alert('Actor eliminado con éxito');
          this.router.navigate(['/actors']); // Redirige a la lista de actores después de la eliminación
        },
        (error) => {
          console.error('Error al eliminar actor', error);
          alert('No se pudo eliminar el actor');
        }
      );
    }
  }
}
