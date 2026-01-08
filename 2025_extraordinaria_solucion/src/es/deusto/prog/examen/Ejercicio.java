// IAG (ChatGPT 4o-mini)
// ADAPTADO: El código ha sido creado con con ChatGPT 4o-mini.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen;

import java.util.Objects;

public class Ejercicio implements Comparable<Ejercicio> {

	public enum GrupoMuscular {
		ABDOMINALES, BRAZOS, CARDIO, ESPALDA, HOMBROS, PECHO, PIERNAS 
	}

	public enum Nivel {
	    INICIAL, MEDIO, AVANZADO
	}

	private String nombre;
    private int duracion; // minutos
    private GrupoMuscular grupoMuscular;
    private Nivel nivel;

    public Ejercicio(String nombre, int duracion, GrupoMuscular grupoMuscular, Nivel nivel) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.grupoMuscular = grupoMuscular;
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public GrupoMuscular getGrupoMuscular() {
        return grupoMuscular;
    }

    public Nivel getNivel() {
        return nivel;
    }

    @Override
    public String toString() {
        return String.format("['%s' (%d min., %s, %s)]", nombre, duracion, grupoMuscular, nivel);
    }
    
    @Override
	public int hashCode() {
		return Objects.hash(duracion, grupoMuscular, nivel, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ejercicio other = (Ejercicio) obj;
		return duracion == other.duracion && grupoMuscular == other.grupoMuscular && nivel == other.nivel
				&& Objects.equals(nombre, other.nombre);
	}

    @Override
    public int compareTo(Ejercicio otro) {
        int comparacionNivel = this.nivel.compareTo(otro.nivel);

        if (comparacionNivel != 0) {
            return comparacionNivel;
        }
        
        if (this.nombre.compareTo(otro.nombre) != 0) {
            return this.nombre.compareTo(otro.nombre);
        }
        
        return Integer.compare(this.duracion, otro.duracion);
    }    
}