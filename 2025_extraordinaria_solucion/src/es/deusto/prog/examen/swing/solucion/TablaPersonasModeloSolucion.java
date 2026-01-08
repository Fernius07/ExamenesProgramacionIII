package es.deusto.prog.examen.swing.solucion;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import es.deusto.prog.examen.Persona;

public class TablaPersonasModeloSolucion extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private static final String[] COLUMNAS = { "Nombre", "Email", "Abono", "Actividades" };

    private List<Persona> personas;

    public TablaPersonasModeloSolucion(List<Persona> personas) {
        this.personas = personas;
    }

    // TODO Tarea 1 - Swing: Completa el modelo de datos

    @Override
    public int getColumnCount() {
        return COLUMNAS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNAS[column];
    }

    @Override
    public int getRowCount() {
        return personas.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Persona persona = personas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return persona.getNombre();
            case 1:
                return persona.getEmail();
            case 2:
                return persona.getTipoAbono();
            case 3:
                return persona.getActividades().size();
            default:
                return null;
        }
    }
}