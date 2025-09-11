package Clases.Prescribir.data;

import Clases.Prescribir.logic.Prescripcion;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "prescripciones")
public class PrescripcionesWrapper {

    private List<Prescripcion> prescripciones;

    @XmlElement(name = "prescripcion")
    public List<Prescripcion> getPrescripciones() {
        return prescripciones;
    }

    public void setPrescripciones(List<Prescripcion> prescripciones) {
        this.prescripciones = prescripciones;
    }
}
