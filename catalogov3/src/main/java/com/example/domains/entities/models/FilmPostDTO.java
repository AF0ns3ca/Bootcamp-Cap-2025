package com.example.domains.entities.models;

import java.math.BigDecimal;
import java.util.List;

import com.example.domains.entities.Film;
import com.example.domains.entities.Language;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Value;

//@Schema(name = "Pelicula (Detalles)", description = "Version completa de las peliculas")
@Value
@Data @AllArgsConstructor
public class FilmPostDTO {

   @JsonProperty("id")
    private int filmId;

    @NotBlank
    @Size(max = 128)
    private String title;
    
    // bi-directional many-to-one association to Language
	@ManyToOne
	@JoinColumn(name = "language_id")
	@NotNull
	@JsonManagedReference
	private Language language;
    
    @NotNull
    private byte rentalDuration;

    @NotNull
    @Digits(integer = 2, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal rentalRate;

    @NotNull
    @Digits(integer = 3, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal replacementCost;

    public static FilmPostDTO from(Film film) {
        return new FilmPostDTO(
            film.getFilmId(),
            film.getTitle(),
            film.getLanguage(), // Asumiendo que 'Language' tiene un método 'getLanguageId'
            film.getRentalDuration(),
            film.getRentalRate(),
            film.getReplacementCost()
        );
    }

    public static Film from(FilmPostDTO dto) {
        return new Film(
            dto.getFilmId(),
            dto.getTitle(),
            dto.getLanguage(),
            dto.getRentalDuration(),
            dto.getRentalRate(),
            dto.getReplacementCost()
        );
}
}
