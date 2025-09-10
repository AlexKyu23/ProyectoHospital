package Listas;

import Clases.Prescribir.presentation.PrescripcionModel;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPrescripciones {

    // Lista que guarda todas las prescripciones
    private static List<PrescripcionModel> prescripciones = new ArrayList<>();

    // Agregar una prescripción
    public static void agregar(PrescripcionModel p) {
        prescripciones.add(p);
    }

    // Obtener todas las prescripciones
    public static List<PrescripcionModel> getPrescripciones() {
        return prescripciones;
    }

    // Eliminar una prescripción
    public static void eliminar(PrescripcionModel p) {
        prescripciones.remove(p);
    }

    // Limpiar todas las prescripciones (opcional)
    public static void limpiar() {
        prescripciones.clear();
    }
}
