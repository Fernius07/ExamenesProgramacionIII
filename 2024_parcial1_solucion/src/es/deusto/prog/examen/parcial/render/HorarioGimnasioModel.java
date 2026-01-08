package es.deusto.prog.examen.parcial.render;

import es.deusto.prog.examen.parcial.Actividad;
import es.deusto.prog.examen.parcial.Sesion;
import es.deusto.prog.examen.parcial.Sesion.DiaSemana;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class HorarioGimnasioModel extends AbstractTableModel {
    private static final long serialVersionUID = 1L;
    private static final int HORA_INICIO = 9;
    private static final int HORA_FIN = 14;
    private static final int COLUMNAS = DiaSemana.values().length + 1; // +1 para la columna de hora
    private List<Actividad> actividades;

    public HorarioGimnasioModel(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    @Override
    public int getRowCount() {
        return HORA_FIN - HORA_INICIO;
    }

    @Override
    public int getColumnCount() {
        return COLUMNAS;
    }

    @Override
    public String getColumnName(int column) {
        if (column > 0) {
        	return DiaSemana.values()[column - 1].toString();
        } else {
            return "";
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            // Columna de hora
            return String.format("%02d:00", HORA_INICIO + rowIndex);
        } else {
            // Columnas de actividades para cada día
            DiaSemana dia = DiaSemana.values()[columnIndex - 1];
            int hora = HORA_INICIO + rowIndex;

            for (Actividad actividad : actividades) {
                for (Sesion sesion : actividad.getSesiones()) {
                    if (sesion.getDia() == dia && sesion.getHoraInicio() == hora) {
                        return actividad; // Devolver el objeto actividad correspondiente
                    }
                }
            }
            
            return ""; // Si no hay actividad, devolver un String vacío
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // Todas las celdas son no editables
    }
}
