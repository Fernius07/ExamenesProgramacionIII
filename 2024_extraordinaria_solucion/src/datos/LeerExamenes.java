package datos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dominio.Examen;

public class LeerExamenes {

	private static Map<Integer, List<Examen>> leerFichero(String fichero, int asignatura) throws IOException {
		Map<Integer, List<Examen>> examenes = new HashMap<>();
		
		// leer datos examen parcial de Programación III 
		try (BufferedReader reader = new BufferedReader(new FileReader(fichero))) {
			String nombreFichero = Paths.get(fichero).getFileName().toString().split("\\.")[0];
			DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-d");
			LocalDate fechaExamen = LocalDate.parse(nombreFichero, formateador);
			
			String linea = null;
			while ((linea = reader.readLine()) != null) {
				String[] partes = linea.split(",");
				int nia = Integer.parseInt(partes[0]);
		
				List<Float> resultados = new ArrayList<>();
				for (int i = 1; i < partes.length; i++) {
					resultados.add(Float.parseFloat(partes[i]));
				}
				
				Examen examen = new Examen(
					fechaExamen, 
					nia,
					asignatura,
					resultados
				);
				examenes.putIfAbsent(nia, new ArrayList<>());
				examenes.get(nia).add(examen);
			}
		}
		
		return examenes;
	}
	
	public static Map<Integer, List<Examen>> leerFicheros(String[] ficheros, int asignatura) throws IOException {
		Map<Integer, List<Examen>> mapaCombinado = new HashMap<Integer, List<Examen>>();
		
		for (String fichero : ficheros) {
			Map<Integer, List<Examen>> mapaExamenes = leerFichero(fichero, asignatura);
			for (Entry<Integer, List<Examen>> e : mapaExamenes.entrySet()) {
				mapaCombinado.putIfAbsent(e.getKey(), new ArrayList<>());
				mapaCombinado.get(e.getKey()).addAll(e.getValue());
			}
		}
		
		return mapaCombinado;
	}
}
