package es.deusto.prog3.examen202401ord.datos;

import java.util.ArrayList;

/** Clase para gestión de objetos de conversación de ChatNoGPT
 * @author prog3 @ ingenieria.deusto.es
 */
public class Conversacion {
	private int id;
	private String nickUsuario;
	private long fecha;
	private ArrayList<Frase> frases;

	/** Construye un objeto conversación sin datos
	 */
	public Conversacion() {
		frases = new ArrayList<>();
	}
	
	/** Construye un objeto conversación
	 * @param usuario	Usuario que realiza la conversación
	 * @param fecha	Fecha de inicio de la conversación
	 */
	public Conversacion(String usuario, long fecha) {
		super();
		this.nickUsuario = usuario;
		this.fecha = fecha;
		frases = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickUsuario() {
		return nickUsuario;
	}

	public void setNickUsuario(String nickUsuario) {
		this.nickUsuario = nickUsuario;
	}

	public long getFecha() {
		return fecha;
	}

	public void setFecha(long fecha) {
		this.fecha = fecha;
	}

	public ArrayList<Frase> getFrases() {
		return frases;
	}
	
	public void setFrases(ArrayList<Frase> frases) {
		if (frases != null) {
			this.frases = frases;
		}
	}

	@Override
	public String toString() {
		return "Conversacion [id=" + id + ", usuario=" + nickUsuario + ", fecha=" + fecha + ", frases=" + frases + "]";
	}
	
}
