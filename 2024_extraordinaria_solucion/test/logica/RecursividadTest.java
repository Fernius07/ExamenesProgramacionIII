package logica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class RecursividadTest {

	@Test
	public void setUp() {
		List<Integer> estudiantes = Arrays.asList(12001, 12002, 12003, 12004, 12005, 12006, 12007, 12008, 12009);
		List<Float> medias = Arrays.asList(2.5f, 5.1f, 7f, 2f, 10f, 5f, 4.8f, 7.3f, 8.4f);
		
		List<List<Integer>> grupos = new ArrayList<List<Integer>>();
		Recursividad.generarGrupos(estudiantes, medias, grupos, 3);
		
		assertEquals(58, grupos.size());
		
		assertTrue(grupos.contains(Arrays.asList(12001, 12002, 12005)));
		assertTrue(grupos.contains(Arrays.asList(12002, 12003, 12005)));
		assertTrue(grupos.contains(Arrays.asList(12006, 12007, 12008)));
		assertTrue(grupos.contains(Arrays.asList(12007, 12008, 12009)));
	}
}
