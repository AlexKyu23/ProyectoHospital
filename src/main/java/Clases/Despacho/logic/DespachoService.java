package Clases.Despacho.logic;

import Clases.Receta.logic.Receta;
import Clases.Receta.logic.RecetaService;
import Clases.Receta.logic.EstadoReceta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DespachoService {

    // 🔍 Buscar recetas confeccionadas con fecha válida (±3 días desde hoy)
    public List<Receta> recetasDisponiblesParaDespacho() {
        LocalDate hoy = LocalDate.now();
        try {
            return RecetaService.instance().findAll().stream()
                    .filter(r -> r.getEstado() == EstadoReceta.CONFECCIONADA)
                    .filter(r -> {
                        LocalDate retiro = r.getFechaRetiro();
                        return retiro != null &&
                                !retiro.isBefore(hoy.minusDays(3)) &&
                                !retiro.isAfter(hoy.plusDays(3));
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error al obtener recetas para despacho: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // 🔄 Cambiar estado a EN_PROCESO
    public void iniciarDespacho(String recetaId) throws Exception {
        RecetaService.instance().cambiarEstado(recetaId, EstadoReceta.EN_PROCESO);
    }

    // 🔄 Cambiar estado a LISTA
    public void alistarMedicamentos(String recetaId) throws Exception {
        RecetaService.instance().cambiarEstado(recetaId, EstadoReceta.LISTA);
    }

    // 🔄 Cambiar estado a ENTREGADA
    public void entregarReceta(String recetaId) throws Exception {
        RecetaService.instance().cambiarEstado(recetaId, EstadoReceta.ENTREGADA);
    }
}
