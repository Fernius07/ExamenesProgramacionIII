package es.deusto.prog3.examen.domain;

public class Jugador {
	
	public enum Pais {
		NORUEGA,
		EEUU,
		INDIA,
		FRANCIA,
		PAISES_BAJOS,
		ALEMANIA,
		CHINA
	}

	private String nombre;
	private String apellido;
	private int puntuacion;
	private String perfil;
	private Pais pais;
	
	public Jugador(String nombre, String apellido, int puntuacion, String perfil, Pais pais) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.puntuacion = puntuacion;
		this.perfil = perfil;
		this.pais = pais;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getApellido() {
		return apellido;
	}

	public int getPuntuacion() {
		return puntuacion;
	}

	public String getPerfil() {
		return perfil;
	}
	
	public Pais getPais() {
		return pais;
	}
}
