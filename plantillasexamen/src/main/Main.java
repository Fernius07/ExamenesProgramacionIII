package main;

import db.GestorBD;
import domain.Dificultad;
import domain.Pista;
import gui.VentanaResort;
import logic.RecursividadSki;

import javax.swing.SwingUtilities;
import java.util.*;

public class Main {

	private static final int NUM_PISTAS = 150;

	public static void main(String[] args) {
		System.out.println(">>> INICIO DEL EXAMEN: SISTEMA RESORT DE ESQUÍ <<<");

		// ----------------------------------------------------------------------------------
		// PREPARACIÓN DE DATOS (No es tarea del examen, pero necesario para que
		// funcione)
		// ----------------------------------------------------------------------------------
		System.out.println("\n[INIT] Inicializando Base de Datos...");
		GestorBD gestor = new GestorBD();

		// Limpiamos la BD y generamos 150 pistas nuevas para tener datos de prueba
		// fiables
		gestor.borrarTodasLasPistas();
		List<Pista> datosAleatorios = generarPistasAleatorias(NUM_PISTAS);
		gestor.insertarPistasMasivas(datosAleatorios); // Usa Batch Update
		System.out.println("[INIT] Se han generado e insertado " + NUM_PISTAS + " pistas aleatorias.");

		// ----------------------------------------------------------------------------------
		// TAREA 2: JDBC (Mapas y Transacciones)
		// ----------------------------------------------------------------------------------
		System.out.println("\n=== TAREA 2: JDBC ===");

		// 2.1. Carga de datos agrupados en Mapa <Enum, List>
		System.out.println("1. Cargando pistas agrupadas por dificultad...");
		Map<Dificultad, List<Pista>> mapaPistas = gestor.cargarPistasPorDificultad();

		// Verificación por consola
		for (Dificultad d : mapaPistas.keySet()) {
			System.out.format("   - Dificultad %s: %d pistas cargadas.\n", d, mapaPistas.get(d).size());
		}

		// 2.2. Prueba de Transacción (Update + Insert Log)
		int idAleatorio = new Random().nextInt(NUM_PISTAS) + 1;
		System.out.println("2. Probando transacción con pista aleatoria (ID: " + idAleatorio + ")...");
		boolean exitoTransaccion = gestor.cerrarPistaConLog(idAleatorio); // Cerramos una pista aleatoria entre 1 y 150
		if (exitoTransaccion) {
			System.out.println("   -> ÉXITO: La pista " + idAleatorio + " se ha cerrado y se ha guardado el LOG.");
		} else {
			System.out.println("   -> FALLO: No se pudo cerrar la pista (¿Quizás el ID no existe?).");
		}

		// Recargamos el mapa para que la GUI refleje el cambio de estado de la pista 1
		mapaPistas = gestor.cargarPistasPorDificultad();

		// ----------------------------------------------------------------------------------
		// TAREA 3: RECURSIVIDAD (Combinatoria Aleatoria)
		// ----------------------------------------------------------------------------------
		System.out.println("\n=== TAREA 3: RECURSIVIDAD ===");

		// Aplanamos la lista para la recursividad
		List<Pista> todasLasPistas = new ArrayList<>();
		mapaPistas.values().forEach(todasLasPistas::addAll);

		// REDUCCIÓN DE DATOS PARA RECURSIVIDAD:
		// Seleccionamos solo las primeras 25 pistas para evitar que se bloquee por
		// exceso de combinaciones
		// (150 pistas en backtracking es inviable en tiempo real)
		List<Pista> pistasParaRecursividad = todasLasPistas.subList(0, Math.min(25, todasLasPistas.size()));
		System.out.println("   (Usando subconjunto de 25 pistas para cálculo rápido)");

		// Generamos un objetivo aleatorio razonable (entre 5.0 y 15.0 km)
		double objetivoAleatorio = 7.1 ;
		objetivoAleatorio = Math.round(objetivoAleatorio * 10.0) / 10.0; // Redondear a 1 decimal

		System.out.format("Buscando combinaciones que sumen exactamente %.1f km...\n", objetivoAleatorio);

		RecursividadSki.buscarRutas(pistasParaRecursividad, objetivoAleatorio);

		// ----------------------------------------------------------------------------------
		// TAREA 1 (Swing) y TAREA 4 (Hilos)
		// ----------------------------------------------------------------------------------
		System.out.println("\n=== TAREA 1 (Swing) y TAREA 4 (Hilos) ===");
		System.out.println("Lanzando interfaz gráfica...");

		final Map<Dificultad, List<Pista>> datosParaGUI = mapaPistas;

		SwingUtilities.invokeLater(() -> {
			VentanaResort ventana = new VentanaResort(datosParaGUI);
			ventana.setVisible(true);
			System.out.println("   -> Ventana visible.");
		});
	}

	/**
	 * Generador de pistas con datos aleatorios
	 */
	private static List<Pista> generarPistasAleatorias(int cantidad) {
		List<Pista> lista = new ArrayList<>();
		Random rand = new Random();

		String[] nombres = { "Águila", "Lobo", "Oso", "Zorro", "Marmota", "Rebeco", "Halcón", "Sarrio", "Ciervo",
				"Liebre", "Lince" };
		String[] ubicaciones = { "Norte", "Sur", "Este", "Oeste", "Alta", "Baja", "Panorámica", "Bosque", "Valle",
				"Cima" };

		for (int i = 1; i <= cantidad; i++) {
			String nombre = "Pista " + nombres[rand.nextInt(nombres.length)] + " "
					+ ubicaciones[rand.nextInt(ubicaciones.length)] + " " + i;

			Dificultad dif = Dificultad.values()[rand.nextInt(Dificultad.values().length)];

			// Longitud aleatoria (0.5 - 5.0 km)
			double longitud = 0.5 + (4.5 * rand.nextDouble());
			longitud = Math.round(longitud * 10.0) / 10.0;

			boolean abierta = rand.nextDouble() < 0.9; // 90% abiertas

			lista.add(new Pista(i, nombre, dif, abierta, longitud));
		}
		return lista;
	}
}