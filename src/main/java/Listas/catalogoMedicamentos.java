package Listas;

import Clases.Medicamento.logic.Medicamento;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "catalogoMedicamentos")
public class catalogoMedicamentos {
    private List<Medicamento> medicamentos;

    public catalogoMedicamentos() {
        medicamentos = new ArrayList<>();
    }

    @XmlElement(name = "medicamento")
    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public void inclusion(Medicamento medicamento) {
        medicamentos.add(medicamento);
    }

    public List<Medicamento> consulta() {
        return medicamentos;
    }


    public List<Medicamento> busquedaPorDescripcion(String descripcion) {
        List<Medicamento> resultados = new ArrayList<>();
        for (Medicamento m : medicamentos) {
            if (m.getDescripcion().equalsIgnoreCase(descripcion)) {
                resultados.add(m);
            }
        }
        return resultados;
    }


    public Medicamento busquedaPorCodigo(int codigo) {
        for (Medicamento m : medicamentos) {
            if (m.getCodigo() == codigo) {
                return m;
            }
        }
        return null;
    }

    public void modificacion(int codigo, Medicamento nuevoMedicamento) {
        for (int i = 0; i < medicamentos.size(); i++) {
            if (medicamentos.get(i).getCodigo() == codigo) {
                medicamentos.set(i, nuevoMedicamento);
                return;
            }
        }
    }


    public void borrado(int codigo) {
        medicamentos.removeIf(m -> m.getCodigo() == codigo);
    }


    public void mostrarDetalles() {
        for (Medicamento m : medicamentos) {
            System.out.println(m);
        }
    }
}
