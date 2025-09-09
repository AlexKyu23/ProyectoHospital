package Clases.Paciente;

import Clases.Persona.Persona;

import java.time.LocalDate;

public class Paciente extends Persona {
    private String telefono;
    private LocalDate fechaNacimiento;

    public Paciente(String id, String nombre, String telefono, LocalDate fechaNacimiento) {
        super(id, nombre);
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    @Override
    public String toString() {
        return "Paciente{id='" + id + "', nombre='" + nombre + "', fechaNacimiento=" + fechaNacimiento + "}";
    }
}
