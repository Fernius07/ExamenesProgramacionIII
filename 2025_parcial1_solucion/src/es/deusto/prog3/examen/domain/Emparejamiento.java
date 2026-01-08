package es.deusto.prog3.examen.domain;

import java.time.LocalTime;

public class Emparejamiento {

	private LocalTime hora;
	private Jugador jugador1, jugador2;
	
	public Emparejamiento(LocalTime hora, Jugador jugador1, Jugador jugador2) {
		this.hora = hora;
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
	}

	public LocalTime getHora() {
		return hora;
	}

	public Jugador getJugador1() {
		return jugador1;
	}

	public Jugador getJugador2() {
		return jugador2;
	}
}
