// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.parcial;

import java.util.Objects;

public class Sesion {
	
	public enum DiaSemana {
		LUNES, MARTES, MIERCOLES, JUEVES, VIERNES
	}

	private Actividad actividad;
	private DiaSemana dia;
	private int horaInicio; // formato 24h: 9 = 9:00, 14 = 14:00

	public Sesion(Actividad actividad, DiaSemana dia, int horaInicio) {
		this.actividad = actividad;
		this.dia = dia;
		this.horaInicio = horaInicio;
	}

	public Actividad getActividad() {
		return actividad;
	}

	public DiaSemana getDia() {
		return dia;
	}

	public int getHoraInicio() {
		return horaInicio;
	}

	@Override
	public int hashCode() {
		return Objects.hash(actividad, dia, horaInicio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sesion other = (Sesion) obj;
		return Objects.equals(actividad, other.actividad) && dia == other.dia && horaInicio == other.horaInicio;
	}

	@Override
	public String toString() {
		return "Sesion [actividad=" + actividad + ", dia=" + dia + ", horaInicio=" + horaInicio + "]";
	}
}