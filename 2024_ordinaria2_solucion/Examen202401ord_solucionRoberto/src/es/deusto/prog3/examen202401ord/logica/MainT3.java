package es.deusto.prog3.examen202401ord.logica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class MainT3 {
	public static void main(String[] args) {
		String[] frasesPosibles = ChatNoGPT.frases;
		// TODO T3		
		generarCombinaciones(frasesPosibles, 6);		
	}

	// T3 Método recursivo
	
	public static void generarCombinaciones(String[] frases, int n) {
	
		long[] contadores = {0l, 0l};
		List<List<String>> result = new ArrayList<>();
		List<String> aux = new ArrayList<>();
		
		// Invocación al método recursivo
		generarCombinaciones(result, contadores, frases, n, aux);
				
		result.forEach(c -> System.out.println(c));
		
		System.out.format("Combinaciones totales: %d\n", contadores[0]);
		System.out.format("Combinaciones sin pregunta: %d\n", contadores[1]);		
	}	

	/**
	 * Genera las combinaciones de n frases a partir de un array de frases.
	 * @param result List<List<String>> con las combinaciones generadas
	 * @param contadores long[] con los contadores para el total y total sin repetidos y preguntas 
	 * @param frases String[] con las frases a combinar
	 * @param n número de elementos de cada combinación
	 * @param aux List<String> con una combinación temporal que se va creando poco a poco
	 */
	public static void generarCombinaciones(List<List<String>> result, long[] contadores, String[] frases, int n, List<String> aux) {
		
		// Caso base: se han añadido 5 frases
		if (aux.size() == n) {
			result.add(new ArrayList<>(aux));
			
			// Se incrementa el contador de combinaciones sin repetición
			contadores[1]++;

			// Se inicializa de nuevo la lista auxiliar
			aux = new ArrayList<>();			
		} else {			
			// Se procesan cada una de las frases
			for (String frase : frases) {
				// Se incrementa el total
				contadores[0]++;

				// Se añade la frase a la lista temporal
				aux.add(frase);
				
				// Si la combinación actual no contiene ya la frase
				if (combinacionSinPreguntaYSinFrasesRepetidas(new LinkedList<>(aux))) {				
					// Se reaiza la invocación recursiva
					generarCombinaciones(result, contadores, frases, n, aux);
				}
				
				// Se elimina la frase de la lista temporal
				aux.remove(frase);
			}			
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