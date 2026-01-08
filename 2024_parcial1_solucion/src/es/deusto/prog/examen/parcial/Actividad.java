// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.parcial;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.deusto.prog.examen.parcial.Sesion.DiaSemana;

public class Actividad {

	public enum TipoActividad {
		BOXEO(new Color(173, 131, 80)),		
	    PILATES(new Color(224, 119, 125)),
	    SPINNING(new Color(220, 247, 99)),
	    YOGA(new Color(143, 213, 166)),
	    ZUMBA(new Color(183, 192, 238));

	    private final Color color;

	    private TipoActividad(Color color) {
	        this.color = color;
	    }

	    public Color getColor() {
	        return color;
	    }
	}

	private TipoActividad tipo;
	private List<Sesion> sesiones;

	public Actividad(TipoActividad tipo, int plazasMaximas) {
		this.tipo = tipo;
		this.sesiones = new ArrayList<>();
	}

	public TipoActividad getTipo() {
		return tipo;
	}
	
	public List<Sesion> getSesiones() {
		return sesiones;
	}

	public Sesion getSesion(DiaSemana dia, int horaInicio) {
		for (Sesion sesion : sesiones) {
			if (sesion.getDia().equals(dia) && sesion.getHoraInicio() == horaInicio) {
				return sesion;
			}
		}
		
		return null;
	}
	
	public void addSesion(Sesion sesion) {
		sesiones.add(sesion);
    }

	@Override
	public int hashCode() {
		return Objects.hash(tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actividad other = (Actividad) obj;
		return tipo == other.tipo;
	}
	
	@Override
	public String toString() {
		return "Actividad [tipo=" + tipo + "]";
	}
}