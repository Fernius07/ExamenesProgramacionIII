package es.deusto.ingenieria.esqui.recursividad;

import java.util.List;

import es.deusto.ingenieria.esqui.domino.Itinerario;
import es.deusto.ingenieria.esqui.jdbc.GestorBD;

public class MainRecursividad {

	public static void main(String[] args) {
		// Se obtienen los itinerarios desde la base de datos
		GestorBD gestorBD = new GestorBD();		
		List<Itinerario> itinerarios = gestorBD.getItinerarios(gestorBD.getCiudades());

		// Casos de prueba
		generarViajes(itinerarios, "Jaca", 3);			// 24 viajes
		generarViajes(itinerarios, "La Massana", 3);	// 48 viajes		
		generarViajes(itinerarios, "Saint-Lary", 3);	// 16 viajes
	}
	
	// TODO TAREA 2: RECURSIVIDAD
	// El objetivo de esta tarea es la generación de viajes de ida y vuelta tomando 
	// como referencia una estación de esquí. Un viaje es una sucesión encadenada 
	// de itinerarios. Los viajes deben cumplir estas restricciones:
	// - El viaje comienza y termina en una ciudad, y esa ciudad no puede repetirse en medio del viaje.
	// - El viaje debe pasar por los 3 países disponibles (Andorra, España y Francia).
	// - No se pueden repetir itinerarios en un mismo viaje.
	// - El número máximo de itinerarios de un viaje debe ser menor o igual que un número máximo.
	public static void generarViajes(List<Itinerario> itinerarios, 
									 String ciudadOrigen,
									 int maxIntinerarios) {		
		System.out.printf("%n---------------------------------------------------------------------------%n");
		System.out.printf("GENERANDO VIAJES DESDE '%s' CON UN MÁXIMO DE %d ITINERARIOS%n", ciudadOrigen, maxIntinerarios);
		System.out.printf("---------------------------------------------------------------------------%n");

		// INCPORPORA AQUÍ TU CÓDIGO PARA IMPLEMENTAR EL PROCESO RECURSIVO
		
	}
}