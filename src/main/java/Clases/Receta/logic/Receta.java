package Clases.Receta.logic;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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

    // Constructor vacío para JAXB
    public Receta() {
        medicamentos = new ArrayList<>();
        estado = EstadoReceta.CONFECCIONADA;
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
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @XmlElement
    public String getMedicoId() { return medicoId; }
    public void setMedicoId(String medicoId) { this.medicoId = medicoId; }

    @XmlElement
    public String getPacienteId() { return pacienteId; }
    public void setPacienteId(String pacienteId) { this.pacienteId = pacienteId; }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getFechaConfeccion() { return fechaConfeccion; }
    public void setFechaConfeccion(LocalDate fechaConfeccion) { this.fechaConfeccion = fechaConfeccion; }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    public LocalDate getFechaRetiro() { return fechaRetiro; }
    public void setFechaRetiro(LocalDate fechaRetiro) { this.fechaRetiro = fechaRetiro; }

    @XmlElement
    public EstadoReceta getEstado() { return estado; }
    public void setEstado(EstadoReceta estado) { this.estado = estado; }

    // ✅ CORREGIDO: Anotaciones JAXB para serializar lista de medicamentos
    @XmlElementWrapper(name = "medicamentos")
    @XmlElement(name = "ItemReceta")
    public List<ItemReceta> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<ItemReceta> medicamentos) { this.medicamentos = medicamentos; }
}
