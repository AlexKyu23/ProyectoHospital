package hospital.presentation.Farmaceuta.presentation;

import hospital.presentation.AbstractTableModel;
import logic.Farmaceuta;

import java.util.ArrayList;
import java.util.List;

public class FarmaceutaTableModel extends AbstractTableModel<Farmaceuta> {
    public static final int ID = 0;
    public static final int NOMBRE = 1;

    public FarmaceutaTableModel(int[] cols, List<Farmaceuta> rows) {
        super(cols, rows != null ? rows : new ArrayList<>());
    }

    @Override
    protected void initColNames() {
        colNames = new String[2];
        colNames[ID] = "ID";
        colNames[NOMBRE] = "Nombre";
    }

    @Override
    protected Object getPropetyAt(Farmaceuta f, int col) {
        return switch (cols[col]) {
            case ID -> f.getId();
            case NOMBRE -> f.getNombre();
            default -> "";
        };
    }
}
