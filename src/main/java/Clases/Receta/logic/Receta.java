package Clases.Receta.logic;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "receta")
public class Receta {
    private String id;
    private String medicoId;
    private String pacienteId;
    private LocalDate fechaConfeccion;
    private LocalDate fechaRetiro;
    private EstadoReceta estado;
    private List<ItemReceta> medicamentos;

    public Receta() {
        medicamentos = new ArrayList<>();
        estado = EstadoReceta.CONFECCIONADA;
    }
    public Receta(String id, String medicoId, String pacienteId, LocalDate fechaConfeccion, LocalDate fechaRetiro, EstadoReceta estado) {
        this();
        this.id = id;
        this.medicoId = medicoId;
        this.pacienteId = pacienteId;
        this.fechaConfeccion = fechaConfeccion;
        this.fechaRetiro = fechaRetiro;
        this.estado = estado;
    }

    public Receta(String id, String medicoId, String pacienteId, LocalDate fechaConfeccion, LocalDate fechaRetiro) {
        this();
        this.id = id;
        this.medicoId = medicoId;
        this.pacienteId = pacienteId;
        this.fechaConfeccion = fechaConfeccion;
        this.fechaRetiro = fechaRetiro;
    }

    @XmlElement
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public String getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(String medicoId) {
        this.medicoId = medicoId;
    }

    @XmlElement
    public String getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(String pacienteId) {
        this.pacienteId = pacienteId;
    }

    @XmlElement
    public LocalDate getFechaConfeccion() {
        return fechaConfeccion;
    }

    public void setFechaConfeccion(LocalDate fechaConfeccion) {
        this.fechaConfeccion = fechaConfeccion;
    }

    @XmlElement
    public LocalDate getFechaRetiro() {
        return fechaRetiro;
    }

    public void setFechaRetiro(LocalDate fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }

    @XmlElement
    public EstadoReceta getEstado() {
        return estado;
    }

    public void setEstado(EstadoReceta estado) {
        this.estado = estado;
    }

    @XmlElement
    public List<ItemReceta> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<ItemReceta> medicamentos) {
        this.medicamentos = medicamentos;
    }
}
