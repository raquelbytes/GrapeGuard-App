package Util;

import modelo.vo.Tratamiento;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TratamientoTableModel extends AbstractTableModel {
    private final List<Tratamiento> tratamientos;
    private final String[] columnNames = {"ID", "Nombre", "Cantidad", "Precio Unitario"};

    public TratamientoTableModel(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }

    @Override
    public int getRowCount() {
        return tratamientos.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tratamiento tratamiento = tratamientos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return tratamiento.getId(); 
            case 1:
                return tratamiento.getNombre();
            case 2:
                return tratamiento.getCantidad();
            case 3:
                return tratamiento.getPrecioUnitario(); 
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addTratamiento(Tratamiento tratamiento) {
        tratamientos.add(tratamiento);
        fireTableRowsInserted(tratamientos.size() - 1, tratamientos.size() - 1);
    }

    public void removeTratamiento(int rowIndex) {
        tratamientos.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateTratamiento(int rowIndex, Tratamiento tratamiento) {
        tratamientos.set(rowIndex, tratamiento);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos.clear();
        this.tratamientos.addAll(tratamientos);
    }

    public Tratamiento getTratamientoAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < tratamientos.size()) {
            return tratamientos.get(rowIndex);
        } else {
            return null; 
        }
    }

    public void clear() {
        tratamientos.clear();
        fireTableDataChanged();
    }
    
     public void actualizarDatos(List<Tratamiento> nuevosTratamientos) {
        setTratamientos(nuevosTratamientos);
        fireTableDataChanged();
    }
}