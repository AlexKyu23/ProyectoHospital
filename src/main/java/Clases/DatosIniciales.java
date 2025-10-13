package Clases;

import Clases.Farmaceuta.logic.Farmaceuta;
import Clases.Medico.logic.Medico;
import Clases.Paciente.data.ListaPacientes;
import Clases.Medicamento.data.catalogoMedicamentos;
import Clases.Medico.data.ListaMedicos;
import Clases.Farmaceuta.data.ListaFarmaceutas;
import Clases.Usuario.data.ListaUsuarios;
import Clases.Receta.Data.RepositorioRecetas;
import Clases.Persistencia.DatosSistemaWrapper;
import Clases.Persistencia.PersistenciaSistema;
import Clases.Usuario.logic.Usuario;

public class DatosIniciales {
    public static ListaMedicos listaMedicos = new ListaMedicos();
    public static ListaFarmaceutas listaFarmaceutas = new ListaFarmaceutas();
    public static ListaPacientes listaPacientes = new ListaPacientes();
    public static catalogoMedicamentos catalogoMed = new catalogoMedicamentos();
    public static ListaUsuarios listaUsuarios = new ListaUsuarios();
    public static RepositorioRecetas repositorioRecetas = new RepositorioRecetas();

    public static void cargarTodo() {
        System.out.println("⏳ Cargando listas desde sistema.xml...");

        DatosSistemaWrapper datos = PersistenciaSistema.cargarTodo();

        listaMedicos.setList(datos.getMedicos());
        listaFarmaceutas.setList(datos.getFarmaceutas());
        listaPacientes.setList(datos.getPacientes());
        catalogoMed.setList(datos.getMedicamentos());
        listaUsuarios.setUsuarios(datos.getUsuarios());
        repositorioRecetas.setRecetas(datos.getRecetas());

        System.out.println("✅ Listas cargadas correctamente.");
        System.out.println("👨‍⚕️ Médicos: " + listaMedicos.consulta().size());
        System.out.println("💊 Medicamentos: " + catalogoMed.consulta().size());
        System.out.println("👥 Pacientes: " + listaPacientes.consulta().size());
        System.out.println("🧑‍🔬 Farmacéuticos: " + listaFarmaceutas.consulta().size());
        System.out.println("🔐 Usuarios: " + listaUsuarios.getUsuarios().size());
        System.out.println("📋 Recetas: " + repositorioRecetas.getRecetas().size());

        // 🔹 Precarga si el sistema está vacío
        if (listaUsuarios.getUsuarios().isEmpty()) {
            System.out.println("⚠️ No hay usuarios registrados. Precargando datos iniciales...");

            listaUsuarios.inclusion(new Usuario("adm01", "Administrador", "admin", "ADM"));
            listaUsuarios.inclusion(new Usuario("med01", "Dr. Salas", "med", "MED"));
            listaUsuarios.inclusion(new Usuario("far01", "Luis Mora", "far", "FAR"));

            listaMedicos.inclusion(new Medico("med01", "Dr. Salas", "med", "Cardiología"));
            listaFarmaceutas.inclusion(new Farmaceuta("far01", "Luis Mora", "far"));

            guardarTodo();
            System.out.println("✅ Precarga completada y guardada.");
        }
    }


    public static void guardarTodo() {
        DatosSistemaWrapper datos = new DatosSistemaWrapper();
        datos.setMedicos(listaMedicos.consulta());
        datos.setFarmaceutas(listaFarmaceutas.consulta());
        datos.setPacientes(listaPacientes.consulta());
        datos.setMedicamentos(catalogoMed.consulta());
        datos.setUsuarios(listaUsuarios.getUsuarios());
        datos.setRecetas(repositorioRecetas.getRecetas());

        PersistenciaSistema.guardarTodo(datos);
        System.out.println("💾 sistema.xml guardado correctamente.");
    }

    public static catalogoMedicamentos getCatalogoMed() {
        return catalogoMed;
    }

    public static RepositorioRecetas getRepositorioRecetas() {
        return repositorioRecetas;
    }
}
