package logic;

import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        Service service = Service.instance();

        try {
            System.out.println("✅ Conectado al servicio\n");

            // --------------------------
            // Mostrar todos los registros existentes
            // --------------------------
            mostrarTodo(service);

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void mostrarTodo(Service service) throws Exception {
        System.out.println("\n📋 Médicos:");
        for (Medico m : service.searchMedico(new Medico())) {
            System.out.println(" - " + m);
        }

        System.out.println("\n📋 Farmaceutas:");
        for (Farmaceuta f : service.searchFarmaceuta(new Farmaceuta())) {
            System.out.println(" - " + f);
        }

        System.out.println("\n📋 Pacientes:");
        for (Paciente p : service.searchPaciente(new Paciente())) {
            System.out.println(" - " + p);
        }

        System.out.println("\n📋 Medicamentos:");
        for (Medicamento m : service.searchMedicamento(new Medicamento())) {
            System.out.println(" - " + m);
        }

        System.out.println("\n📋 Recetas:");
        for (Receta r : service.searchReceta()) {
            System.out.println(" - " + r.getId() + " | Médico: " + r.getMedicoId() + " | Paciente: " + r.getPacienteId());
            System.out.println("   Items:");
            for (ItemReceta i : service.buscarItemsPorReceta(r.getId())) {
                System.out.println("    - " + i.getItemRecetaId() + " | " + i.getMedicamentoCodigo() + " | " +
                        i.getCantidad() + " | " + i.getIndicaciones());
            }
        }

        System.out.println("\n📋 Prescripciones:");
        for (Prescripcion p : service.listarPrescripciones()) {
            String fechaConfeccion = p.getFechaConfeccion() != null
                    ? p.getFechaConfeccion().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    : "N/A";
            String fechaRetiro = p.getFechaRetiro() != null
                    ? p.getFechaRetiro().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    : "N/A";

            System.out.println(" - #" + p.getNumero() + " | Médico: " + p.getMedico().getNombre() +
                    " | Paciente: " + p.getPaciente().getNombre() + " | Item: " + p.getItem().getMedicamentoCodigo() +
                    " | Estado: " + p.getEstado() +
                    " | Confección: " + fechaConfeccion +
                    " | Retiro: " + fechaRetiro);
        }
    }
}
