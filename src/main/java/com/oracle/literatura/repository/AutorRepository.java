package com.oracle.literatura.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.oracle.literatura.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a")
    List<Autor> mostrarLibrosEnRegistroPorAutor();

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :autorFecha")
    List<Autor> mostrarLibrosEnRegistroPorFechaAutor(Integer autorFecha);

/*     @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :fecha AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento > :fecha)")
    List<Autor> mostrarLibrosEnRegistroPorFechaAutor(Integer fecha); */
} 
