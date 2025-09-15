package Clases;

import Clases.Paciente.data.ListaPacientes;
import Clases.Medicamento.data.catalogoMedicamentos;
import Clases.Medico.data.ListaMedicos;
import Clases.Farmaceuta.data.ListaFarmaceutas;
import Clases.Usuario.data.ListaUsuarios;
import Clases.Receta.Data.RepositorioRecetas;

public class DatosIniciales {
    public static ListaMedicos listaMedicos = new ListaMedicos();
    public static ListaFarmaceutas listaFarmaceutas = new ListaFarmaceutas();
    public static ListaPacientes listaPacientes = new ListaPacientes();
    public static catalogoMedicamentos catalogoMed = new catalogoMedicamentos();
    public static ListaUsuarios listaUsuarios = new ListaUsuarios();
    public static RepositorioRecetas repositorioRecetas = new RepositorioRecetas();

    public static void cargarTodo() {
        System.out.println("⏳ Cargando listas desde XML...");

        listaMedicos.cargar();
        listaFarmaceutas.cargar();
        listaPacientes.cargar();
        catalogoMed.cargar();
        listaUsuarios.cargar();
        repositorioRecetas.cargar();

        System.out.println("✅ Listas cargadas correctamente.");
        System.out.println("👨‍⚕️ Médicos: " + listaMedicos.consulta().size());
        System.out.println("💊 Medicamentos: " + catalogoMed.consulta().size());
        System.out.println("👥 Pacientes: " + listaPacientes.consulta().size());
        System.out.println("🧑‍🔬 Farmacéuticos: " + listaFarmaceutas.consulta().size());
        System.out.println("🔐 Usuarios: " + listaUsuarios.getUsuarios().size());
        System.out.println("📋 Recetas: " + repositorioRecetas.getRecetas().size());
    }

    public static catalogoMedicamentos getCatalogoMed() {
        return catalogoMed;
    }

    public static RepositorioRecetas getRepositorioRecetas() {
        return repositorioRecetas;
    }
}