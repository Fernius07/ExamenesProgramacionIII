package es.deusto.prog3.examen202401ord.logica;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class ChatNoGPTTest {

	@Test
	public void testDevuelveRespuesta() {
		ChatNoGPT chat = new ChatNoGPT(); 
		Random random = new Random();
		List<String> listaRespuestas = new ArrayList<>( Arrays.asList( ChatNoGPT.frases ) );
		Set<String> conjRespuestas = new HashSet<>();
		// Prueba 100 respuestas a frases aleatorias
		for (int i=0; i<100; i++) {
			String frase = "Frase aleatoria " + random.nextDouble();
			String respuesta = chat.devuelveRespuesta( frase );
			assertTrue( listaRespuestas.contains( respuesta ) ); // Las respuestas dadas deben estar entre las opciones posibles de respuesta
			conjRespuestas.add( respuesta );
		}
		assertTrue( conjRespuestas.size() > 10 );  // Suponemos que si es suficientemente aleatorio hay más de 10 respuestas distintas entre 100 posibles
	}

}
