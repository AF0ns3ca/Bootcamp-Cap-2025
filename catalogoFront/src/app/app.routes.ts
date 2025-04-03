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
import { CategoryFormComponent } from './categories/category-create/category-create.component';
import { LanguagesListComponent } from './languages/languages-list/languages-list.component';
import { LanguagesFormComponent } from './languages/languages-create/languages-create.component';
import { LanguagesDetailComponent } from './languages/languages-detail/languages-detail.component';

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
    {path: 'create', component: FilmFormComponent},
    {path: ':id', component: FilmDetailComponent},
    {path: 'edit/:id', component: FilmFormComponent },
  ]},

  {path: 'category', children: [
    {path: '', component: CategoriesListComponent},
    {path: 'create', component: CategoryFormComponent},
    {path: ':id', component: CategoryDetailComponent},
    {path: 'edit/:id', component: CategoryFormComponent },
  ]},

  {path: 'language', children: [
    {path: '', component: LanguagesListComponent},
    {path: 'create', component: LanguagesFormComponent},
    {path: ':id', component: LanguagesDetailComponent},
    {path: 'edit/:id', component: LanguagesFormComponent },
  ]},

  
  // { path: 'categories', component: CategoriesListComponent},
  // { path: 'category/create', component: CategoryFormComponent},
  // { path: 'category/:id', component: CategoryDetailComponent },
  // { path: 'category/edit/:id', component: CategoryFormComponent },
  // { path: 'actors', component: ActorListComponent},
  // { path: 'films', component: FilmListComponent},
  // { path: 'actors/create', component: ActorCreateComponent},
  // { path: 'films/:id', component: FilmDetailComponent},
  // { path: 'actors/:id', component: ActorDetailComponent},
  

];
