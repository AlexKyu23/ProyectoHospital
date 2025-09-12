package Clases.Medicamento.presentation;

import Clases.AbstractTableModel;
import Clases.Medicamento.logic.Medicamento;


import java.util.List;

public class MedicamentoTableModel extends AbstractTableModel<Medicamento> {
    public static final int CODIGO = 0;
    public static final int NOMBRE = 1;
    public static final int DESCRIPCION = 2;

    public MedicamentoTableModel(int[] cols, List<Medicamento> rows) {
        super(cols, rows);
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
        switch (cols[col]) {
            case CODIGO: return m.getCodigo();
            case NOMBRE: return m.getNombre();
            case DESCRIPCION: return m.getDescripcion();
            default: return "";
        }
    }
}
