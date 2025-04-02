// film-detail.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FilmService } from '../film.service';
import { Actor } from '../../actors/actor.model';  // Si tienes un modelo Actor
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-film-detail',
  templateUrl: './film-detail.component.html',
  styleUrls: ['./film-detail.component.css'],
  standalone: true,  // Marca el componente como independiente
  imports: [CommonModule, RouterLink],  // Importa los módulos necesarios aquí
})
export class FilmDetailComponent implements OnInit {
  filmId: number | null = null;
  film: any;  // Cambiar a un tipo más específico si es necesario
  actors: Actor[] = [];  // Lista de actores

  constructor(
    private route: ActivatedRoute,
    private filmService: FilmService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.filmId = +params['id'];  // Obtener el id de la película
      if (this.filmId !== null) {
        this.loadFilmDetails();
        this.loadActors();
      }
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
}
