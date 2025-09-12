package Clases.Farmaceuta.presentation;

import Clases.AbstractTableModel;
import Clases.Farmaceuta.logic.Farmaceuta;

import java.util.List;

public class FarmaceutaTableModel extends AbstractTableModel<Farmaceuta> {
    public static final int ID = 0;
    public static final int NOMBRE = 1;

    public FarmaceutaTableModel(int[] cols, List<Farmaceuta> rows) {
        super(cols, rows);
    }

    @Override
    protected void initColNames() {
        colNames = new String[2];
        colNames[ID] = "ID";
        colNames[NOMBRE] = "Nombre";
    }

    @Override
    protected Object getPropetyAt(Farmaceuta f, int col) {
        switch (cols[col]) {
            case ID: return f.getId();
            case NOMBRE: return f.getNombre();
            default: return "";
        }
    }
}
