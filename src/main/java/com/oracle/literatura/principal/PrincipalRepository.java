package com.oracle.literatura.principal;

import java.util.Optional;
import java.util.Scanner;

import com.oracle.literatura.model.DataLibros;
import com.oracle.literatura.model.DataDatos;
import com.oracle.literatura.service.ConsumoApi;
import com.oracle.literatura.service.ConvertirDatos;

public class PrincipalRepository {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvertirDatos conversor = new ConvertirDatos();

    public void muestraLibreria() {
        int salir = -1;
        String menu = """
                1- Buscar libro por titulo.
                2- Listar Libros registrados.
                3- Listar Autores registrados.
                4- Listar Autores vivos en fecha determinada.
                5- Listar Libros por idioma.
                0- Salir
                """;

        var json = consumoApi.obtenerDatos(URL_BASE);
        // var datos = conversor.obtenerDatos(json, Datos.class);
        while (salir != 0) {

            System.out.println(menu);
            System.out.println("");
            System.out.println("Ingrese el valor de la consulta: ");
            int consulta = teclado.nextInt();
            teclado.nextLine();

            switch (consulta) {
                case 1 -> {
                    /* Ingrese el titulo del libro que desea leer: */
                    System.out.println("Ingrese el titulo del libro de desea leer: ");
                    String pedazoTitulo = teclado.nextLine();

                    json = consumoApi.obtenerDatos(URL_BASE + "?search=" + pedazoTitulo.replace(" ", "+"));
                    var datosBusqueda = conversor.obtenerDatos(json, DataDatos.class);

                    Optional<DataLibros> libroBuscado = datosBusqueda.resultado().stream()
                            .filter(l -> l.titulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
                            .findFirst();

                    if (libroBuscado.isPresent()) {
                        System.out.println("");
                        System.out.println("-------LIBRO-------");
                        System.out.println(libroBuscado.get().titulo());
                        System.out.println(libroBuscado.get().autor().get(0).autores());
                        System.out.println(libroBuscado.get().idioma());
                        System.out.println(libroBuscado.get().descargas());
                        System.out.println("");
                    } else {
                        System.out.println("Libro no encontrado");
                    }
                    break;
                }
                case 0 -> {
                    salir=0;
                    break;
                }

                default -> {
                    System.out.println("Opcion no disponible en sistema");
                    return;
                }
            }
        }
    }
}
