import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FilmService } from '../films/film.service'; // Asegúrate de importar el servicio
import { Film } from '../films/film.model'; 
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-home',
  imports: [RouterLink, CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  standalone: true,  
  providers: [DatePipe]
})

export class HomeComponent implements OnInit {

  films: Film[] = [];
  currentDate: string = "";

  constructor(private filmService: FilmService, private datePipe: DatePipe) {}

  ngOnInit(): void {
    this.setCurrentDate();
    this.loadNovedades();  
  }

  setCurrentDate(): void {

    const today = new Date();
    this.currentDate = this.datePipe.transform(today, 'yyyy-MM-dd 00:00:00')!;
    console.log('Fecha formateada:', this.currentDate);
  }

  loadNovedades(): void {
    console.log('Cargando novedades...');
    this.filmService.getNovedades(this.currentDate).subscribe(
      (films) => {
        this.films = films;
        console.log('Películas cargadas:', this.films);
      },
      (error) => {
        console.error('Error al cargar las novedades', error);
      }
    );
  }
  
}
