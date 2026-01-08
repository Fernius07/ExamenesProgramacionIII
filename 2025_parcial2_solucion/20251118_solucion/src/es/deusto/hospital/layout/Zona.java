package es.deusto.hospital.layout;

import java.awt.Color;

public class Zona {
	private final String nombre;
	private final int planta;
	private final Color color;

	public Zona(String nombre, int planta, Color color) {
		this.nombre = nombre;
		this.planta = planta;
		this.color = color;
	}

	public String getNombre() {
		return nombre;
	}

	public int getPlanta() {
		return planta;
	}

	public Color getColor() {
		return color;
	}
}