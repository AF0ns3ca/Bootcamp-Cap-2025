package com.data.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;


@SpringBootApplication
public class DataApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DataApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Aplicacion Iniciada");
	}

}
