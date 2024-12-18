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

    String menu = """
            1- Buscar libro por titulo.
            2- Listar Libros registrados.
            3- Listar Autores registrados.
            4- Listar Autores vivos en fecha determinada.
            5- Listar Libros por idioma.
            0- Salir
            """;
    String menuIdiomas = """
            1.Español
            2.Ingles
            3.Frances
            4.Portugues
            """;

    public void muestraLibreria() {
        Integer salir = -1;

        // var json = consumoApi.obtenerDatos(URL_BASE);
        // var datos = conversor.obtenerDatos(json, Datos.class);
        while (salir != 0) {
            System.out.println("");
            System.out.println(menu);
            System.out.println("");
            System.out.println("Ingrese el valor de la consulta: ");
            Integer consulta = teclado.nextInt();
            teclado.nextLine();

            switch (consulta) {
                case 1 -> buscarLibrotitulo();
                case 2 -> mostrarLibrosEnRegistroFeatAutor();
                case 3 -> mostrarLibrosEnRegistroPorAutor();
                case 4 -> mostrarLibrosEnRegistroPorFechaAutor();
                case 5 -> mostrarLibrosPorIdioma();
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
        System.out.println("Ingrese el título del libro que desea leer: ");
        String pedazoTitulo = teclado.nextLine();
    
        // Verificar si el libro ya existe en la base de datos
        Optional<Libro> libroExistente = libroRepo.findByTitulo(pedazoTitulo);
    
        if (libroExistente.isPresent()) {
            System.out.println("El libro ya se encuentra registrado en el sistema.");
            Libro libro = libroExistente.get();
            System.out.println("");
            System.out.println("---------------Libro------------------");
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor(es): " + libro.getAutor().get(0).getAutores());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Número de descargas: " + libro.getDescargas());
            return;
        }
    
        // Si no existe, consultar desde la API
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + pedazoTitulo.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, DataDatos.class);
    
        if (datosBusqueda.resultado().isEmpty()) {
            System.out.println("No se encontró el libro");
            return;
        }
    
        Libro libro = new Libro(datosBusqueda.resultado().get(0));
    
        // Procesar autores dentro del libro
        libro.getAutor().forEach(dataAutor -> {
            Autor autor = new Autor();
            autor.setAutores(dataAutor.getAutores());
            autor.setFechaNacimiento(dataAutor.getFechaNacimiento()); 
            autor.setFechaFallecimiento(dataAutor.getFechaFallecimiento());
        });
    
        libroRepo.save(libro);
    
        System.out.println("");
        System.out.println("Libro almacenado en el sistema:");
        System.out.println("---------------------------------");
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Autor(es): " + libro.getAutor().get(0).getAutores());
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
            for (Integer i = 0; i < libroSis.size(); i++) {
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
            for (Integer i = 0; i < autoreSis.size(); i++) {
                Autor autor = autoreSis.get(i);
                System.out.println("");
                System.out.println("-----------Autores---------");
                System.out.println("Autor: " + autor.getAutores());
                System.out.println("Fecha de Nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de Fallecimiento: " + autor.getFechaFallecimiento());
            }
        }

    }

    // @SuppressWarnings("null")
    public void mostrarLibrosEnRegistroPorFechaAutor() {
        System.out.println("Ingrese la fecha a consultar: ");
        Integer fecha = teclado.nextInt();

        try {
            autoreSisDto = autorService.mostrarLibrosEnRegistroPorFechaAutor(fecha);

            if (autoreSisDto.isEmpty()) {
                System.out.println("No se encontraron autores vivos en la fecha indicada.");
            } else {
                autoreSisDto.forEach(a -> {
                    System.out.println("-----Autor en Fecha-----");
                    System.out.println("Autor: " + a.getAutores());
                    System.out.println("Fecha de Nacimiento: " + (a.getFechaNacimiento() != null ? a.getFechaNacimiento() : null));
                    System.out.println("Fecha de Fallecimiento: " + (a.getFechaFallecimiento() != null ? a.getFechaFallecimiento() : null));
                    System.out.println("");
                });
            }
        } catch (Exception e) {
            System.out.println("Autor no encontrado en fecha indicada." + e.getMessage());
        }

    }

    public void mostrarLibrosPorIdioma() {
        System.out.println("Ingrese el # idioma a consultar: ");
        System.out.println(menuIdiomas);
        int valorIdioma = teclado.nextInt();
        String idioma = "";

        switch (valorIdioma) {
            case 1:
                idioma = "[es]";
                break;
            case 2:
                idioma = "[en]";
                break;
            case 3:
                idioma = "[fr]";
                break;
            case 4:
                idioma = "[pt]";
                break;

            default:System.out.println("Opción no valida.");
                break;
        }

        try {
            libroSis = libroService.buscarLibrosPorIdioma(idioma);
            libroSis.forEach(l -> {
                System.out.println("");
                System.out.println("---------------Libro------------------");
                System.out.println("Título: " + l.getTitulo());
                System.out.println("Autor(es): " + l.getAutor().get(0).getAutores());
                System.out.println("Idioma: " + l.getIdioma());
                System.out.println("Número de descargas: " + l.getDescargas());
            });
        } catch (Exception e) {
            System.out.println("Libro con idioma indicado no se encuentra en sistema." + e.getMessage());
        }

    }
}
