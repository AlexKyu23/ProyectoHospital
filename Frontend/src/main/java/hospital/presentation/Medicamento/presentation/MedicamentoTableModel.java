package hospital.presentation.Medicamento.presentation;

import hospital.presentation.AbstractTableModel;
import logic.Medicamento;

import java.util.ArrayList;
import java.util.List;

public class MedicamentoTableModel extends AbstractTableModel<Medicamento> {
    public static final int CODIGO = 0;
    public static final int NOMBRE = 1;
    public static final int DESCRIPCION = 2;

    public MedicamentoTableModel(int[] cols, List<Medicamento> rows) {
        super(cols, rows != null ? rows : new ArrayList<>());
    }

    @Override
    protected void initColNames() {
        colNames = new String[3];
        colNames[CODIGO] = "Código";
        colNames[NOMBRE] = "Nombre";
        colNames[DESCRIPCION] = "Descripción";
    }

    @Override
    protected Object getPropetyAt(Medicamento m, int col) {
        return switch (cols[col]) {
            case CODIGO -> m.getCodigo();
            case NOMBRE -> m.getNombre();
            case DESCRIPCION -> m.getDescripcion();
            default -> "";
        };
    }
}
