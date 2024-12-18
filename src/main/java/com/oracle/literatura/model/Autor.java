package com.oracle.literatura.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String autores;
    private Integer fechaNacimiento;
    private Integer fechaFallecimiento;
    @ManyToOne(fetch = FetchType.EAGER)
    private Libro libro;

    public Autor() {
    }

    public Autor(String autores, Integer fechaNacimiento, Integer fechaFallecimiento) {
        this.autores = autores;
        this.fechaNacimiento = fechaNacimiento != null ? fechaNacimiento : null;
        this.fechaFallecimiento = fechaFallecimiento != null ? fechaFallecimiento : null;
    }

    public Autor(DataAutor dataAutor) {
        this.autores = dataAutor.autores();
        this.fechaNacimiento = dataAutor.fechaNacimiento() != null ? dataAutor.fechaNacimiento() : null;
        this.fechaFallecimiento = dataAutor.fechaFallecimiento() != null ? dataAutor.fechaFallecimiento() : null;
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
