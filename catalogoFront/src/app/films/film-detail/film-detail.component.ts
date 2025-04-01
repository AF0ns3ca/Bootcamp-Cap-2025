import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';  // Importamos ActivatedRoute para acceder a los parámetros de la ruta
import { FilmService } from '../film.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-film-detail',
  templateUrl: './film-detail.component.html',
  styleUrls: ['./film-detail.component.css'],
  standalone: true,
  imports: [CommonModule]  // Importamos CommonModule para usar ngIf, ngFor, etc.
})
export class FilmDetailComponent implements OnInit {

  filmId: number | null = null;  // ID de la película que recibimos desde la URL
  film: any;

  constructor(
    private route: ActivatedRoute,  // Activamos la ruta
    private filmService: FilmService
  ) {}

  ngOnInit(): void {
    // Obtenemos el parámetro 'id' desde la URL
    this.route.params.subscribe(params => {
      this.filmId = +params['id'];  // Convertimos el parámetro 'id' a número
      if (this.filmId !== null) {
        this.loadMovieDetails();
      }
    });
  }

  loadMovieDetails(): void {
    if (this.filmId !== null) {
      this.filmService.getFilmDetails(this.filmId).subscribe(data => {
        this.film = data;
        console.log('Detalles de la película:', this.film);  // Esto te ayudará a verificar los datos
      });
    }
  }
  
}
