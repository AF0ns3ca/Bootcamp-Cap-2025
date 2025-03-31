


import { Component, OnInit } from '@angular/core';
import { FilmService } from '../film.service';  // Ajusta la ruta si es necesario
import { CommonModule } from '@angular/common';  // Asegúrate de importar CommonModule
import { Film } from '../film.model';

@Component({
  selector: 'app-actor-list',  // Este es el nombre del componente en HTML
  templateUrl: './film-list.component.html',
  styleUrls: ['./film-list.component.css'],
  standalone: true,  // Marca el componente como independiente
  imports: [CommonModule]  // Importa CommonModule para usar directivas comunes (como *ngFor)
})
export class FilmListComponent implements OnInit {
  films: Film[] = [];

  constructor(private filmService: FilmService) { }

  ngOnInit(): void {
    this.filmService.getActors().subscribe(data => {
      this.films = data;
    });
  }
}