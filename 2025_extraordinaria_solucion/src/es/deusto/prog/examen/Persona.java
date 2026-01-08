// IAG (ChatGPT 4o-mini)
// ADAPTADO: El código ha sido creado con con ChatGPT 4o-mini.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.deusto.prog.examen.Actividad.TipoActividad;

public class Persona {

	public enum TipoAbono {
		SIMPLE(1), 								 // Una actividad
		DUO(2),									 // Dos actividades
		COMPLETO(TipoActividad.values().length); // Todas las actividades
		
		private int numActividades;
		
		TipoAbono(int numActividades) {
            this.numActividades = numActividades;
		}
		
		public int getNumActividades() {
			return numActividades;
		}
	}
	
	private long id;
	private String nombre;
	private String email;
	private TipoAbono tipoAbono;
	
	private List<Actividad> actividades = new ArrayList<>();
	private List<Ejercicio> rutina = new ArrayList<>();
	
	public Persona(String nombre, String email, TipoAbono tipoAbono) {
		this.nombre = nombre;
		this.email = email;
		this.tipoAbono = tipoAbono;
	}
	
	public Persona(Long id, String nombre, String email, TipoAbono tipoAbono) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.tipoAbono = tipoAbono;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEmail() {
		return email;
	}

	public TipoAbono getTipoAbono() {
		return tipoAbono;
	}
	
	public void setTipoAbono(TipoAbono tipoAbono) {
		this.tipoAbono = tipoAbono;		
	}

	public List<Actividad> getActividades() {
		return actividades;
	}

	public void setActividades(List<Actividad> actividades) throws IllegalArgumentException {
		if (actividades != null) {
			if (tipoAbono.getNumActividades() != actividades.size()) {
				throw new IllegalArgumentException(
						String.format("El abono %s no permite apuntarse a %d actividades", 
								tipoAbono.toString(), actividades.size()));
			}
			
			this.actividades = actividades;
		}
	}
	
	public List<Ejercicio> getRutina() {
		return rutina;
	}
	
	public void setRutina(List<Ejercicio> rutina) {
		this.rutina = rutina;
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
		Persona other = (Persona) obj;
		return Objects.equals(email, other.email);
	}

	@Override
	public String toString() {
		return String.format("%s (%s)", nombre, email);		
	}
}