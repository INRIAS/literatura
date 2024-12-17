package com.oracle.literatura.principal;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oracle.literatura.model.DataLibro;
import com.oracle.literatura.model.Libro;
import com.oracle.literatura.repository.AutorRepository;
import com.oracle.literatura.repository.LibroRepository;
import com.oracle.literatura.dto.AutorDto;
import com.oracle.literatura.model.Autor;
import com.oracle.literatura.model.DataDatos;
import com.oracle.literatura.service.AutorService;
import com.oracle.literatura.service.ConsumoApi;
import com.oracle.literatura.service.ConvertirDatos;
import com.oracle.literatura.service.LibroService;

import jakarta.transaction.Transactional;

@Component
public class PrincipalRepository {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvertirDatos conversor = new ConvertirDatos();
    private List<Libro> libroSis;
    private List<Autor> autoreSis;
    private List<AutorDto> autoreSisDto;
    @Autowired
    private LibroRepository libroRepo;
    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;
    @Autowired
    private AutorRepository autorRepo;

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

        // var json = consumoApi.obtenerDatos(URL_BASE);
        // var datos = conversor.obtenerDatos(json, Datos.class);
        while (salir != 0) {
            System.out.println("");
            System.out.println(menu);
            System.out.println("");
            System.out.println("Ingrese el valor de la consulta: ");
            int consulta = teclado.nextInt();
            teclado.nextLine();

            switch (consulta) {
                case 1 -> buscarLibrotitulo();
                case 2 -> mostrarLibrosEnRegistroFeatAutor();
                case 3 -> mostrarLibrosEnRegistroPorAutor();
                case 4 -> mostrarLibrosEnRegistroPorFechaAutor();
                case 0 -> {
                    salir = 0;
                    break;
                }

                default -> {
                    System.out.println("Opcion no disponible en sistema");
                    return;
                }
            }
        }
    }

    private void buscarLibrotitulo() {
        /* Ingrese el titulo del libro que desea leer: */
        System.out.println("Ingrese el titulo del libro de desea leer: ");
        String pedazoTitulo = teclado.nextLine();

        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + pedazoTitulo.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, DataDatos.class);

        Libro libro = new Libro(datosBusqueda.resultado().get(0));
        libroRepo.save(libro);

        System.out.println("");
        System.out.println("Libro almacenado en sistema: ");
        System.out.println("---------------------------------");
        System.out.println("Titulo: " + libro.getTitulo());
        System.out.println("Autor: " + libro.getAutor().get(0).getAutores());
        System.out.println("Idioma: " + libro.getIdioma());
        System.out.println("Descargas: " + libro.getDescargas());
        System.out.println("---------------------------------");
        System.out.println("");
    }

    // @Transactional
    private void mostrarLibrosEnRegistroFeatAutor() {
        System.out.println("-----LIBROS EN SISTEMA-----");
        libroSis = libroService.mostrarLibrosEnRegistroFeatAutor();

        if (libroSis.isEmpty()) {
            System.out.println("No se registran libros en sistema");
        } else {
            // Iterar con un for clásico
            for (int i = 0; i < libroSis.size(); i++) {
                Libro libro = libroSis.get(i);

                System.out.println("");
                System.out.println("---------------Libro------------------");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor(es): " + libro.getAutor().get(0).getAutores());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getDescargas());
            }
        }

    }

    public void mostrarLibrosEnRegistroPorAutor() {
        System.out.println("-----AUTORES EN SISTEMA-----");
        autoreSis = autorService.mostrarLibrosEnRegistroPorAutor();

        if (autoreSis.isEmpty()) {
            System.out.println("No se registran autores en sistema");
        } else {
            for (int i = 0; i < autoreSis.size(); i++) {
                Autor autor = autoreSis.get(i);
                System.out.println("");
                System.out.println("-----------Autores---------");
                System.out.println("Autor: " + autor.getAutores());
                System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de Fallecimiento: " + autor.getFechaFallecimiento());
            }
        }

    }

    public void mostrarLibrosEnRegistroPorFechaAutor() {
        System.out.println("Ingrese la fecha a consultar: ");
        int fecha = teclado.nextInt();

        try {
            autoreSis = autorRepo.mostrarLibrosEnRegistroPorFechaAutor(fecha);
            autoreSisDto.forEach(a -> {
                System.out.println("-----Autor en Fecha-----");
                System.out.println("Autor: " + a.getAutores() +
                        "\nFecha de Nacimiento: " + a.getFechaNacimiento() +
                        "\nFecha de Fallecimiento: " + a.getFechaFallecimiento());
                System.out.println("");
            });
        } catch (Exception e) {
            System.out.println("Autor no encontrado en fecha indicada." + e.getMessage());
        }

    }

}
