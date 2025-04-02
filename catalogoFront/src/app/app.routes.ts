// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { ActorListComponent } from './actors/actor-list/actor-list.component';
import { FilmListComponent } from './films/film-list/film-list.component';
import { CategoriesListComponent } from './categories/categories-list/categories-list.component';
import { FilmDetailComponent } from './films/film-detail/film-detail.component';
import { ActorDetailComponent } from './actors/actor-detail/actor-detail.component';
import { CategoryDetailComponent } from './categories/category-detail/category-detail.component';
import { ActorFormComponent } from './actors/actor-create/actor-create.component';
import { FilmFormComponent } from './films/film-create/film-create.component';

export const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'home', component: HomeComponent },
  {path: 'actors', children: [
    {path: '', component: ActorListComponent},
    {path: 'create', component: ActorFormComponent},
    {path: ':id', component: ActorDetailComponent},
    { path: 'edit/:id', component: ActorFormComponent },

  ]},
  {path: 'films', children: [
    {path: '', component: FilmListComponent},
    {path: ':id', component: FilmDetailComponent},
    {path: 'create', component: FilmFormComponent},
    {path: 'edit/:id', component: FilmFormComponent },
  ]},
  { path: 'categories', component: CategoriesListComponent},
  { path: 'category/:id', component: CategoryDetailComponent },
  // { path: 'actors', component: ActorListComponent},
  // { path: 'films', component: FilmListComponent},
  // { path: 'actors/create', component: ActorCreateComponent},
  // { path: 'films/:id', component: FilmDetailComponent},
  // { path: 'actors/:id', component: ActorDetailComponent},
  

];
