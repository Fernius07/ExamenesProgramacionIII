package es.deusto.prog3.examen2026extra.swing;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import es.deusto.prog3.examen2026extra.Actividad;

public class TablaActividadesModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final List<Actividad> actividades;
    private final String[] columnas = { "Nombre", "Tipo", "Intensidad", "Minutos" };

    public TablaActividadesModel(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    @Override
    public int getRowCount() {
        return actividades.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Actividad a = actividades.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> a.getNombre();
            case 1 -> a.getTipo();
            case 2 -> a.getIntensidad();
            case 3 -> a.getMinutos();
            default -> "";
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex != 3) {
            return;
        }
        try {
            int nuevosMinutos = Integer.parseInt(aValue.toString());
            if (nuevosMinutos < 0) {
                return;
            }
            actividades.get(rowIndex).setMinutos(nuevosMinutos);
            fireTableCellUpdated(rowIndex, columnIndex);
        } catch (NumberFormatException ignored) {
            // Si el valor no es numérico, no se actualiza.
        }
    }
}
