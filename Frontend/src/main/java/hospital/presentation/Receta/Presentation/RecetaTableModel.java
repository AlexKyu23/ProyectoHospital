package hospital.presentation.Receta.Presentation;



import hospital.presentation.AbstractTableModel;
import logic.ItemReceta;
import logic.Receta;

import java.util.List;

public class RecetaTableModel extends AbstractTableModel<Receta> {
    public static final int ID = 0;
    public static final int MEDICO = 1;
    public static final int PACIENTE = 2;
    public static final int FECHA = 3;
    public static final int ESTADO = 4;
    public static final int MEDICAMENTOS = 5;

    public RecetaTableModel(int[] cols, List<Receta> rows) {
        super(cols, rows);
    }

    @Override
    protected void initColNames() {
        colNames = new String[6];
        colNames[ID] = "ID";
        colNames[MEDICO] = "Médico";
        colNames[PACIENTE] = "Paciente";
        colNames[FECHA] = "Fecha";
        colNames[ESTADO] = "Estado";
        colNames[MEDICAMENTOS] = "Medicamentos";

    }

    @Override
    protected Object getPropetyAt(Receta r, int col) {
        return switch (cols[col]) {
            case ID -> r.getId();
            case MEDICO -> r.getMedicoId();
            case PACIENTE -> r.getPacienteId();
            case FECHA -> r.getFechaConfeccion();
            case ESTADO -> r.getEstado();
            case MEDICAMENTOS -> r.getMedicamentos().stream()
                    .map(ItemReceta::getDescripcion)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("Sin medicamentos");
            default -> "";
        };
    }

}
