package com.oracle.literatura.dto;

public record AutorDto(
                String autores,
                int fechaNacimiento,
                int fechaFallecimiento) {

        public AutorDto(String autores, int fechaNacimiento, int fechaFallecimiento) {
                this.autores = autores;
                this.fechaNacimiento = fechaNacimiento;
                this.fechaFallecimiento = fechaFallecimiento;
        }

        public String getAutores() {
                return autores;
        }

        public int getFechaNacimiento() {
                return fechaNacimiento;
        }

        public int getFechaFallecimiento() {
                return fechaFallecimiento;
        }

}
