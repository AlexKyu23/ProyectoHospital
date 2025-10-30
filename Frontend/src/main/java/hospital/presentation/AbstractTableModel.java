package hospital.presentation;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTableModel<E> extends javax.swing.table.AbstractTableModel {
    protected List<E> rows;
    protected int[] cols;
    protected String[] colNames;

    public AbstractTableModel(int[] cols, List<E> rows) {
        this.cols = cols;
        this.rows = rows != null ? rows : new ArrayList<>();
        initColNames();
    }

    public void setRows(List<E> rows) {
        this.rows = rows != null ? rows : new ArrayList<>();
        fireTableDataChanged();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }

    @Override
    public String getColumnName(int col) {
        return colNames[cols[col]];
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return Object.class;
    }

    @Override
    public int getRowCount() {
        return rows != null ? rows.size() : 0;
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (rows == null || row >= rows.size()) return null;
        E e = rows.get(row);
        return getPropetyAt(e, col);
    }

    public E getRowAt(int row) {
        return rows != null && row < rows.size() ? rows.get(row) : null;
    }

    protected abstract Object getPropetyAt(E e, int col);
    protected abstract void initColNames();
}
