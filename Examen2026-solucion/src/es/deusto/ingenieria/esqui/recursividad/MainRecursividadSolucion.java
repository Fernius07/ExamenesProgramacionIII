package es.deusto.ingenieria.esqui.recursividad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.deusto.ingenieria.esqui.domino.Ciudad.Pais;
import es.deusto.ingenieria.esqui.jdbc.GestorBD;
import es.deusto.ingenieria.esqui.domino.Itinerario;

public class MainRecursividadSolucion {

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
		
		int result = generarViajes(itinerarios, ciudadOrigen, maxIntinerarios, new HashMap<>(), new ArrayList<>());		
		
		System.out.printf("---------------------------------------------------------------------------%n");
		System.out.printf("- Se han encontrado %d viajes.%n", result);
	}
	
	/**
	 * Método recursivo para generar viajes.
	 * @param itinerarios Lis<Itinerario> con todos los itinerarios disponibles.
	 * @param ciudadOrigen Sring con el nombre de la ciudad de origen del viaje.
	 * @param maxIntinerarios int con el número máximo de itinerarios permitidos en un viaje.
	 * @param paisesVisitados HashMap<Pais, Integer> con los países visitados y el número ciudades visitadas en cada país.
	 * @param viajeActual List<Itinerario> con el viaje actual en construcción.
	 * @return int con el número de viajes encontrados que cumplen las condiciones.
	 */
	public static int generarViajes(List<Itinerario> itinerarios, 
			 						 String ciudadOrigen,
			 						 int maxIntinerarios,
			 						 HashMap<Pais, Integer> paisesVisitados,		
			 						 List<Itinerario> viajeActual) {
		// CASO BASE 1: El viaje actual cumple todas las condiciones
		if (paisesVisitados.keySet().size() == 3 && 								// Se han visitado los 3 países
			viajeActual.getLast().getDestino().getNombre().equals(ciudadOrigen) && 	// El viaje termina en la ciudad de origen
			viajeActual.size() <= maxIntinerarios) {								// No se supera el número máximo de itinerarios
	
			// Se muestra el viaje actual
			viajeActual.forEach(itinerario -> System.out.printf("%s, ", itinerario));
			System.out.println();
			
			// Se devuelve 1 porque se ha encontrado un viaje válido
			return 1;
		// CASO BASE 2: Se supera el número de itinerarios o la duración máxima
		} else if (viajeActual.size() > maxIntinerarios) {
			// Se devuelve 0 porque no se ha encontrado un viaje válido
			return 0;
		// CASO RECURSIVO
		} else {
			// Contador de resultados
			int numResultados = 0;
			
			// Se recorren todos los itinerarios disponibles
			for (Itinerario i : itinerarios) {
				// Se comprueba que el itinerario no se esté en el viaje actual
				if (!viajeActual.contains(i) ) {
					// Se obtiene la ciudad inicio del siguiente itinerario a añadir 
					String ciudadInicio = (viajeActual.size() == 0) ? ciudadOrigen : viajeActual.getLast().getDestino().getNombre();
					// Si ya se ha añadido algún itinerario, la ciudadInicio no puede ser ciudadOrigen
					ciudadInicio = (viajeActual.size() > 0 && ciudadInicio.equals(ciudadOrigen)) ? "" : ciudadInicio;
				
					// La ciudad origen del itinerario actual es la ciudad inicio calculada
					if (i.getOrigen().getNombre().equals(ciudadInicio)) {
						// Se añade el itinerario al viaje actual
						viajeActual.add(i);
						
						// Se incrementa el contador del país vistado
						Pais paisVisitado = i.getDestino().getPais();
						paisesVisitados.putIfAbsent(paisVisitado, 0);
						paisesVisitados.put(paisVisitado, paisesVisitados.get(paisVisitado) + 1);
						
						// Llamada recursiva
						numResultados += generarViajes(itinerarios, ciudadOrigen, maxIntinerarios, paisesVisitados, viajeActual);
						
						// Se elimina el itinerario del viaje actual (backtracking)
						viajeActual.remove(i);
						
						// Se ajusta el contador de país visitado (backtracking)
						paisesVisitados.put(paisVisitado, paisesVisitados.get(paisVisitado) - 1);
						
						if (paisesVisitados.get(paisVisitado) == 0) {
							paisesVisitados.remove(paisVisitado);
						}
					}
				}
			}
			
			return numResultados;
		}
	}
}