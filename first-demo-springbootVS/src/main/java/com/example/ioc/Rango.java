package com.example.ioc;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Data para clase mutable, que se puedna añadir mas propiedades, Value para clase inmutable
@Data 
@Component 
//Busca en el archivo de configuración uno que coincida con el nombre que le hemos puesto
@ConfigurationProperties("rango")
public class Rango {
	
	private int min;
	private int max;

}
