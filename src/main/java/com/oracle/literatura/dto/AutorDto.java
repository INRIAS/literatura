package com.oracle.literatura.dto;

public record AutorDto(
                String autores,
                Integer fechaNacimiento,
                Integer fechaFallecimiento) {

        public AutorDto(String autores, Integer fechaNacimiento, Integer fechaFallecimiento) {
                this.autores = autores;
                this.fechaNacimiento = fechaNacimiento;
                this.fechaFallecimiento = fechaFallecimiento;
        }

        public String getAutores() {
                return autores;
        }

        public Integer getFechaNacimiento() {
                return fechaNacimiento;
        }

        public Integer getFechaFallecimiento() {
                return fechaFallecimiento;
        }

}
