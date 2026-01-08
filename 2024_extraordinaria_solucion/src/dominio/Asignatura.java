package dominio;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una asignatura que puede cursar un estudiante.
 * Una asignatura tiene varias competencias que deben ser superadas por el estudiante.
 */
public class Asignatura {

	private int codigo; // codigo de la asignatura (p. ej. 145315)
	private String nombre; // nombre de la asignatura (p. ej. Programación III)
	private List<Competencia> competencias; // lista de competencias de la asignatura
	
	public Asignatura(int codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.competencias = new ArrayList<Competencia>();
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void addCompetencia(Competencia competencia) {
		competencias.add(competencia);
	}

	public List<Competencia> getCompetencias() {
		return competencias;
	}
	
	@Override
	public String toString() {
		return String.format("%6d: %s", codigo, nombre);
	}
}
