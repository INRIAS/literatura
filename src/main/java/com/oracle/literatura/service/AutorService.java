package com.oracle.literatura.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.literatura.dto.AutorDto;
import com.oracle.literatura.model.Autor;
import com.oracle.literatura.repository.AutorRepository;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepo;

    public List<Autor> mostrarLibrosEnRegistroPorAutor() {
        return autorRepo.mostrarLibrosEnRegistroPorAutor();
    }

    public List<AutorDto> mostrarLibrosEnRegistroPorFechaAutor(Integer autorFecha) {

        List<Autor> autores = autorRepo.mostrarLibrosEnRegistroPorFechaAutor(autorFecha);

        return autores.stream()
                .map(a -> new AutorDto(
                        a.getAutores(),
                        a.getFechaNacimiento(),
                        a.getFechaFallecimiento()))
                .collect(Collectors.toList());
    }

}
