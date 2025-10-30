package hospital.presentation.Medico.presentation;

import hospital.presentation.AbstractTableModel;
import logic.Medico;

import java.util.List;

public class MedicoTableModel extends AbstractTableModel<Medico> {
    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int ESPECIALIDAD = 2;

    public MedicoTableModel(int[] cols, List<Medico> rows) {
        super(cols, rows);
    }

    @Override
    protected void initColNames() {
        colNames = new String[3];
        colNames[ID] = "ID";
        colNames[NOMBRE] = "Nombre";
        colNames[ESPECIALIDAD] = "Especialidad";
    }

    @Override
    protected Object getPropetyAt(Medico m, int col) {
        return switch (cols[col]) {
            case ID -> m.getId();
            case NOMBRE -> m.getNombre();
            case ESPECIALIDAD -> m.getEspecialidad();
            default -> "";
        };
    }
}
