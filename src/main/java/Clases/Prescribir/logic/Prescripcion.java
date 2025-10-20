package Clases.Prescribir.logic;

import Clases.Paciente.logic.Paciente;
import Clases.Medico.logic.Medico;
import Clases.Receta.logic.ItemReceta;

import java.time.LocalDateTime;

public class Prescripcion {

    private int numero;                // ✅ corresponde al campo AUTO_INCREMENT en SQL
    private Paciente paciente;
    private Medico medico;
    private ItemReceta item;           // ✅ un solo item, igual que antes
    private LocalDateTime fechaConfeccion;
    private LocalDateTime fechaRetiro;
    private String estado;             // "CONFECCIONADA", "EN_PROCESO", "LISTA", "ENTREGADA"

    // 🔹 Constructor vacío (necesario para SQL y compatibilidad con JAXB anterior)
    public Prescripcion() {
    }

    public Prescripcion(int numero, Paciente paciente, Medico medico, ItemReceta item,
                        LocalDateTime fechaConfeccion, LocalDateTime fechaRetiro, String estado) {
        this.numero = numero;
        this.paciente = paciente;
        this.medico = medico;
        this.item = item;
        this.fechaConfeccion = fechaConfeccion;
        this.fechaRetiro = fechaRetiro;
        this.estado = estado;
    }

    // ---------------- Getters y Setters ----------------

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public ItemReceta getItem() {
        return item;
    }

    public void setItem(ItemReceta item) {
        this.item = item;
    }

    public LocalDateTime getFechaConfeccion() {
        return fechaConfeccion;
    }

    public void setFechaConfeccion(LocalDateTime fechaConfeccion) {
        this.fechaConfeccion = fechaConfeccion;
    }

    public LocalDateTime getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(LocalDateTime fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // ---------------- toString ----------------
    @Override
    public String toString() {
        return "Prescripcion{" +
                "numero=" + numero +
                ", paciente=" + paciente +
                ", medico=" + medico +
                ", item=" + item +
                ", fechaConfeccion=" + fechaConfeccion +
                ", fechaRetiro=" + fechaRetiro +
                ", estado='" + estado + '\'' +
                '}';
    }
}