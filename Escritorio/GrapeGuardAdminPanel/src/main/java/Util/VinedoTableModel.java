package Util;

import modelo.vo.Vinedo;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class VinedoTableModel extends AbstractTableModel {
    private final List<Vinedo> vinedos;
    private final String[] columnNames = {"ID", "Nombre", "Ubicación", "Fecha de Plantación", "Hectáreas"};
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public VinedoTableModel(List<Vinedo> vinedos) {
        this.vinedos = vinedos;
    }

    @Override
    public int getRowCount() {
        return vinedos.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vinedo vinedo = vinedos.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return vinedo.getId();
            case 1:
                return vinedo.getNombre();
            case 2:
                return vinedo.getUbicacion();
            case 3:
                return dateFormat.format(vinedo.getFechaPlantacion());
            case 4:
                return vinedo.getHectareas();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addVinedo(Vinedo vinedo) {
        vinedos.add(vinedo);
        fireTableRowsInserted(vinedos.size() - 1, vinedos.size() - 1);
    }

    public void removeVinedo(int rowIndex) {
        vinedos.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateVinedo(int rowIndex, Vinedo vinedo) {
        vinedos.set(rowIndex, vinedo);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void setVinedos(List<Vinedo> vinedos) {
        this.vinedos.clear();
        this.vinedos.addAll(vinedos);
        fireTableDataChanged();
    }

   public Vinedo getVinedoAt(int rowIndex) {
    if (rowIndex >= 0 && rowIndex < vinedos.size()) {
        return vinedos.get(rowIndex);
     
    } else {
        return null;
    }
}


    public void limpiar() {
        vinedos.clear();
        fireTableDataChanged();
    }
}
