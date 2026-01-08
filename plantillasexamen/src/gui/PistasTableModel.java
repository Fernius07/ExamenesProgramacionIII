package gui;

import domain.Dificultad;
import domain.Pista;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Arrays;

public class PistasTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private List<Pista> pistas;
	private final List<String> columnas = Arrays.asList("Nombre", "Dificultad", "Estado", "Longitud (km)");

	public PistasTableModel(List<Pista> pistas) {
		this.pistas = pistas;
	}

	@Override
	public int getRowCount() {
		return pistas.size();
	}

	@Override
	public int getColumnCount() {
		return columnas.size();
	}

	@Override
	public String getColumnName(int column) {
		return columnas.get(column);
	}

	// CRUCIAL: Definir clases para que los renderers funcionen
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 1:
			return Dificultad.class; // Para nuestro Renderer personalizado
		case 3:
			return Double.class;
		default:
			return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Pista p = pistas.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return p.getNombre();
		case 1:
			return p.getDificultad();
		case 2:
			return p.isAbierta() ? "ABIERTA" : "CERRADA";
		case 3:
			return p.getLongitud();
		default:
			return null;
		}
	}

	public void actualizarDatos(List<Pista> nuevasPistas) {
		this.pistas = nuevasPistas;
		fireTableDataChanged();
	}
}