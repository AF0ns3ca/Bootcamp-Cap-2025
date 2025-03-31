// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ActorListComponent } from './actors/actor-list/actor-list.component';
import { FilmListComponent } from './films/film-list/film-list.component';
// import { ActorListComponent } from './actors/actor-list/actor-list.component';

export const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'actors', component: ActorListComponent},
  { path: 'films', component: FilmListComponent},
];
