package com.desafioapirest.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootApplication
public class ClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClienteApplication.class, args);

		System.out.println("****************************************");
		System.out.println("****************************************");
		System.out.println("**********PROGRAMA LEVANTADO************");
		System.out.println("****************************************");
		System.out.println("****************************************");

	}

}
