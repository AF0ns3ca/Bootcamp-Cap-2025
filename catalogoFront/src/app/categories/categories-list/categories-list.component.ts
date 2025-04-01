import { Component, OnInit } from '@angular/core';
import { CategoryService } from '../category.service';  // Ajusta la ruta si es necesario
import { CommonModule } from '@angular/common';  // Asegúrate de importar CommonModule
import { Category } from '../category.model';

@Component({
  selector: 'app-categories-list',  // Este es el nombre del componente en HTML
  templateUrl: './categories-list.component.html',
  styleUrls: ['./categories-list.component.css'],
  standalone: true,  // Marca el componente como independiente
  imports: [CommonModule]  // Importa CommonModule para usar directivas comunes (como *ngFor)
})
export class CategoriesListComponent implements OnInit {

  categories: Category[] = [];
  
    constructor(private categoryService: CategoryService) { }
  
    ngOnInit(): void {
      this.categoryService.getCategories().subscribe(data => {
        this.categories = data;
      });
    }

}
