package com.oracle.literatura;



import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.oracle.literatura.principal.PrincipalRepository;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		PrincipalRepository principal = new PrincipalRepository();
		principal.muestraLibreria();
	}

}
