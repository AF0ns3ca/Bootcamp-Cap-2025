package com.example.ioc;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


//@Repository
//@Qualifier("mentira")
public class RepositorioMock implements Repositorio {
	
	
	@Override
	public void guardar() {
		System.err.println("Guardando de mentira");
	}

}
