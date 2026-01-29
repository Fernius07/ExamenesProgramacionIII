package es.deusto.prog.biblioteca.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import es.deusto.prog.biblioteca.dominio.Autor;
import es.deusto.prog.biblioteca.dominio.Autor.Nacionalidad;
import es.deusto.prog.biblioteca.dominio.Genero;
import es.deusto.prog.biblioteca.dominio.Libro;

public class MainSwing extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public MainSwing(List<Libro> libros) {
		setLayout(new BorderLayout());
		
		LibroTableModel modeloLibros = new LibroTableModel(libros);
		
		JTable tablaLibros = new JTable(modeloLibros);
		tablaLibros.setShowGrid(true);
		tablaLibros.setGridColor(Color.LIGHT_GRAY);
		
		// TODO TAREA 2: JTable y Eventos
		
		// TAREA 2.1: Ajusta el renderizado de la tabla para que:
		// - La altura de las filas sea de 35 píxeles
		// - La columna "Título" tenga un ancho de 300 píxeles
		// - La columna "Autor" tenga un ancho de 200 píxeles
		// - La columna "Páginas" tenga un ancho de 80 píxeles
		// - El encabezado de la tabla tenga una altura de 30 píxeles
		
		// INCORPORA AQUÍ TU CÓDIGO PARA AJUSTAR EL RENDERIZADO
		
		
		JScrollPane scrollPane = new JScrollPane(tablaLibros);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);
		
		// TAREA 2.2: Gestión de eventos
		// Añade un listener a la tabla para que cuando el usuario haga doble clic
		// en una fila, se muestre un JOptionPane con la información completa del libro
		// seleccionado usando su método toString()
		
		// INCORPORA AQUÍ TU CÓDIGO PARA LA GESTIÓN DE EVENTOS
		
		
		JLabel lblFooter = new JLabel("Sistema de Gestión de Biblioteca - Examen Extraordinario 2026");
		lblFooter.setForeground(Color.BLUE);
		lblFooter.setHorizontalAlignment(JLabel.CENTER);
		lblFooter.setBorder(new LineBorder(Color.GRAY));
		add(lblFooter, BorderLayout.SOUTH);
		
		setTitle("Biblioteca - Catálogo de Libros");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		List<Libro> libros = crearBiblioteca();
		
		SwingUtilities.invokeLater(() -> new MainSwing(libros));
	}
	
	// Método auxiliar para crear la biblioteca de prueba
	private static List<Libro> crearBiblioteca() {
		Autor cervantes = new Autor("Miguel de Cervantes", Nacionalidad.ES);
		Autor tolkien = new Autor("J.R.R. Tolkien", Nacionalidad.UK);
		Autor asimov = new Autor("Isaac Asimov", Nacionalidad.US);
		Autor christie = new Autor("Agatha Christie", Nacionalidad.UK);
		Autor garcia = new Autor("Gabriel García Márquez", Nacionalidad.ES);
		Autor orwell = new Autor("George Orwell", Nacionalidad.UK);
		Autor rowling = new Autor("J.K. Rowling", Nacionalidad.UK);
		Autor murakami = new Autor("Haruki Murakami", Nacionalidad.JP);
		
		return Arrays.asList(
			new Libro("Don Quijote de la Mancha", cervantes, Genero.NOVELA_HISTORICA, 1605, 863),
			new Libro("El Señor de los Anillos", tolkien, Genero.FANTASIA, 1954, 1178),
			new Libro("El Hobbit", tolkien, Genero.FANTASIA, 1937, 310),
			new Libro("Fundación", asimov, Genero.CIENCIA_FICCION, 1951, 255),
			new Libro("Yo, Robot", asimov, Genero.CIENCIA_FICCION, 1950, 224),
			new Libro("El fin de la eternidad", asimov, Genero.CIENCIA_FICCION, 1955, 288),
			new Libro("Asesinato en el Orient Express", christie, Genero.MISTERIO, 1934, 256),
			new Libro("Diez negritos", christie, Genero.MISTERIO, 1939, 272),
			new Libro("Cien años de soledad", garcia, Genero.FICCION, 1967, 471),
			new Libro("1984", orwell, Genero.CIENCIA_FICCION, 1949, 326),
			new Libro("Rebelión en la granja", orwell, Genero.FICCION, 1945, 144),
			new Libro("Harry Potter y la piedra filosofal", rowling, Genero.FANTASIA, 1997, 223),
			new Libro("Harry Potter y el cáliz de fuego", rowling, Genero.FANTASIA, 2000, 636),
			new Libro("Tokio Blues", murakami, Genero.FICCION, 1987, 389),
			new Libro("1Q84", murakami, Genero.FICCION, 2009, 928)
		);
	}
}
