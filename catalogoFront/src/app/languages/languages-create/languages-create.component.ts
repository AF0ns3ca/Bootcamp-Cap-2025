import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { LanguageService } from '../language.service';
import { Language } from '../language.model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';  // Importa Router
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-languages-create',
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './languages-create.component.html',
  styleUrl: './languages-create.component.css'
})
export class LanguagesCreateComponent implements OnInit {

  languageForm!: FormGroup; 
  languageId!: number;  // Guardamos el ID del language si estamos editando uno
  isEditMode: boolean = false;  // Verifica si estamos en el modo de edición

  constructor(
    private fb: FormBuilder,
    private languageService: LanguageService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.languageId = +this.route.snapshot.paramMap.get('id')!;  // Obtén el ID de los parámetros de la ruta
    this.isEditMode = this.languageId ? true : false;  // Si hay un ID, estamos en modo edición

    this.languageForm = this.fb.group({
      categoria: ['', Validators.required],
    });

    if (this.isEditMode) {
      this.loadLanguageData();
    }
  }

  // Cargar los datos del language a editar
  loadLanguageData(): void {
      this.languageService.getLanguageById(this.languageId).subscribe(
        (language: Language) => {
          this.languageForm.patchValue({
            idioma: language.idioma,
          });
        },
        (error) => {
          console.error('Error al cargar los datos del actor:', error);
        }
      );
    }

  onSubmit(): void {
    if (this.languageForm.valid) {
      const languageData: Language = this.languageForm.value;
  
      // Si estamos en modo edición, no agregues el id al languageData (si no es necesario)
      if (this.isEditMode) {
        // Asegúrate de que el id en el cuerpo de la solicitud coincida con el id de la URL
        languageData.id = this.languageId;  // Agregar el id explícitamente si es necesario
  
        // Si estamos editando, actualizamos el language
        this.languageService.updateLanguage(this.languageId, languageData).subscribe(
          (response) => {
            console.log('language actualizado exitosamente:', response);
            alert('language actualizado con éxito.');
            this.router.navigate(['/language']);
          },
          (error) => {
            console.error('Error al actualizar el language:', error);
          }
        );
      } else {
        // Si estamos creando un language, no necesitamos el id en el cuerpo
        this.languageService.addLanguage(languageData).subscribe(
          (response) => {
            console.log('language creado exitosamente:', response);
            alert('language creado con éxito.');
            this.router.navigate(['/language']);
          },
          (error) => {
            console.error('Error al agregar language:', error);
          }
        );
      }
    }
  }
  
}  

