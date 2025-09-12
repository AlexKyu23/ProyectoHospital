
package Clases.Despacho.logic;

import Clases.Receta.logic.Receta;
import Clases.Receta.logic.RecetaService;
import Clases.Receta.logic.EstadoReceta;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DespachoService {

    // Buscar recetas confeccionadas con fecha válida
    public List<Receta> recetasDisponiblesParaDespacho() {
        LocalDate hoy = LocalDate.now();
        return RecetaService.instance().findAll().stream()
                .filter(r -> r.getEstado() == EstadoReceta.CONFECCIONADA)
                .filter(r -> {
                    LocalDate retiro = r.getFechaRetiro();
                    return !retiro.isBefore(hoy.minusDays(3)) && !retiro.isAfter(hoy.plusDays(3));
                })
                .collect(Collectors.toList());
    }

    // Cambiar estado a "proceso"
    public void iniciarDespacho(String recetaId) {
        RecetaService.instance().cambiarEstado(recetaId, EstadoReceta.EN_PROCESO);
    }

    // Cambiar estado a "lista"
    public void alistarMedicamentos(String recetaId) {
        RecetaService.instance().cambiarEstado(recetaId, EstadoReceta.LISTA);
    }

    // Cambiar estado a "entregada"
    public void entregarReceta(String recetaId) {
        RecetaService.instance().cambiarEstado(recetaId, EstadoReceta.ENTREGADA);
    }
}
