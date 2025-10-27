package data;

import logic.Farmaceuta;
import logic.Medico;
import logic.Paciente;
import logic.Receta;
import logic.Service;
import logic.Usuario;

public class DatosIniciales {

    public static void inicializarDatos() {
        System.out.println("⏳ Inicializando datos en la base de datos...");

        Service service = Service.instance();
        try {
            // Verificar si hay usuarios (indicador de base de datos vacía)
            if (service.findAllUsuario().isEmpty()) {
                System.out.println("⚠️ No hay usuarios registrados. Precargando datos iniciales...");

                // Crear usuarios iniciales
                service.createUsuario(new Usuario("adm01", "Administrador", "admin", "ADM"));
                service.createUsuario(new Usuario("med01", "Dr. Salas", "med", "MED"));
                service.createUsuario(new Usuario("far01", "Luis Mora", "far", "FAR"));

                // Crear médicos
                service.createMedico(new Medico("med01", "Dr. Salas", "med", "Cardiología"));

                // Crear farmacéuticos
                service.createFarmaceuta(new Farmaceuta("far01", "Luis Mora", "far"));
                System.out.println("✅ Precarga completada en la base de datos.");
            } else {
                System.out.println("✅ Base de datos ya contiene datos. No se realiza precarga.");
            }

            // Mostrar estadísticas
            System.out.println("👨‍⚕️ Médicos: " + service.findAllMedico().size());
            System.out.println("💊 Medicamentos: " + service.findAllMedicamento().size());
            System.out.println("👥 Pacientes: " + service.findAllPaciente().size());
            System.out.println("🧑‍🔬 Farmacéuticos: " + service.findAllFarmaceuta().size());
            System.out.println("🔐 Usuarios: " + service.findAllUsuario().size());
            System.out.println("📋 Recetas: " + service.findAllReceta().size());

        } catch (Exception e) {
            System.err.println("Error al inicializar datos: " + e.getMessage());
        }
    }
}