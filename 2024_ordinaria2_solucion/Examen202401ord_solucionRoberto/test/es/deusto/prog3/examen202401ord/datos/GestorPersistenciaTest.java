package es.deusto.prog3.examen202401ord.datos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import es.deusto.prog3.examen202401ord.main.Main;

public class GestorPersistenciaTest {	
	
	@Test
	public void testCargaConversaciones() {
		// TODO T2
		String dbName = "test.db";
		String nick = "a";
		String nombreChat = Main.NOMBRE_CHAT;
		
		// Se crea el Gestor de persistencia y se inicializa
		GestorPersistencia bd = new GestorPersistencia();
		bd.init(dbName);

		if (!bd.existeBD(dbName)) {
        	bd.crearTablasBD( null, true );
    	}

		// Se recuperan las conversaciones
		ArrayList<Conversacion> conversaciones = bd.cargaConversaciones(nick);
		ArrayList<Frase> frases = null;
		
		// Para cada conversación
		for (int i=0; i<conversaciones.size()-1; i++) {
			// Comprobar que el usuario de la conversación es el usuario “a”
			assertEquals(conversaciones.get(i).getNickUsuario(), nick);
			
			// A partir del primer elemento
			if (i > 0) {
				// Comprobar que el id es posterior al id de la anterior conversación
				assertTrue(conversaciones.get(i).getId() > conversaciones.get(i-1).getId());
				// Comprobar que la fecha es posterior a la fecha de la anterior conversación
				assertTrue(conversaciones.get(i).getFecha() > conversaciones.get(i-1).getFecha());
				
				frases = conversaciones.get(i).getFrases();
				
				// Para cada frase dentro de cada conversación
				for (int j=0; j<frases.size()-1; j++) {
					// Comprobar que el idConv de cada frase coincide con el id de la conversación
					assertEquals(conversaciones.get(j).getId(), frases.get(j-1).getIdConv());

					// Comprobar que el primer emisor de cada conversación es el usuario “a”,
					// y que se irán alternando “a” con, frase a frase.					
					if (j%2 == 0) {
						assertEquals(frases.get(j).getEmisor(), nick);
					} else {
						assertEquals(frases.get(j).getEmisor(), nombreChat);
					}

					// A partir del primer elemento
					if (j > 0) {
						// Comprobar que el id es posterior al id de la anterior frase.
						assertTrue(frases.get(j).getId() > frases.get(j-1).getId());
						//Comprobar que la fecha al menos 100 msg posterior a la fecha de la anterior frase.
						assertTrue(frases.get(j).getFecha() > frases.get(j-1).getFecha() + 100);
					}
				}
			}
		}
	}
}
