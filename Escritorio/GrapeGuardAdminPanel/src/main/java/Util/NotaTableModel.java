package Util;

import modelo.vo.Nota;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.time.format.DateTimeFormatter;

public class NotaTableModel extends AbstractTableModel {
    private final List<Nota> notas;
    private final String[] columnNames = {"Viñedo", "ID", "Nota", "Fecha Creación", "Prioridad"};
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public NotaTableModel(List<Nota> notas) {
        this.notas = notas;
    }

    @Override
    public int getRowCount() {
        return notas.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Nota nota = notas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return nota.getVinedo(); 
            case 1:
                return nota.getId();
            case 2:
                return nota.getNota();
            case 3:
                return nota.getFechaCreacion().format(dateTimeFormatter); 
            case 4:
                return nota.getPrioridad();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void addNota(Nota nota) {
        notas.add(nota);
        fireTableRowsInserted(notas.size() - 1, notas.size() - 1);
    }

    public void removeNota(int rowIndex) {
        notas.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void updateNota(int rowIndex, Nota nota) {
        notas.set(rowIndex, nota);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void setNotas(List<Nota> notas) {
        this.notas.clear();
        this.notas.addAll(notas);
        fireTableDataChanged(); // Necesario para refrescar la tabla
    }
    
    public Nota getNotaAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < notas.size()) {
            return notas.get(rowIndex);
        } else {
            return null; 
        }
    }

    public void limpiar() {
        notas.clear();
        fireTableDataChanged();
    }
    
      public void actualizarDatos(List<Nota> nuevasNotas) {
        this.notas.clear();
        this.notas.addAll(nuevasNotas); 
        fireTableDataChanged();
    }
}
