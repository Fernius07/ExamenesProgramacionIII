package es.deusto.prog3.examen.tabla;

import javax.swing.table.AbstractTableModel;

import es.deusto.prog3.examen.domain.PiezaAjedrez;

public class TableroModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private String[] nombreColumnas = {
		"a", "b", "c", "d", "e", "f", "g", "h"
	};
	
	private PiezaAjedrez[][] tablero;
	
	
	public TableroModel(PiezaAjedrez[][] tablero) {
		this.tablero = tablero;
	}

	@Override
	public int getColumnCount() {
		return tablero.length + 2;
	}

	@Override
	public int getRowCount() {
		return tablero.length + 1;
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (column == 0 || column == 9) {
			if (row != 8) {
				return tablero.length - row;
			} else {
				return "";
			}
		}
		
		if (row == 8) {
			return nombreColumnas[column - 1];
		}
		
		return tablero[tablero.length - row  - 1][column - 1];
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 0 || column == 9) {
			return "";
		}
		
		 return nombreColumnas[column - 1];
	}

}
