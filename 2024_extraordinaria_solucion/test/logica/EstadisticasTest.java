package logica;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import datos.BDException;
import datos.GestorBD;
import datos.LeerExamenes;
import dominio.Estudiante;
import dominio.Examen;
import logica.Estadisticas.DiferenciaExamenes;

public class EstadisticasTest {
	
	private Estadisticas estadisticas;
	private Map<Integer, List<Examen>> examenes;
	private List<Estudiante> estudiantes;

	@Before
	public void setUp() throws IOException, BDException {
		estadisticas = new Estadisticas();
		
		String[] ficheros = {"src/2020-11-10.csv", "src/2021-01-14.csv" };
		examenes = LeerExamenes.leerFicheros(ficheros, 145315);
		
		GestorBD gestorBD = new GestorBD();
		gestorBD.conectar("competencias.bd");
		gestorBD.crearTablas();
		gestorBD.insertarDatosPrueba();
		
		estudiantes = gestorBD.obtenerEstudiantes(145315);
	}
	
	@Test
	public void testObtenerNotasMedias() {
		Map<Integer, List<Float>> notasMedias = estadisticas.obtenerNotasMedias(examenes);
		assertEquals(13, notasMedias.size());
		
		assertEquals(3.7f, notasMedias.get(11110).get(0), 0.001f);
		assertEquals(5.5f, notasMedias.get(11110).get(1), 0.001f);
		
		assertEquals(12001, new ArrayList<Integer>(notasMedias.keySet()).get(0).intValue());
		assertEquals(10120, new ArrayList<Integer>(notasMedias.keySet()).get(12).intValue());
	}
	
	@Test
	public void testObtenerDiferenciaExamenes() {
		List<DiferenciaExamenes> diferencias = estadisticas.obtenerDiferenciaExamenes(examenes, estudiantes);
		assertEquals(13, diferencias.size());
		
		assertEquals(10615, diferencias.get(0).getEstudiante().getNIA());
		assertEquals(10219, diferencias.get(1).getEstudiante().getNIA());
	}
}
