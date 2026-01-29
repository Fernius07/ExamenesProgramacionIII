package es.deusto.prog.biblioteca.dominio;

public enum Genero {
	FICCION("Ficción"),
	NO_FICCION("No Ficción"),
	NOVELA_HISTORICA("Novela Histórica"),
	CIENCIA_FICCION("Ciencia Ficción"),
	FANTASIA("Fantasía"),
	MISTERIO("Misterio"),
	THRILLER("Thriller"),
	ROMANCE("Romance"),
	BIOGRAFIA("Biografía"),
	ENSAYO("Ensayo"),
	POESIA("Poesía"),
	INFANTIL("Infantil");
	
	private final String nombre;
	
	Genero(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
}
