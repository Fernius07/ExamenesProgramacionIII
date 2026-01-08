package es.deusto.prog3.examen202401ord.datos;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import es.deusto.prog3.examen202401ord.main.Main;

public class GestorPersistenciaTest {

	@Test
	public void testCargaConversaciones() {
		// TODO T2
		GestorPersistencia gp = new GestorPersistencia();
		gp.init( "test" );
		gp.crearTablasBD( null, true );
		String USUARIO = "a";
		ArrayList<Conversacion> lc = gp.cargaConversaciones( USUARIO );
		int idConvAnterior = -1;
		long fechaConvAnterior = -1;
		for (Conversacion conv : lc) {
			assertEquals( USUARIO, conv.getNickUsuario() );
			assertTrue( idConvAnterior < conv.getId() );
			assertTrue( fechaConvAnterior < conv.getFecha() );
			
			int idAnterior = -1;
			long fechaAnterior = -1;
			String emisor = USUARIO;
			for (Frase frase : conv.getFrases()) {
				assertEquals( frase.getEmisor(), emisor );
				assertEquals( conv.getId(), frase.getIdConv() );
				if (emisor.equals(USUARIO)) {
					emisor = Main.NOMBRE_CHAT;
				} else {
					emisor = USUARIO;
				}
				assertTrue( idAnterior < frase.getId() );
				assertTrue( fechaAnterior + 100 <= frase.getFecha() );
				idAnterior = frase.getId();
				fechaAnterior = frase.getFecha();
			}
			idConvAnterior = conv.getId();
			fechaAnterior = conv.getFecha();
		}
	}
	
}
