package datos;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import dominio.Examen;

// T2. Implementa el test unitario con las comprobaciones indicadas
public class LeerExamenesTest {
	
	@Test
	public void testLeerFicheros() throws IOException {
		// leer los datos desde los ficheros
		String[] ficheros = {"src/2020-11-10.csv", "src/2021-01-14.csv" };
		Map<Integer, List<Examen>> mapaExamenes = LeerExamenes.leerFicheros(ficheros, 145315);
		
		// comprobar que se han leído datos para 13 estudiantes distintos
		assertEquals(13, mapaExamenes.size());
		
		// comprobar que todos los nia están entre 10000 y 20000 incluídos
		for (Integer nia : mapaExamenes.keySet()) {
			assertTrue(nia >= 10000 && nia <= 20000);
		}
		
		// comprobar que el código de la asignatura es 145315 en todos los examenes realizados
		// y que se han realizado 2 examenes por cada estudiante
		// comprobar que todas las calificaciones de las competencias son entre 0 y 10
		for (List<Examen> examenes : mapaExamenes.values()) {
			assertEquals(2, examenes.size());
			for (Examen examen : examenes) {
				assertEquals(145315, examen.getAsignatura());
				
				for (Float calificacion : examen.getResultados()) {
					assertTrue(calificacion >= 0 && calificacion <= 10);
				}
			}
		}
				
		// comprobar que todos los usuarios se han examinado en el primer examen de dos
		// competencias y el segundo de 5 competencias
		for (List<Examen> examenes : mapaExamenes.values()) {
			assertEquals(2, examenes.get(0).getResultados().size());
			assertEquals(5, examenes.get(1).getResultados().size());
		}
	}
	
	@Test(expected=IOException.class)
	public void testLeerFicheroNoExiste() throws IOException {
		String[] ficheros = { "no-existe.csv" };
		LeerExamenes.leerFicheros(ficheros, 145315);
	}
}
