package com.example.domains.entities.models;

import com.example.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name="Actor", description = "Representa un actor")
@Data @AllArgsConstructor
public class ActorDTO {
	@JsonProperty("id")
	private int actorId;
	@JsonProperty("nombre")
	@Schema(description = "Nombre del actor", example = "PEPITO", required = true, minLength = 2, maxLength = 45)
	private String firstName;
	@JsonProperty("apellidos")
	@NotBlank
	@Size(min = 2, max = 45)
	@Schema(description = "Apellidos del actor", example = "GTILLO")
	private String lastName;

	//De donde saca sus datos para crear un ActorDTO
	public static ActorDTO from(Actor source) {
		return new ActorDTO(
				source.getActorId(), 
				source.getFirstName(), 
				source.getLastName()
				);
	}

	//Para pasar de actorDTO a actor
	public static Actor from(ActorDTO source) {
		return new Actor(
				source.getActorId(), 
				source.getFirstName(), 
				source.getLastName()
				);
	}
}
