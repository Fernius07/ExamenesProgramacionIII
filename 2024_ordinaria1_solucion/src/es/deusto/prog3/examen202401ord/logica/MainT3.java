package es.deusto.prog3.examen202401ord.logica;

import java.util.HashSet;
import java.util.LinkedList;

public class MainT3 {
	public static void main(String[] args) {
		String[] frasesPosibles = ChatNoGPT.frases;
		// TODO T3
		long[] contadores = new long[2];
		compruebaPosibilidades( frasesPosibles, 4, contadores, new LinkedList<String>() );
		System.out.println( "Combinaciones totales: " + contadores[0] );
		System.out.println( "Combinaciones sin pregunta: " + contadores[1] );
	}
	
	// T3 Método recursivo
	
	/**
	 * @param frases	Frases a combinar
	 * @param numPasos	Número de pasos de combinación
	 * @param contadores	Contador 0 = número total de frases. Contador 1 = número total de frases...
	 */
	private static void compruebaPosibilidades( String[] frases, int numPasos, long[] contadores, LinkedList<String> combinacion ) {
		if (numPasos==0) {  // Caso base
			contadores[0]++;
			// System.out.println( combinacion );
			if (combinacionSinPreguntaYSinFrasesRepetidas(combinacion)) {
				contadores[1]++;
				System.out.println( combinacion );
			}
			return;
		}
		for (String frase : frases) {
			combinacion.addLast( frase );
			if (combinacionSinPreguntaYSinFrasesRepetidas(combinacion)) { // Poda - puntuación extra
				compruebaPosibilidades( frases, numPasos-1, contadores, combinacion );
			}
			combinacion.removeLast();
		}
	}
	
	// Informa si la combinación de frases no tiene repetidas y no tiene preguntas interrogativas explícitas
	private static boolean combinacionSinPreguntaYSinFrasesRepetidas( LinkedList<String> combi ) {
		HashSet<String> frases = new HashSet<>();
		for (String frase : combi) {
			if (frase.contains("?")) {
				return false;
			}
			if (frases.contains(frase)) {
				return false;
			}
			frases.add( frase );
		}
		return true;
	}
	
}
