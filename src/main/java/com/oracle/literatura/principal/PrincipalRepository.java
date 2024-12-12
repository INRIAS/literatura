package com.oracle.literatura.principal;

import java.util.Optional;
import java.util.Scanner;

import com.oracle.literatura.model.DataLibros;
import com.oracle.literatura.model.Datos;
import com.oracle.literatura.service.ConsumoApi;
import com.oracle.literatura.service.ConvertirDatos;

public class PrincipalRepository {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvertirDatos conversor = new ConvertirDatos();

    public void muestraLibreria() {
        int salir = -1;

        // var json = consumoApi.obtenerDatos(URL_BASE);
        // var datos = conversor.obtenerDatos(json, Datos.class);

        while (salir != 0) {
            String menu = """
                    1- Buscar libro por titulo.
                    2- Listar Libros registrados.
                    3- Listar Autores registrados.
                    4- Listar Autores vivos en fecha determinada.
                    5- Listar Libros por idioma.
                    """;

            System.out.println(menu);
            System.out.println("");
            System.out.println("Ingrese el valor de la consulta");
            var consulta = teclado.nextInt();

            switch (consulta) {
                case 1:
                    /* Ingrese el tituo del libro que desea leer: */
                    System.out.println("Ingrese el titulo del libro de desea leer: ");
                    var pedazoTitulo = teclado.nextLine();
                    var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + pedazoTitulo.replace(" ", "+"));
                    var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

                    Optional<DataLibros> libroBuscado = datosBusqueda.resultado().stream()
                            .filter(l -> l.titulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
                            .findFirst();

                    if (libroBuscado.isPresent()) {
                        System.out.println("-------LIBRO-------");
                        System.out.println(libroBuscado.get().titulo());
                        System.out.println(libroBuscado.get().autor().get(0).autores());
                        System.out.println(libroBuscado.get().idioma());
                        System.out.println(libroBuscado.get().descargas());
                    } else {
                        System.out.println("Libro no encontrado");
                    }

                    break;

                default:
                    break;
            }

        }
    }
}
