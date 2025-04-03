import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CategoryService } from '../category.service';
import { Category } from '../category.model';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';  
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-category-create',
  imports: [ReactiveFormsModule, CommonModule, RouterModule],
  templateUrl: './category-create.component.html',
  styleUrl: './category-create.component.css'
})
export class CategoryFormComponent implements OnInit {

  categoryForm!: FormGroup; 
  categoryId!: number; 
  isEditMode: boolean = false;  

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.categoryId = +this.route.snapshot.paramMap.get('id')!;  
    this.isEditMode = this.categoryId ? true : false;  

    this.categoryForm = this.fb.group({
      categoria: ['', Validators.required],
    });

    if (this.isEditMode) {
      this.loadCategoryData();
    }
  }


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
  
      
      if (this.isEditMode) {
        
        categoryData.id = this.categoryId;  
  
        
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
