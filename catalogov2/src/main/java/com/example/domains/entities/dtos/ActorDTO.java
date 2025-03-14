package com.example.domains.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.domains.entities.Actor;

//Value no tiene setters entonces no vale para entrada y salida mientras que Data si hace los setters si wue sirve para entrada y salida
//Data solo crea el constructor sin parametros por eso usamos @allArgsConstructor y con el no args constructor recuperamoso el constructor sin parametros
@Data @AllArgsConstructor
public class ActorDTO {

    private int actorId;
	private String firstName;
	private String lastName;

    //Clase solo con datos que esta para serializarse, no para trabajar con ella.

    //Para pasar de entidad a DTO
    public static ActorDTO from(Actor source){
        return new ActorDTO(source.getActorId(), source.getFirstName(), source.getLastName());
    }

    //Para pasar de DTO a entidad
    public static Actor from(ActorDTO source){
        return new Actor(source.getActorId(), source.getFirstName(), source.getLastName());
    }

}
