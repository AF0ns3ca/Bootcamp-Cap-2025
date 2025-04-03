import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { ActorService } from '../actor.service';
import { Actor } from '../actor.model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router'; 
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-actor-create',
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './actor-create.component.html',
  styleUrl: './actor-create.component.css'
})
export class ActorFormComponent implements OnInit {
  actorForm!: FormGroup; 
  actorId!: number; 
  isEditMode: boolean = false; 

  constructor(
    private fb: FormBuilder,
    private actorService: ActorService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.actorId = +this.route.snapshot.paramMap.get('id')!;  
    this.isEditMode = this.actorId ? true : false;  

    this.actorForm = this.fb.group({
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required]
    });

    if (this.isEditMode) {
      this.loadActorData();
    }
  }


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
  

      if (this.isEditMode) {

        actorData.id = this.actorId;  
  
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