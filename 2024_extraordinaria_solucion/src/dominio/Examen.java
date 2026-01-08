package dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Clase que representa los resultados de un examen
 */
public class Examen {

	private LocalDate fecha; // fecha en la que se realizó el examen
	private int nia; // identificador del alumno
	private int asignatura; // id de la asignatura
	private List<Float> resultados; // resultados obtenidos en cada competencia de la asignatura
	
	public Examen(LocalDate fecha, int nia, int asignatura, List<Float> resultados) {
		this.fecha = fecha;
		this.nia = nia;
		this.asignatura = asignatura;
		this.resultados = resultados; 
	}
	
	public LocalDate getFecha() {
		return fecha;
	}


	public int getNia() {
		return nia;
	}


	public int getAsignatura() {
		return asignatura;
	}


	public List<Float> getResultados() {
		return resultados;
	}
	
	@Override
	public String toString() {
		return String.format("%s (%s): %s", asignatura, DateTimeFormatter.BASIC_ISO_DATE.format(fecha), resultados);
	}
}
