package es.deusto.prog.biblioteca.dominio;

import java.util.Date;
import java.util.Objects;

public class Prestamo implements Comparable<Prestamo> {
	
	private long id;
	private final Usuario usuario;
	private final Libro libro;
	private final Date fechaPrestamo;
	private Date fechaDevolucion;
	private final int diasMaximos;
	
	public Prestamo(Usuario usuario, Libro libro, int diasMaximos) {
		this.usuario = usuario;
		this.libro = libro;
		this.fechaPrestamo = new Date();
		this.fechaDevolucion = null;
		this.diasMaximos = diasMaximos;
		this.id = -1;
	}
	
	public Prestamo(long id, Usuario usuario, Libro libro, Date fechaPrestamo, Date fechaDevolucion, int diasMaximos) {
		this.id = id;
		this.usuario = usuario;
		this.libro = libro;
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.diasMaximos = diasMaximos;
	}
	
	public long getId() { return id; }
	public Usuario getUsuario() { return usuario; }
	public Libro getLibro() { return libro; }
	public Date getFechaPrestamo() { return fechaPrestamo; }
	public Date getFechaDevolucion() { return fechaDevolucion; }
	public int getDiasMaximos() { return diasMaximos; }
	
	public void setId(long id) { this.id = id; }
	public void setFechaDevolucion(Date fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
	
	public boolean isActivo() {
		return fechaDevolucion == null;
	}
	
	public long getDiasTranscurridos() {
		Date fechaFinal = fechaDevolucion != null ? fechaDevolucion : new Date();
		long diff = fechaFinal.getTime() - fechaPrestamo.getTime();
		return diff / (1000 * 60 * 60 * 24);
	}
	
	public boolean isRetrasado() {
		return isActivo() && getDiasTranscurridos() > diasMaximos;
	}
	
	@Override
	public String toString() {
		String estado = isActivo() ? "📖 ACTIVO" : "✓ DEVUELTO";
		if (isRetrasado()) {
			estado = "⚠️ RETRASADO";
		}
		return String.format("%s - %s prestó '%s' el %tF (máx. %d días) %s", 
							 estado,
							 usuario.getNombre(),
							 libro.getTitulo(),
							 fechaPrestamo,
							 diasMaximos,
							 fechaDevolucion != null ? String.format("- Devuelto: %tF", fechaDevolucion) : "");
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(fechaPrestamo, libro, usuario);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prestamo other = (Prestamo) obj;
		return Objects.equals(fechaPrestamo, other.fechaPrestamo) 
			&& Objects.equals(libro, other.libro)
			&& Objects.equals(usuario, other.usuario);
	}
	
	@Override
	public int compareTo(Prestamo o) {
		return this.fechaPrestamo.compareTo(o.fechaPrestamo);
	}
}
