import { Component, OnInit } from '@angular/core';
import { ActorService } from '../actor.service';  // Ajusta la ruta si es necesario
import { CommonModule } from '@angular/common';  // Asegúrate de importar CommonModule
import { Actor } from '../actor.model';

@Component({
  selector: 'app-actor-list',  // Este es el nombre del componente en HTML
  templateUrl: './actor-list.component.html',
  styleUrls: ['./actor-list.component.css'],
  standalone: true,  // Marca el componente como independiente
  imports: [CommonModule]  // Importa CommonModule para usar directivas comunes (como *ngFor)
})
export class ActorListComponent implements OnInit {
  actors: Actor[] = [];

  constructor(private actorService: ActorService) { }

  ngOnInit(): void {
    this.actorService.getActors().subscribe(data => {
      this.actors = data;
    });
  }
}
