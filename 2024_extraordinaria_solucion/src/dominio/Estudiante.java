package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a un estudiante de una asignatura
 */
public class Estudiante {
	
	private int nia; // identificador único del estudiante
	private String nombre; // nombre del estudiante
	private String apellido; // primer apellido del estudiante
	private List<Asignatura> asignaturas; // asignaturas en las que está matriculado el estudiante
	
	/**
	 * Constructor de la clase estudiante
	 */
	public Estudiante(int nia, String nombre, String apellido) {
		this.nia = nia;
		this.nombre = nombre;
		this.apellido = apellido;
		asignaturas = new ArrayList<>();
	}
	
	public Estudiante(int nia, String nombre, String apellido, List<Asignatura> asignaturas) {
		this(nia, nombre, apellido);
		this.asignaturas = asignaturas;
	}
	
	public int getNIA() {
		return nia;
	}
	
	public void setNIA(int nia) {
		this.nia = nia;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public void addAsignatura(Asignatura asignatura) {
		asignaturas.add(asignatura);
	}
	
	public List<Asignatura> getAsignaturas() {
		return asignaturas;
	}
	
	@Override
	public String toString() {
		return String.format("%d: %s %s", nia, nombre, apellido);
	}
}
