package hospital.presentation.Paciente.presentation;




import hospital.presentation.AbstractTableModel;
import logic.Paciente;

import java.util.List;

public class PacienteTableModel extends AbstractTableModel<Paciente> {
    public static final int ID = 0;
    public static final int NOMBRE = 1;
    public static final int TELEFONO = 2;
    public static final int FECHA = 3;

    public PacienteTableModel(int[] cols, List<Paciente> rows) {
        super(cols, rows);
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
        switch (cols[col]) {
            case ID: return p.getId();
            case NOMBRE: return p.getNombre();
            case TELEFONO: return p.getTelefono();
            case FECHA: return p.getFechaNacimiento();
            default: return "";
        }
    }
}
