package es.deusto.hospital.jtable;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class HabitacionModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private List<Parto> partos = new ArrayList<>();	
	private String[] columnas = { "HABITACIÓN", "MADRE", "BEBÉ", "ESTADO" };

	public HabitacionModel(List<Parto> partos) {
		this.partos = partos;
	}

	@Override
	public int getRowCount() {
		return partos.size();
	}

	@Override
	public int getColumnCount() {
		return columnas.length;
	}

	@Override
	public String getColumnName(int c) {
		return columnas[c];
	}

	@Override
	public boolean isCellEditable(int r, int c) {
		return false;
	}

	@Override
	public Object getValueAt(int r, int c) {
		Parto p = partos.get(r);
		
		return switch (c) {
			case 0 -> p.getHabitacion().substring(p.getHabitacion().indexOf("-")+1);
			case 1 -> p.getNombreMadre();
			case 2 -> p.getBebes();
			case 3 -> p.getEstado();
			default -> null;
		};
	}
}