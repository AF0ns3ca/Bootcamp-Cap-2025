import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FilmService } from '../film.service';
import { Film } from '../film.model';

@Component({
  selector: 'app-actor-create',
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './film-create.component.html',
  styleUrl: './film-create.component.css',
})
export class FilmFormComponent implements OnInit {
  filmForm!: FormGroup;
  isEditMode: boolean = false;
  filmId!: number;
  languages: any[] = [];  
  

  constructor(
    private fb: FormBuilder,
    private filmService: FilmService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.filmId = +this.route.snapshot.paramMap.get('id')!;
    this.isEditMode = this.filmId ? true : false;


    this.loadLanguages();


    this.filmForm = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      releaseYear: [''],
      rentalDuration: ['', Validators.required],
      rentalRate: ['', Validators.required],
      replacementCost: ['', Validators.required],
      length: [''],
      rating: [''],
      languageId: ['', Validators.required],
      languageVO: [''],
    });
    

 
    if (this.isEditMode) {
      this.loadFilmData();
    }
  }


  loadLanguages(): void {
    this.filmService.getLanguages().subscribe(
      (data) => {
        console.log('Idiomas cargados:', data);  
        this.languages = data;  
      },
      (error) => {
        console.error('Error al cargar los idiomas:', error);
      }
    );
  }

  loadFilmData(): void {
    this.filmService.getFilmDetails(this.filmId).subscribe(
      (film: Film) => {
        this.filmForm.patchValue(film);
      },
      (error) => {
        console.error('Error al cargar los datos del film:', error);
      }
    );
  }

  onSubmit(): void {
    if (this.filmForm.valid) {
      const filmData: Film = this.filmForm.value;
  

      console.log('Datos que se van a enviar:', filmData);
  
   
      if (this.isEditMode) {
        filmData.filmId = this.filmId;
        this.filmService.updateFilm(this.filmId, filmData).subscribe(
          (response) => {
            console.log('Film actualizado exitosamente:', response);
            alert('Film actualizado con éxito.');
            this.router.navigate(['/films']);
          },
          (error) => {
            console.error('Error al actualizar el film:', error);
          }
        );
      } else {
  
        this.filmService.addFilm(filmData).subscribe(
          (response) => {
            console.log('Film creado exitosamente:', response);
            alert('Film creado con éxito.');
            this.router.navigate(['/films']);
          },
          (error) => {
            console.error('Error al agregar film:', error);
          }
        );
      }
    }
  }
  
}

