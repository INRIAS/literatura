package com.oracle.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DataAutor(
        @JsonAlias("name") String autores,
        @JsonAlias("birth_year") Integer fechaNacimiento,
        @JsonAlias("death_year") Integer fechaFallecimiento) {
}
