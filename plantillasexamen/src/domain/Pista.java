package domain;

import java.util.Objects;

public class Pista implements Comparable<Pista> {
	private int id;
	private String nombre;
	private Dificultad dificultad;
	private boolean abierta;
	private double longitud; // En kilómetros

	public Pista(int id, String nombre, Dificultad dificultad, boolean abierta, double longitud) {
		this.id = id;
		this.nombre = nombre;
		this.dificultad = dificultad;
		this.abierta = abierta;
		this.longitud = longitud;
	}

	// Getters y Setters
	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Dificultad getDificultad() {
		return dificultad;
	}

	public boolean isAbierta() {
		return abierta;
	}

	public void setAbierta(boolean abierta) {
		this.abierta = abierta;
	}

	public double getLongitud() {
		return longitud;
	}

	@Override
	public String toString() {
		return String.format("%s (%s) - %.1f km", nombre, dificultad, longitud);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Pista pista = (Pista) o;
		return id == pista.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public int compareTo(Pista o) {
		return Integer.compare(this.id, o.id);
	}
}