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
        // TODO Tarea 2: devolver el valor correcto de la celda
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // TODO Tarea 2: permitir edición solo de la columna Minutos
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // TODO Tarea 2: actualizar minutos y notificar cambios
    }
}
