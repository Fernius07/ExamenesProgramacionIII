package es.deusto.prog.examen.swing;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import es.deusto.prog.examen.Persona;

public class TablaPersonasModelo extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<Persona> personas;

    public TablaPersonasModelo(List<Persona> personas) {
        this.personas = personas;
    }

    // TODO Tarea 1 - Swing: Completa el modelo de datos

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}