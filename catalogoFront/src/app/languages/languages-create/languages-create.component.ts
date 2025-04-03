import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { LanguageService } from '../language.service';
import { Language } from '../language.model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';  
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-languages-create',
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './languages-create.component.html',
  styleUrl: './languages-create.component.css'
})
export class LanguagesFormComponent implements OnInit {

  languageForm!: FormGroup; 
  languageId!: number; 
  isEditMode: boolean = false; 

  constructor(
    private fb: FormBuilder,
    private languageService: LanguageService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.languageId = +this.route.snapshot.paramMap.get('id')!; 
    this.isEditMode = this.languageId ? true : false;  

    this.languageForm = this.fb.group({
      idioma: ['', Validators.required],
    });

    if (this.isEditMode) {
      this.loadLanguageData();
    }
  }

 
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
  
      
      if (this.isEditMode) {
 
        languageData.id = this.languageId;
  
    
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

