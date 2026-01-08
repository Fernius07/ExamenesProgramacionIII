package es.deusto.prog3.examen.layout;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import es.deusto.prog3.examen.domain.Emparejamiento;
import es.deusto.prog3.examen.domain.Jugador;
import es.deusto.prog3.examen.domain.Jugador.Pais;

public class MainLayout {
	
	private static List<Jugador> jugadores;
	private static Map<LocalDate, List<Emparejamiento>> emparejamientos;

	public static void main(String[] args) {
		jugadores = new ArrayList<Jugador>();
		jugadores.add(new Jugador("Magnus", "Carlsen", 2839, "resources/images/profiles/magnus_carlsen.png", Pais.NORUEGA));
		jugadores.add(new Jugador("Hikaru", "Nakamura", 2789, "resources/images/profiles/hikaru_nakamura.jpg", Pais.EEUU));
		jugadores.add(new Jugador("Fabiano", "Caruana", 2789, "resources/images/profiles/fabiano_caruana.jpg", Pais.EEUU));
		jugadores.add(new Jugador("Arjun", "Erigaisi", 2839, "resources/images/profiles/arjun_erigaisi.jpg", Pais.INDIA));
		jugadores.add(new Jugador("Praggnanandhaa", "Rameshabu", 2771, "resources/images/profiles/praggnanandhaa_rameshabu.jpg", Pais.INDIA));
		jugadores.add(new Jugador("Alireza", "Firouzja", 2762, "resources/images/profiles/alireza_firouzja.jpg", Pais.NORUEGA));
		jugadores.add(new Jugador("Anish", "Giri", 2759, "resources/images/profiles/anish_giri.jpeg", Pais.PAISES_BAJOS));
		jugadores.add(new Jugador("Wesley", "So", 2756, "resources/images/profiles/wesley_so.png", Pais.NORUEGA));
		jugadores.add(new Jugador("Vincent", "Keymer", 2755, "resources/images/profiles/vincent_keymer.jpg", Pais.ALEMANIA));
		jugadores.add(new Jugador("Wei", "Yi", 2754, "resources/images/profiles/wei_yi.jpeg", Pais.CHINA));		
		
		emparejamientos = new LinkedHashMap<>();
		List<Emparejamiento> dia1 = new ArrayList<>();
		dia1.add(new Emparejamiento(LocalTime.of(9, 0), jugadores.get(0), jugadores.get(5)));
		dia1.add(new Emparejamiento(LocalTime.of(10, 0), jugadores.get(1), jugadores.get(6)));
		dia1.add(new Emparejamiento(LocalTime.of(11, 0), jugadores.get(2), jugadores.get(7)));
		emparejamientos.put(LocalDate.of(2025, 11, 3), dia1);
		
		List<Emparejamiento> dia2 = new ArrayList<>();
		dia2.add(new Emparejamiento(LocalTime.of(9, 0), jugadores.get(3), jugadores.get(8)));
		dia2.add(new Emparejamiento(LocalTime.of(10, 0), jugadores.get(4), jugadores.get(9)));
		emparejamientos.put(LocalDate.of(2025, 11, 4), dia2);
	
		SwingUtilities.invokeLater(() -> new VentanaEmparejamientos(emparejamientos));
	}

}
