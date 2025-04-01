import { Actor } from "../../actors/actor.model";

export interface ActorPage {
  content: Actor[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}