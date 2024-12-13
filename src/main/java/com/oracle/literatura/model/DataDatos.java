package com.oracle.literatura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataDatos(
    @JsonAlias("results") List<DataLibros> resultado
) {


}