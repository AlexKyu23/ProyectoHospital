package Clases.Medico.logic;

import Clases.Trabajador.Trabajador;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "medico")
public class Medico extends Trabajador {
    private String especialidad;

    public Medico() {
        super("", "", "");
        this.especialidad = "";
    }

    public Medico(String id, String nombre, String clave, String especialidad) {
        super(id, nombre, clave);
        this.especialidad = especialidad;
    }

    @XmlElement
    @Override
    public String getId() {
        return super.getId();
    }

    @XmlElement
    @Override
    public String getNombre() {
        return super.getNombre();
    }

    @XmlElement
    @Override
    public String getClave() {
        return super.getClave();
    }

    @XmlElement
    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return "Medico{id='" + id + "', nombre='" + nombre +
                "', clave='" + getClave() + "', especialidad='" + especialidad + "'}";
    }
}