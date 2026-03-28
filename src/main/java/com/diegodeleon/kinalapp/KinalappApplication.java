package com.diegodeleon.kinalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class KinalappApplication {

	public static void main(String[] args) {
		SpringApplication.run(KinalappApplication.class, args);

		//Generar contraseña encriptada
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("1234"));
	}
}