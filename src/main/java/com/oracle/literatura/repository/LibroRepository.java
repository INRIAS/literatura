package com.oracle.literatura.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oracle.literatura.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    Optional<Libro> findByTitulo(String titulo);

    @Query("SELECT l FROM Libro l JOIN FETCH l.autor")
    List<Libro> mostrarLibrosEnRegistroFeatAutor();

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    List<Libro> buscarLibrosPorIdioma(String idioma);

}
