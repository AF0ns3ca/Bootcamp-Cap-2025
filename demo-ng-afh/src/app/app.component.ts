import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'demo-ng-afh';

  modalVisible = false;

  // Método para abrir el modal
  openModal() {
    this.modalVisible = true;
  }

  // Método para cerrar el modal
  closeModal() {
    this.modalVisible = false;
  }
  
}
