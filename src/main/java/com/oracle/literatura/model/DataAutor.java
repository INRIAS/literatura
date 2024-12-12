package com.oracle.literatura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataAutor(
        @JsonAlias("name") String autores,
        @JsonAlias("birth_year") String fechadeNacimiento) {
}
