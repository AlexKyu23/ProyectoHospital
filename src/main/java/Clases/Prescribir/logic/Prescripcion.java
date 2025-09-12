package Clases.Prescribir.logic;

import Clases.Paciente.logic.Paciente;
import Clases.Medico.logic.Medico;
import Clases.Receta.logic.ItemReceta;
import Clases.Prescribir.data.LocalDateTimeAdapter;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "prescripcion")
public class Prescripcion {

    private Paciente paciente;
    private Medico medico;
    private List<ItemReceta> items;
    private LocalDateTime fechaConfeccion;
    private LocalDateTime fechaRetiro;
    private String estado; // "confeccionada", "enProceso", "lista", "entregada"

    // 🔹 Constructor vacío obligatorio para JAXB
    public Prescripcion() {
        this.items = new ArrayList<>();
    }

    public Prescripcion(Paciente paciente, Medico medico, List<ItemReceta> medicamentos,
                        LocalDateTime fechaConfeccion, LocalDateTime fechaRetiro, String estado) {
        this.paciente = paciente;
        this.medico = medico;
        this.items = medicamentos;
        this.fechaConfeccion = fechaConfeccion;
        this.fechaRetiro = fechaRetiro;
        this.estado = estado;
    }

    // ---------------- Getters y Setters ----------------

    @XmlElement
    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @XmlElement
    public Medico getMedico() {
        return medico;
    }
    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    @XmlElementWrapper(name = "items")
    @XmlElement(name = "item")
    public List<ItemReceta> getItems() {
        return items;
    }
    public void setItems(List<ItemReceta> items) {
        this.items = items;
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getFechaConfeccion() {
        return fechaConfeccion;
    }
    public void setFechaConfeccion(LocalDateTime fechaConfeccion) {
        this.fechaConfeccion = fechaConfeccion;
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    public LocalDateTime getFechaRetiro() {
        return fechaRetiro;
    }
    public void setFechaRetiro(LocalDateTime fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    @XmlElement
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
                "paciente=" + paciente +
                ", medico=" + medico +
                ", items=" + items +
                ", fechaConfeccion=" + fechaConfeccion +
                ", fechaRetiro=" + fechaRetiro +
                ", estado='" + estado + '\'' +
                '}';
    }
}
