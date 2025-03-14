package com.example.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

import com.example.domains.core.entities.AbstractEntity;


/**
 * The persistent class for the actor database table.
 * 
 */
@Entity
@Table(name="actor")
@NamedQuery(name="Actor.findAll", query="SELECT a FROM Actor a")
public class Actor extends AbstractEntity<Actor> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="actor_id", unique=true, nullable=false)
	private int actorId;

	@Column(name="first_name", nullable=false, length=45)
	//Para que no pueda estar en blanco
	@NotBlank
	//Para que tenga un tamaño minimo y maximo
	@Size(min = 2, max = 45)
	//Para que se definan los caracteres validos que puede tener
	//En este caso solo letras
	@Pattern(regexp = "^[A-Z]*$", message = "El nombre debe estar en mayusculas, sin espacios y sin caracteres especiales")
	private String firstName;

	
	@Column(name="last_name", nullable=false, length=45)
	//Para que no pueda estar en blanco
	@NotBlank
	//Para que tenga un tamaño minimo y maximo
	@Size(min = 2, max = 45)
	private String lastName;

	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	//Para que la fecha de actualizacion no pueda ser futura y deba ser una fecha
	@PastOrPresent
	private Timestamp lastUpdate;

	//bi-directional many-to-one association to FilmActor
	//FetchType.EAGER para que traiga los datos de la tabla relacionada
	//FetchType.LAZY para que no traiga los datos de la tabla relacionada
	//FetchType.LAZY es el valor por defecto
	@OneToMany(mappedBy="actor")
	private List<FilmActor> filmActors;

	public Actor() {
	}

	public Actor(int actorId, String firstName, String lastName) {
		super();
		this.actorId = actorId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	

	public int getActorId() {
		return this.actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) throws IllegalArgumentException {	
		if (this.getFirstName() == null) {
			throw new IllegalArgumentException("El nombre no puede ser nulo");
			
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<FilmActor> getFilmActors() {
		return this.filmActors;
	}

	public void setFilmActors(List<FilmActor> filmActors) {
		this.filmActors = filmActors;
	}

	public FilmActor addFilmActor(FilmActor filmActor) {
		getFilmActors().add(filmActor);
		filmActor.setActor(this);

		return filmActor;
	}

	public FilmActor removeFilmActor(FilmActor filmActor) {
		getFilmActors().remove(filmActor);
		filmActor.setActor(null);

		return filmActor;
	}


	//Para comprobar la primary key, hashCode y equals
	@Override
	public int hashCode() {
		return Objects.hash(actorId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		return actorId == other.actorId;
	}

	@Override
	public String toString() {
		return "Actor [actorId=" + actorId + ", firstName=" + firstName + ", lastName=" + lastName + ", lastUpdate="
				+ lastUpdate + "]";
	}

	public void jubilate() {
		//ponemos active a false
		//ponemos fecha de baja a la fecha actual
	}

	public void premioRecibido() {
		//...
	}

}