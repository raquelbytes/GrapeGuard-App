package Util;

import modelo.vo.VinedoTratamiento;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VinedoTratamientoTableModel extends AbstractTableModel {
    private final List<VinedoTratamiento> vinedoTratamientos;
    private final String[] columnNames = {"ID", "Vinedo", "Tratamiento"};

    public VinedoTratamientoTableModel(List<VinedoTratamiento> vinedoTratamientos) {
        this.vinedoTratamientos = vinedoTratamientos;
    }

    @Override
    public int getRowCount() {
        return vinedoTratamientos.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        VinedoTratamiento vinedoTratamiento = vinedoTratamientos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return vinedoTratamiento.getId(); 
            case 1:
                return vinedoTratamiento.getVinedo();
            case 2:
                return vinedoTratamiento.getTratamiento();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addVinedoTratamiento(VinedoTratamiento vinedoTratamiento) {
        vinedoTratamientos.add(vinedoTratamiento);
        fireTableRowsInserted(vinedoTratamientos.size() - 1, vinedoTratamientos.size() - 1);
    }

    public void removeVinedoTratamiento(int rowIndex) {
        vinedoTratamientos.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateVinedoTratamiento(int rowIndex, VinedoTratamiento vinedoTratamiento) {
        vinedoTratamientos.set(rowIndex, vinedoTratamiento);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void setVinedoTratamientos(List<VinedoTratamiento> vinedoTratamientos) {
        this.vinedoTratamientos.clear();
        this.vinedoTratamientos.addAll(vinedoTratamientos);
    }

    public VinedoTratamiento getVinedoTratamientoAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < vinedoTratamientos.size()) {
            return vinedoTratamientos.get(rowIndex);
        } else {
            return null; 
        }
    }

    public void clear() {
        vinedoTratamientos.clear();
        fireTableDataChanged();
    }
}
