package es.deusto.ingenieria.esqui.swing;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import es.deusto.ingenieria.esqui.domino.Estacion;

public class EstacionTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final String[] COLUMNAS = {"PAÍS",
											  "CIUDAD", 
											  "ESTACIÓN",											  
											  "<html>KM.<br>ESQUIABLES</html"};
	private List<Estacion> estaciones;
	
	public EstacionTableModel(List<Estacion> estaciones) {
		this.estaciones = estaciones;
	}
	
	@Override
	public int getRowCount() {
		return this.estaciones.size();
	}

	@Override
	public int getColumnCount() {
		return COLUMNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Estacion estacion = this.estaciones.get(rowIndex);
		
		return switch (columnIndex) {
			case 0 -> estacion.getCiudad().getPais().getNombre(); 			
			case 1 -> estacion.getCiudad().getNombre();
			case 2 -> estacion.getNombre();
			case 3 -> estacion.getKmEsquiablesTotal();
			default -> null;
		};
	}
	
	@Override
	public String getColumnName(int column) {
		return COLUMNAS[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}