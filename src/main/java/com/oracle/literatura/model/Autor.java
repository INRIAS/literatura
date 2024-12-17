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
    private int fechaNacimiento;
    private int fechaFallecimiento;
    @ManyToOne(fetch = FetchType.EAGER)
    private Libro libro;

    public Autor() {
    }

    public Autor(String autores, int fechaNacimiento, int fechaFallecimiento) {
        this.autores = autores;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;
    }

 
    public Autor(DataAutor dataAutor) {
        this.autores = dataAutor.autores();
        // Manejando null con Integer
        if (dataAutor.fechaNacimiento() != null) {
            this.fechaNacimiento = Integer.valueOf(dataAutor.fechaNacimiento());
        } else {
            this.fechaNacimiento = 0;  // Usar null si no hay valor
        }

        if (dataAutor.fechaFallecimiento() != null) {
            this.fechaFallecimiento = Integer.valueOf(dataAutor.fechaFallecimiento());
        } else {
            this.fechaFallecimiento = 0;  // Usar null si no hay valor
        }
    }

    public String getAutores() {
        return autores;
    }

    public void setAutores(String autores) {
        this.autores = autores;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(int fechaFallecimiento) {
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
