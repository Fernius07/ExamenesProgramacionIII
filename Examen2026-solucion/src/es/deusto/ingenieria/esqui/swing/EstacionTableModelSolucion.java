package es.deusto.ingenieria.esqui.swing;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import es.deusto.ingenieria.esqui.domino.Estacion;

public class EstacionTableModelSolucion extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final String[] COLUMNAS = { "PAÍS",
											   "ESTACIÓN",
											   "CIUDAD",
											   "APERTURA",
											   "CLIMA",
											   "<html>KM.<br>ESQUIABLES</html>" };
	private List<Estacion> estaciones;

	public EstacionTableModelSolucion(List<Estacion> estaciones) {
		this.estaciones = estaciones;
	}

	@Override
	public int getRowCount() {
		return (estaciones != null) ? estaciones.size() : 0;
	}

	@Override
	public int getColumnCount() {
		return COLUMNAS.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Estacion e = estaciones.get(rowIndex);

		return switch (columnIndex) {
			case 0 -> e.getCiudad().getPais();
	        case 1 -> e;
	        case 2 -> e.getCiudad();
	        case 3 -> (e.getEstado() != null) ? e.getEstado().getApertura() : null;
	        case 4 -> e.getEstado();
	        case 5 -> e;
			default -> null;
		};
	}

	@Override
	public String getColumnName(int column) {
		return COLUMNAS[column];
	}
}