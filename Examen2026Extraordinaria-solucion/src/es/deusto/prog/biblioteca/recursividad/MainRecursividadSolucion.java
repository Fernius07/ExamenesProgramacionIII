package es.deusto.prog.biblioteca.recursividad;

import java.util.Arrays;
import java.util.List;

import es.deusto.prog.biblioteca.dominio.Autor;
import es.deusto.prog.biblioteca.dominio.Autor.Nacionalidad;
import es.deusto.prog.biblioteca.dominio.Genero;
import es.deusto.prog.biblioteca.dominio.Libro;

public class MainRecursividadSolucion {

	// SOLUCIÓN TAREA 1 - Recursividad
	
	// Método recursivo para contar libros por género
	public static int contarLibrosPorGenero(List<Libro> libros, Genero genero) {
		return contarLibrosPorGeneroRec(libros, genero, 0);
	}
	
	// Método auxiliar recursivo
	private static int contarLibrosPorGeneroRec(List<Libro> libros, Genero genero, int indice) {
		// Caso base: hemos llegado al final de la lista
		if (indice >= libros.size()) {
			return 0;
		}
		
		// Caso recursivo
		int count = 0;
		if (libros.get(indice).getGenero() == genero) {
			count = 1;
		}
		
		return count + contarLibrosPorGeneroRec(libros, genero, indice + 1);
	}
	
	// Método público para buscar libros por páginas
	public static void buscarLibrosPorPaginas(List<Libro> libros, int paginasMinimas) {
		buscarLibrosPorPaginasRec(libros, paginasMinimas, 0);
	}
	
	// Método auxiliar recursivo para buscar por páginas
	private static void buscarLibrosPorPaginasRec(List<Libro> libros, int paginasMinimas, int indice) {
		// Caso base: hemos llegado al final de la lista
		if (indice >= libros.size()) {
			return;
		}
		
		// Caso recursivo: si el libro cumple la condición, lo mostramos
		Libro libro = libros.get(indice);
		if (libro.getNumPaginas() >= paginasMinimas) {
			System.out.printf("  - %s (%d págs.)\n", 
				libro.getTitulo(), 
				libro.getNumPaginas());
		}
		
		// Llamada recursiva con el siguiente libro
		buscarLibrosPorPaginasRec(libros, paginasMinimas, indice + 1);
	}
	
	public static void main(String[] args) {
		List<Libro> libros = crearBiblioteca();
		
		System.out.println("=== TAREA 1: RECURSIVIDAD - SOLUCIÓN ===\n");
		
		// Casos de prueba para contar libros por género
		System.out.printf("Libros de %s: %d\n", 
			Genero.CIENCIA_FICCION, 
			contarLibrosPorGenero(libros, Genero.CIENCIA_FICCION));
		
		System.out.printf("Libros de %s: %d\n", 
			Genero.FANTASIA, 
			contarLibrosPorGenero(libros, Genero.FANTASIA));
		
		System.out.printf("Libros de %s: %d\n\n", 
			Genero.MISTERIO, 
			contarLibrosPorGenero(libros, Genero.MISTERIO));
		
		// Casos de prueba para buscar libros por páginas
		System.out.println("Libros con 500 o más páginas:");
		buscarLibrosPorPaginas(libros, 500);
		
		System.out.println("\nLibros con 300 o más páginas:");
		buscarLibrosPorPaginas(libros, 300);
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
			new Libro("Harry Potter y el cáliz de fuego", rowling, Genero.FANTASIA, 2000, 636)
		);
	}
}
