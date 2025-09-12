package Clases.Paciente.logic;

import Clases.Persona.logic.Persona;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;

@XmlRootElement(name = "paciente")
public class Paciente extends Persona {
    private String telefono;
    private LocalDate fechaNacimiento;

    // 🔹 Constructor vacío obligatorio para JAXB
    public Paciente() {
        super("", "");
        this.telefono = "";
        this.fechaNacimiento = LocalDate.now();
    }

    // 🔹 Constructor completo
    public Paciente(String id, String nombre, String telefono, LocalDate fechaNacimiento) {
        super(id, nombre);
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    // ---------------- Getters y Setters ----------------

    @XmlElement
    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @XmlElement
    @Override
    public String getNombre() {
        return super.getNombre();
    }

    @Override
    public void setNombre(String nombre) {
        super.setNombre(nombre);
    }

    @XmlElement
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateAdapter.class) // 🔹 Adaptador para LocalDate
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    // ---------------- toString ----------------
    @Override
    public String toString() {
        return "Paciente{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}
