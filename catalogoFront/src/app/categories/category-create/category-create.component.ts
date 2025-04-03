import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CategoryService } from '../category.service';
import { Category } from '../category.model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';  // Importa Router
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-category-create',
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './category-create.component.html',
  styleUrl: './category-create.component.css'
})
export class CategoryFormComponent implements OnInit {

  categoryForm!: FormGroup; 
  categoryId!: number;  // Guardamos el ID del category si estamos editando uno
  isEditMode: boolean = false;  // Verifica si estamos en el modo de edición

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.categoryId = +this.route.snapshot.paramMap.get('id')!;  // Obtén el ID de los parámetros de la ruta
    this.isEditMode = this.categoryId ? true : false;  // Si hay un ID, estamos en modo edición

    this.categoryForm = this.fb.group({
      categoria: ['', Validators.required],
    });

    if (this.isEditMode) {
      this.loadCategoryData();
    }
  }

  // Cargar los datos del category a editar
  loadCategoryData(): void {
      this.categoryService.getCategoryById(this.categoryId).subscribe(
        (category: Category) => {
          this.categoryForm.patchValue({
            categoria: category.categoria,
          });
        },
        (error) => {
          console.error('Error al cargar los datos del actor:', error);
        }
      );
    }

  onSubmit(): void {
    if (this.categoryForm.valid) {
      const categoryData: Category = this.categoryForm.value;
  
      // Si estamos en modo edición, no agregues el id al categoryData (si no es necesario)
      if (this.isEditMode) {
        // Asegúrate de que el id en el cuerpo de la solicitud coincida con el id de la URL
        categoryData.id = this.categoryId;  // Agregar el id explícitamente si es necesario
  
        // Si estamos editando, actualizamos el category
        this.categoryService.updateCategory(this.categoryId, categoryData).subscribe(
          (response) => {
            console.log('category actualizado exitosamente:', response);
            alert('category actualizado con éxito.');
            this.router.navigate(['/category']);
          },
          (error) => {
            console.error('Error al actualizar el category:', error);
          }
        );
      } else {
        // Si estamos creando un category, no necesitamos el id en el cuerpo
        this.categoryService.addCategory(categoryData).subscribe(
          (response) => {
            console.log('category creado exitosamente:', response);
            alert('category creado con éxito.');
            this.router.navigate(['/category']);
          },
          (error) => {
            console.error('Error al agregar category:', error);
          }
        );
      }
    }
  }
  
}  
