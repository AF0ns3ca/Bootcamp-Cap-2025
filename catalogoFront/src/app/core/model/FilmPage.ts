import { Film } from "../../films/film.model";

export interface FilmPage {
  content: Film[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}