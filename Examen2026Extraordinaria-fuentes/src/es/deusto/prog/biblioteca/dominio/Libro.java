package es.deusto.prog.biblioteca.dominio;

import java.util.Objects;

public class Libro implements Comparable<Libro> {
	
	private long id;
	private final String titulo;
	private final Autor autor;
	private final Genero genero;
	private final int anioPublicacion;
	private final int numPaginas;
	private boolean disponible;
	
	public Libro(String titulo, Autor autor, Genero genero, int anioPublicacion, int numPaginas) {
		this.titulo = titulo;
		this.autor = autor;
		this.genero = genero;
		this.anioPublicacion = anioPublicacion;
		this.numPaginas = numPaginas;
		this.disponible = true;
		this.id = -1;
	}
	
	public Libro(long id, String titulo, Autor autor, Genero genero, int anioPublicacion, int numPaginas, boolean disponible) {
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;
		this.genero = genero;
		this.anioPublicacion = anioPublicacion;
		this.numPaginas = numPaginas;
		this.disponible = disponible;
	}
	
	public long getId() { return id; }
	public String getTitulo() { return titulo; }
	public Autor getAutor() { return autor; }
	public Genero getGenero() { return genero; }
	public int getAnioPublicacion() { return anioPublicacion; }
	public int getNumPaginas() { return numPaginas; }
	public boolean isDisponible() { return disponible; }
	
	public void setId(long id) { this.id = id; }
	public void setDisponible(boolean disponible) { this.disponible = disponible; }
	
	@Override
	public String toString() {
		String disponibilidad = disponible ? "✓ Disponible" : "✗ Prestado";
		return String.format("📚 '%s' por %s [%s] (%d) - %d págs. - %s", 
							 titulo, 
							 autor.getNombre(),
							 genero.getNombre(),
							 anioPublicacion,
							 numPaginas,
							 disponibilidad);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(autor, titulo);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		return Objects.equals(autor, other.autor) && Objects.equals(titulo, other.titulo);
	}
	
	@Override
	public int compareTo(Libro o) {
		int res = this.autor.compareTo(o.autor);
		
		if (res == 0) {
			res = this.titulo.compareTo(o.titulo);
		}
		
		return res;
	}
}
