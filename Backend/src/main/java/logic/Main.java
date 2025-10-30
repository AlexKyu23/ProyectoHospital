package logic;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Service service = Service.instance();
            System.out.println("✅ Conectado al servicio\n");

            // 👨‍⚕️ Médicos
            List<Medico> medicos = service.findAllMedico();
            System.out.println("📋 Médicos (" + medicos.size() + "):");
            for (Medico m : medicos) {
                System.out.println("   - " + m.getId() + " | " + m.getNombre() + " | " + m.getEspecialidad());
            }

            // 🧍 Pacientes
            List<Paciente> pacientes = service.findAllPaciente();
            System.out.println("\n📋 Pacientes (" + pacientes.size() + "):");
            for (Paciente p : pacientes) {
                System.out.println("   - " + p.getId() + " | " + p.getNombre() + " | " + p.getTelefono() + " | " + p.getFechaNacimiento());
            }

            // 💊 Medicamentos
            List<Medicamento> medicamentos = service.findAllMedicamento();
            System.out.println("\n📋 Medicamentos (" + medicamentos.size() + "):");
            for (Medicamento m : medicamentos) {
                System.out.println("   - " + m.getCodigo() + " | " + m.getNombre() + " | " + m.getDescripcion());
            }

            // 📄 Recetas
            List<Receta> recetas = service.findAllRecetas();
            System.out.println("\n📋 Recetas (" + recetas.size() + "):");
            for (Receta r : recetas) {
                System.out.println("   - " + r.getId() + " | Médico: " + r.getMedicoId() + " | Paciente: " + r.getPacienteId() +
                        " | Fecha: " + r.getFechaConfeccion() + " | Retiro: " + r.getFechaRetiro() + " | Estado: " + r.getEstado());
            }

            // 📝 Items de receta
            System.out.println("\n📋 Items de receta:");
            for (Receta r : recetas) {
                List<ItemReceta> items = service.findItemRecetaByReceta(r.getId());
                System.out.println("   Receta " + r.getId() + " (" + items.size() + " items):");
                for (ItemReceta ir : items) {
                    System.out.println("     - " + ir.getItemRecetaId() + " | Medicamento: " + ir.getMedicamentoCodigo() +
                            " | Cantidad: " + ir.getCantidad() + " | Indicaciones: " + ir.getIndicaciones());
                }
            }

            // 🚚 Prescripciones
            List<Prescripcion> prescripciones = service.findAllPrescripcion();
            System.out.println("\n📋 Prescripciones (" + prescripciones.size() + "):");
            for (Prescripcion p : prescripciones) {
                System.out.println("   - #" + p.getNumero() + " | Médico: " + p.getMedico().getNombre() +
                        " | Paciente: " + p.getPaciente().getNombre() + " | Estado: " + p.getEstado());
            }

            // 🔐 Usuarios
            List<Usuario> usuarios = service.findAllUsuario();
            System.out.println("\n📋 Usuarios (" + usuarios.size() + "):");
            for (Usuario u : usuarios) {
                System.out.println("   - " + u.getId() + " | " + u.getNombre() + " | Rol: " + u.getRol());
            }

            System.out.println("\n✅ Verificación completa de contenido en base de datos");

        } catch (Exception e) {
            System.err.println("❌ Error durante la verificación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
