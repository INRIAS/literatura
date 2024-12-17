package com.oracle.literatura;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.oracle.literatura.principal.PrincipalRepository;
import com.oracle.literatura.repository.LibroRepository;


@SpringBootApplication
public class LiteraluraApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(LiteraluraApplication.class, args);
		PrincipalRepository principal = context.getBean(PrincipalRepository.class); // Obtén la instancia gestionada por Spring
		principal.muestraLibreria();
	}
}

/* @SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner{
	@Autowired
	private LibroRepository libroRepo;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		PrincipalRepository principal = new PrincipalRepository(libroRepo);
		principal.muestraLibreria();
	}

} */
