import { Component, OnInit } from '@angular/core';
import { ActorService } from '../actor.service';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Film } from 'src/app/films/film.model';

@Component({
  selector: 'app-actor-detail',
  imports: [CommonModule, RouterModule],
  templateUrl: './actor-detail.component.html',
  styleUrl: './actor-detail.component.css'
})
export class ActorDetailComponent implements OnInit {
  // Aquí puedes definir las propiedades y métodos que necesites para el detalle del actor
  actorId: number | null = null;  // ID del actor que recibimos desde la URL
  actor: any;  // Cambia 'any' por el tipo de datos que esperas recibir
  peliculas: Film[] = [];

  constructor(
    private route: ActivatedRoute,  // Activamos la ruta
    private actorService: ActorService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.actorId = +params['id'];  // Convertimos el parámetro 'id' a número
      console.log(`Actor ID desde URL: ${this.actorId}`);  // Log para verificar el valor de actorId
      
      if (this.actorId !== null) {
        this.loadActorDetails();
        this.getActorMovies();  // Llamada al método para obtener las películas
      } else {
        console.error('Actor ID es nulo o inválido');
      }
    });
  }

  loadActorDetails(): void {
    if (this.actorId !== null) {
      this.actorService.getActorDetails(this.actorId).subscribe(data => {
        this.actor = data;
        console.log('Detalles del actor:', this.actor);  // Esto te ayudará a verificar los datos
      });
    }
  }

  getActorMovies(): void {
    if (this.actorId !== null) {
      // Convierte actorId de number a string
      const actorIdAsString = this.actorId.toString();  // o `${this.actorId}`
      
      // Ahora puedes pasar actorIdAsString al método del servicio que espera un string
      this.actorService.getActorMovies(actorIdAsString).subscribe(
        (films: any) => {
          this.peliculas = films;  // Aquí se guardan las películas del actor
        },
        (error) => {
          console.error('Error al obtener las películas del actor:', error);
        }
      );
    }

  }
  

}


