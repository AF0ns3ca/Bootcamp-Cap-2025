// import { CommonModule } from '@angular/common';
// import { Component } from '@angular/core';
// import { RouterOutlet } from '@angular/router';

// import { NavbarComponent } from "./layout/navbar/navbar.component";
// import { FooterComponent } from "./layout/footer/footer.component";
// import { ActorListComponent } from './actors/actors.component';


// @Component({
//   selector: 'app-root',
//   imports: [CommonModule, RouterOutlet, NavbarComponent, FooterComponent, ActorListComponent],
//   templateUrl: './app.component.html',
//   styleUrl: './app.component.css'
// })
// export class AppComponent {
// }

import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ActorListComponent } from './actors/actor-list/actor-list.component';  // Importa el componente

import { NavbarComponent } from "./layout/navbar/navbar.component";
import { FooterComponent } from "./layout/footer/footer.component";
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-root',
  imports: [CommonModule, RouterOutlet, NavbarComponent, FooterComponent, ActorListComponent],
  standalone: true,  // Marca el componente como independiente
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})


export class AppComponent {
  constructor(private http: HttpClient) {}
  title = 'Movie Catalog';
}



