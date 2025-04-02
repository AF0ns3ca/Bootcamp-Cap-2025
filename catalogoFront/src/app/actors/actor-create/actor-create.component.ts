import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { ActorService } from '../actor.service';
import { Actor } from '../actor.model';
import { Router, RouterModule } from '@angular/router';  // Importa Router
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-actor-create',
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './actor-create.component.html',
  styleUrl: './actor-create.component.css'
})
export class ActorCreateComponent implements OnInit {
  actorForm!: FormGroup;  // Usamos el operador de aserción no nula

  constructor(
    private fb: FormBuilder,
    private actorService: ActorService,
    private router: Router  // Inyecta el Router
  ) {}

  ngOnInit(): void {
    // Inicializamos el formulario en ngOnInit
    this.actorForm = this.fb.group({
      nombre: ['', Validators.required],  // Nombre del actor
      apellidos: ['', Validators.required] // Apellidos del actor
    });
  }

  // Método para enviar el formulario
  onSubmit(): void {
    if (this.actorForm.valid) {
      const newActor: Actor = this.actorForm.value;  // Extraemos los datos del formulario

      // Llamamos al servicio para agregar el actor
      this.actorService.addActor(newActor).subscribe(
        (response) => {
          console.log('Actor agregado exitosamente:', response);
          
          // Mostrar el alert de éxito
          alert('Actor creado con éxito.');

          // Redirigir a la página de actores
          this.router.navigate(['/actors']);
        },
        (error) => {
          console.error('Error al agregar actor:', error);
        }
      );
    }
  }
}
