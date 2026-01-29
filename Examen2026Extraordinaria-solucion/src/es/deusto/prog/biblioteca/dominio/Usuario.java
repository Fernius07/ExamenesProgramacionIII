package es.deusto.prog.biblioteca.dominio;

import java.util.Objects;

public class Usuario implements Comparable<Usuario> {
	
	public enum TipoUsuario {
		ESTUDIANTE("Estudiante", 5),
		PROFESOR("Profesor", 10),
		INVESTIGADOR("Investigador", 15),
		PUBLICO_GENERAL("Público General", 3);
		
		private final String nombre;
		private final int maxPrestamos;
		
		TipoUsuario(String nombre, int maxPrestamos) {
			this.nombre = nombre;
			this.maxPrestamos = maxPrestamos;
		}
		
		public String getNombre() {
			return nombre;
		}
		
		public int getMaxPrestamos() {
			return maxPrestamos;
		}
		
		@Override
		public String toString() {
			return nombre;
		}
	}
	
	private long id;
	private final String nombre;
	private final String email;
	private final TipoUsuario tipo;
	private int prestamosActivos;
	
	public Usuario(String nombre, String email, TipoUsuario tipo) {
		this.nombre = nombre;
		this.email = email;
		this.tipo = tipo;
		this.prestamosActivos = 0;
		this.id = -1;
	}
	
	public Usuario(long id, String nombre, String email, TipoUsuario tipo, int prestamosActivos) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.tipo = tipo;
		this.prestamosActivos = prestamosActivos;
	}
	
	public long getId() { return id; }
	public String getNombre() { return nombre; }
	public String getEmail() { return email; }
	public TipoUsuario getTipo() { return tipo; }
	public int getPrestamosActivos() { return prestamosActivos; }
	
	public void setId(long id) { this.id = id; }
	public void setPrestamosActivos(int prestamosActivos) { this.prestamosActivos = prestamosActivos; }
	
	public boolean puedePrestamo() {
		return prestamosActivos < tipo.getMaxPrestamos();
	}
	
	@Override
	public String toString() {
		return String.format("👤 %s (%s) - %s [%d/%d préstamos]", 
							 nombre, 
							 email,
							 tipo.getNombre(),
							 prestamosActivos,
							 tipo.getMaxPrestamos());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(email, other.email);
	}
	
	@Override
	public int compareTo(Usuario o) {
		return this.nombre.compareTo(o.nombre);
	}
}
