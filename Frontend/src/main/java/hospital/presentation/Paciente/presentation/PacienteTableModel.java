package hospital.presentation.Paciente.presentation;

import hospital.presentation.AbstractTableModel;
import logic.Paciente;

import java.util.ArrayList;
import java.util.List;

public class PacienteTableModel extends AbstractTableModel<Paciente> {
    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int TELEFONO = 2;
    public static final int FECHA = 3;

    public PacienteTableModel(int[] cols, List<Paciente> rows) {
        super(cols, rows != null ? rows : new ArrayList<>());
    }

    @Override
    protected void initColNames() {
        colNames = new String[4];
        colNames[ID] = "ID";
        colNames[NOMBRE] = "Nombre";
        colNames[TELEFONO] = "Teléfono";
        colNames[FECHA] = "Fecha de Nacimiento";
    }

    @Override
    protected Object getPropetyAt(Paciente p, int col) {
        return switch (cols[col]) {
            case ID -> p.getId();
            case NOMBRE -> p.getNombre();
            case TELEFONO -> p.getTelefono();
            case FECHA -> p.getFechaNacimiento();
            default -> "";
        };
    }
}
