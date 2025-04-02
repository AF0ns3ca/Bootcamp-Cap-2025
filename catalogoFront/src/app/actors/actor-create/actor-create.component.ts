import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { ActorService } from '../actor.service';
import { Actor } from '../actor.model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';  // Importa Router
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-actor-create',
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './actor-create.component.html',
  styleUrl: './actor-create.component.css'
})
export class ActorFormComponent implements OnInit {
  actorForm!: FormGroup; 
  actorId!: number;  // Guardamos el ID del actor si estamos editando uno
  isEditMode: boolean = false;  // Verifica si estamos en el modo de edición

  constructor(
    private fb: FormBuilder,
    private actorService: ActorService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.actorId = +this.route.snapshot.paramMap.get('id')!;  // Obtén el ID de los parámetros de la ruta
    this.isEditMode = this.actorId ? true : false;  // Si hay un ID, estamos en modo edición

    this.actorForm = this.fb.group({
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required]
    });

    if (this.isEditMode) {
      this.loadActorData();
    }
  }

  // Cargar los datos del actor a editar
  loadActorData(): void {
    this.actorService.getActorDetails(this.actorId).subscribe(
      (actor: Actor) => {
        this.actorForm.patchValue({
          nombre: actor.nombre,
          apellidos: actor.apellidos
        });
      },
      (error) => {
        console.error('Error al cargar los datos del actor:', error);
      }
    );
  }

  onSubmit(): void {
    if (this.actorForm.valid) {
      const actorData: Actor = this.actorForm.value;
  
      // Si estamos en modo edición, no agregues el id al actorData (si no es necesario)
      if (this.isEditMode) {
        // Asegúrate de que el id en el cuerpo de la solicitud coincida con el id de la URL
        actorData.id = this.actorId;  // Agregar el id explícitamente si es necesario
  
        // Si estamos editando, actualizamos el actor
        this.actorService.updateActor(this.actorId, actorData).subscribe(
          (response) => {
            console.log('Actor actualizado exitosamente:', response);
            alert('Actor actualizado con éxito.');
            this.router.navigate(['/actors']);
          },
          (error) => {
            console.error('Error al actualizar el actor:', error);
          }
        );
      } else {
        // Si estamos creando un actor, no necesitamos el id en el cuerpo
        this.actorService.addActor(actorData).subscribe(
          (response) => {
            console.log('Actor creado exitosamente:', response);
            alert('Actor creado con éxito.');
            this.router.navigate(['/actors']);
          },
          (error) => {
            console.error('Error al agregar actor:', error);
          }
        );
      }
    }
  }
  
}  