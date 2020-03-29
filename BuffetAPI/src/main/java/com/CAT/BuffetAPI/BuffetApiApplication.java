package com.CAT.BuffetAPI;



import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.CAT.BuffetAPI.Entities.Sesion;

@SpringBootApplication
public class BuffetApiApplication {

	public static void main(String[] args) {
		List<Sesion> lista = new ArrayList<>();
		SpringApplication.run(BuffetApiApplication.class, args);
	}

}
