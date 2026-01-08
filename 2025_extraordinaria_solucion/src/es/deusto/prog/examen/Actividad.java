// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.ImageIcon;

import es.deusto.prog.examen.Sesion.DiaSemana;

public class Actividad implements Comparable<Actividad> {

	public enum TipoActividad {
		BOXEO(new Color(255, 237, 223)),		
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
	    
	    public ImageIcon getIcon() {
			String imageName = this.toString().toLowerCase() + ".png";
			String imagePath = "resources/images/" + imageName;				
			return new ImageIcon(imagePath);
	    }
	}

	private long id;
	private TipoActividad tipo;
	private int plazasMaximas;
	
	private List<Sesion> sesiones = new ArrayList<>();
	private List<Persona> personas = new ArrayList<>();

	public Actividad(TipoActividad tipo, int plazasMaximas) {
		this.tipo = tipo;
		this.plazasMaximas = plazasMaximas;
	}
	
	public Actividad(Long id, TipoActividad tipo, int plazasMaximas) {
		this.id = id;
		this.tipo = tipo;
		this.plazasMaximas = plazasMaximas;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}

	public TipoActividad getTipo() {
		return tipo;
	}
	
	public int getPlazasMaximas() {
		return plazasMaximas;
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
	
	public void addSesion(Sesion sesion) throws IllegalArgumentException {
		if (sesiones.contains(sesion)) {
			throw new IllegalArgumentException("La sesión ya existe");
		}
		
		if (sesion.getActividad() != this) {
			throw new IllegalArgumentException("La nueva sesión no pertence a la actividad");
		}
		
		sesiones.add(sesion);
    }
	
	public List<Persona> getPersonas() {
		return personas;
	}
	
	public void addPersona(Persona persona) throws IllegalArgumentException {
		if (personas.contains(persona)) {
			throw new IllegalArgumentException("La persona ya está inscrita en la actividad");
		}
		
		if (personas.size() == plazasMaximas) {
			throw new IllegalArgumentException("No hay plazas disponibles en la actividad");
		}

		personas.add(persona);
	}
	
	public int getPlazasDisponibles() {		
		return (plazasMaximas - personas.size());
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
		return String.format("[%s, %d sesiones, ocupación: %d/%d]", 
				tipo, sesiones.size(), personas.size(), plazasMaximas);
	}

	@Override
	public int compareTo(Actividad o) {
		return tipo.toString().compareTo(o.getTipo().toString());
	}
}