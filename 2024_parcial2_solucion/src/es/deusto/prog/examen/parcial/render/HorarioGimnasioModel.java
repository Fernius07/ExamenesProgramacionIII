package es.deusto.prog.examen.parcial.render;

import es.deusto.prog.examen.parcial.Actividad;
import es.deusto.prog.examen.parcial.Sesion;
import es.deusto.prog.examen.parcial.Sesion.DiaSemana;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

public class HorarioGimnasioModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private Map<DiaSemana, Map<Integer, Actividad>> mapaActividades;
	
	private int[] horas = {
			9, 10, 11, 12, 13
	};
	
	public HorarioGimnasioModel(List<Actividad> actividades) {
		mapaActividades = new HashMap<Sesion.DiaSemana, Map<Integer,Actividad>>();
		for (Actividad actividad : actividades) {
			for (Sesion sesion : actividad.getSesiones()) {
				int hora = sesion.getHoraInicio();
				DiaSemana dia = sesion.getDia();
				if (!mapaActividades.containsKey(dia)) {
					mapaActividades.put(dia, new HashMap<Integer, Actividad>());
				}
				
				mapaActividades.get(dia).put(hora, actividad);
			}
		}
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		if (column == 0) {
			return String.format("%02d:00", horas[row]);
		} 
		
		int hora = horas[row];
		DiaSemana dia = DiaSemana.values()[column - 1];
		return mapaActividades.get(dia).get(hora);
	}

	@Override
	public int getColumnCount() {
		return DiaSemana.values().length + 1;
	}

	@Override
	public int getRowCount() {
		return horas.length;
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0) return "";
		return DiaSemana.values()[column - 1].name();
	}

	
}
