package es.deusto.prog.biblioteca.swing;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import es.deusto.prog.biblioteca.dominio.Libro;

public class LibroTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private List<Libro> libros;
	private String[] columnas = {"Título", "Autor", "Género", "Año", "Páginas", "Disponible"};
	
	public LibroTableModel(List<Libro> libros) {
		this.libros = libros;
	}
	
	@Override
	public int getRowCount() {
		return libros.size();
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
		Libro libro = libros.get(rowIndex);
		
		switch (columnIndex) {
			case 0: return libro.getTitulo();
			case 1: return libro.getAutor().getNombre();
			case 2: return libro.getGenero().getNombre();
			case 3: return libro.getAnioPublicacion();
			case 4: return libro.getNumPaginas();
			case 5: return libro.isDisponible() ? "Sí" : "No";
			default: return null;
		}
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case 3: return Integer.class;
			case 4: return Integer.class;
			default: return String.class;
		}
	}
	
	public Libro getLibro(int rowIndex) {
		return libros.get(rowIndex);
	}
}
