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

  actorId: number | null = null;
  actor: any;
  peliculas: Film[] = [];

  constructor(
    private route: ActivatedRoute, 
    private actorService: ActorService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.actorId = +params['id']; 
      console.log(`Actor ID desde URL: ${this.actorId}`);
      
      if (this.actorId !== null) {
        this.loadActorDetails();
        this.getActorMovies(); 
      } else {
        console.error('Actor ID es nulo o inválido');
      }
    });
  }

  loadActorDetails(): void {
    if (this.actorId !== null) {
      this.actorService.getActorDetails(this.actorId).subscribe(data => {
        this.actor = data;
        console.log('Detalles del actor:', this.actor); 
      });
    }
  }

  getActorMovies(): void {
    if (this.actorId !== null) {

      const actorIdAsString = this.actorId.toString(); 
      

      this.actorService.getActorMovies(actorIdAsString).subscribe(
        (films: any) => {
          this.peliculas = films; 
        },
        (error) => {
          console.error('Error al obtener las películas del actor:', error);
        }
      );
    }

  }
  

}


