package Clases.Persistencia;

import Clases.Receta.logic.Receta;
import Clases.Usuario.logic.Usuario;
import Clases.Medicamento.logic.Medicamento;
import Clases.Paciente.logic.Paciente;
import Clases.Medico.logic.Medico;
import Clases.Farmaceuta.logic.Farmaceuta;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

import java.util.List;

@XmlRootElement(name = "datosSistema")
public class DatosSistemaWrapper {
    private List<Usuario> usuarios;
    private List<Receta> recetas;
    private List<Medicamento> medicamentos;
    private List<Paciente> pacientes;
    private List<Medico> medicos;
    private List<Farmaceuta> farmaceutas;
    public DatosSistemaWrapper() {}

    @XmlElementWrapper(name = "usuarios")
    @XmlElement(name = "usuario")
    public List<Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(List<Usuario> usuarios) { this.usuarios = usuarios; }

    @XmlElementWrapper(name = "recetas")
    @XmlElement(name = "receta")
    public List<Receta> getRecetas() { return recetas; }
    public void setRecetas(List<Receta> recetas) { this.recetas = recetas; }

    @XmlElementWrapper(name = "medicamentos")
    @XmlElement(name = "medicamento")
    public List<Medicamento> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<Medicamento> medicamentos) { this.medicamentos = medicamentos; }

    @XmlElementWrapper(name = "pacientes")
    @XmlElement(name = "paciente")
    public List<Paciente> getPacientes() { return pacientes; }
    public void setPacientes(List<Paciente> pacientes) { this.pacientes = pacientes; }

    @XmlElementWrapper(name = "medicos")
    @XmlElement(name = "medico")
    public List<Medico> getMedicos() { return medicos; }
    public void setMedicos(List<Medico> medicos) { this.medicos = medicos; }

    @XmlElementWrapper(name = "farmaceutas")
    @XmlElement(name = "farmaceuta")
    public List<Farmaceuta> getFarmaceutas() { return farmaceutas; }
    public void setFarmaceutas(List<Farmaceuta> farmaceutas) { this.farmaceutas = farmaceutas; }
}
