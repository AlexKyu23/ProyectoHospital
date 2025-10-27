package logic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        try {
            Service service = Service.instance();
            System.out.println("✅ Conectado al servicio");


            service.resetDatosDePrueba();


            // 👨‍⚕️ Médico
            Medico medico = new Medico("M100", "Dr. House", "clave123", "Diagnóstico");
            service.createMedico(medico);

            // 🧍 Paciente
            Paciente paciente = new Paciente("P200", "Lisa Cuddy", "8888-8888", LocalDate.of(1980, 5, 20));
            service.createPaciente(paciente);

            // 💊 Medicamento
            Medicamento medicamento = new Medicamento("Ibuprofeno", "Antiinflamatorio", 3001);
            service.createMedicamento(medicamento);

            // 📄 Receta
            Receta receta = new Receta("R500", "M100", "P200", LocalDate.now(), LocalDate.now().plusDays(3));
            service.createReceta(receta);

            // 📝 ItemReceta
            ItemReceta item = new ItemReceta("IR600", "R500", 3001, "Tomar cada 8h", 10, "Con agua", 5);
            service.createItemReceta(item);

            // 🚚 Prescripción
            Prescripcion prescripcion = new Prescripcion(0, paciente, medico, item, LocalDateTime.now(), LocalDateTime.now().plusDays(2), "CONFECCIONADA");
            service.createPrescripcion(prescripcion);
            System.out.println("✅ Prescripción creada con número: " + prescripcion.getNumero());

            // 🔐 Usuario
            Usuario usuario = new Usuario("U700", "UsuarioPrueba", "claveU", "MED");
            service.createUsuario(usuario);
            boolean loginOk = service.verificarClaveUsuario("U700", "claveU");
            System.out.println("✅ Login exitoso: " + loginOk);

            System.out.println("🎉 Prueba completada correctamente");

        } catch (Exception e) {
            System.err.println("❌ Error durante la prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
