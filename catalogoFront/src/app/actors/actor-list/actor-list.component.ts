import { Component, OnInit } from '@angular/core';
import { ActorService } from '../actor.service';
import { CommonModule } from '@angular/common'; 
import { Actor } from '../actor.model';
import { FormsModule } from '@angular/forms';
import { Pageable } from 'src/app/core/model/Pageable';
import { RouterModule } from '@angular/router';
import { Router } from '@angular/router';

@Component({
  selector: 'app-actor-list',
  templateUrl: './actor-list.component.html',
  styleUrls: ['./actor-list.component.css'],
  standalone: true, 
  imports: [CommonModule, FormsModule, RouterModule],
})
export class ActorListComponent implements OnInit {
  actors: Actor[] = [];
  pageable: Pageable = { pageNumber: 0, pageSize: 10 };
  totalElements: number = 0;
  totalPages: number = 0;
  currentPage: number = 0;
  

  constructor(private actorService: ActorService, private router: Router) {}

  ngOnInit(): void {
    this.loadActors(); 
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
    this.pageable.pageNumber = 0; 
    this.loadActors(); 
  }

  get pages() {
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }

  onDeleteActor(id: number, event: Event): void {
    event.stopPropagation(); 
    if (confirm('¿Estás seguro de que deseas eliminar a este actor?')) {
      this.actorService.deleteActor(id).subscribe(
        () => {
          this.loadActors(); 
          alert('Actor eliminado con éxito');
          this.router.navigate(['/actors']); 
        },
        (error) => {
          console.error('Error al eliminar actor', error);
          alert('No se pudo eliminar el actor');
        }
      );
    }
  }
}
