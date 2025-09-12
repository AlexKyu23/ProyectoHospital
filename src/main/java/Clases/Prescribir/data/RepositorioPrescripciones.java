package Clases.Prescribir.data;

import Clases.Prescribir.logic.Prescripcion;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPrescripciones {

    // Lista que guarda todas las prescripciones
    private static List<Prescripcion> prescripciones = new ArrayList<>();

    // Agregar una prescripción
    public static void agregar(Prescripcion p) {
        prescripciones.add(p);
    }

    // Obtener todas las prescripciones
    public static List<Prescripcion> getPrescripciones() {
        return prescripciones;
    }

    // Eliminar una prescripción
    public static void eliminar(Prescripcion p) {
        prescripciones.remove(p);
    }

    // Limpiar todas las prescripciones (opcional)
    public static void limpiar() {
        prescripciones.clear();
    }
}
