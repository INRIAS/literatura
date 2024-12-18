package com.oracle.literatura.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.literatura.model.Libro;
import com.oracle.literatura.repository.LibroRepository;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepo;

    public List<Libro> mostrarLibrosEnRegistroFeatAutor(){
        return libroRepo.mostrarLibrosEnRegistroFeatAutor();
    }

    public List<Libro> buscarLibrosPorIdioma(String idioma){
        return libroRepo.buscarLibrosPorIdioma(idioma);
        // return libroRepo.mostrarLibrosPorIdioma(idioma);
    }
}
