package logica;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import dominio.Estudiante;
import dominio.Examen;

// T5. Implementa la funcionalidad de cada uno de los métodos
public class Estadisticas {
	
	/**
	 * Clase que representa la diferencia entre los resultados
	 * de las competencias del primer y segundo examen para un estudiante
	 */
	public class DiferenciaExamenes {
		
		private Estudiante estudiante;
		private List<Float> diferencias;
		
		public DiferenciaExamenes(Estudiante estudiante, List<Float> diferencias) {
            this.estudiante = estudiante;
            this.diferencias = diferencias;
        }	
		
		public Estudiante getEstudiante() {
			return estudiante;
		}
		
		public List<Float> getDiferencias() {
			return diferencias;
		}
		
		@Override
		public String toString() {
			return String.format("%d: %.1f, %1f", estudiante.getNIA(), diferencias.get(0), diferencias.get(1));
		}
	}
	
	 //	T5.1. 
	 public Map<Integer, List<Float>> obtenerNotasMedias(Map<Integer, List<Examen>> examenesAlumnos) {
		Map<Integer, List<Float>> notasMedias = new TreeMap<Integer, List<Float>>(Comparator.reverseOrder());
		for (Entry<Integer, List<Examen>> e : examenesAlumnos.entrySet()) {
			notasMedias.putIfAbsent(e.getKey(), new ArrayList<Float>());
			
			for (Examen examen : e.getValue()) {
				float media = 0;
				for (Float r : examen.getResultados()) {
				    media += r;
				}
				media /= examen.getResultados().size();
    			notasMedias.get(e.getKey()).add(media);
            }
		}
		
		return notasMedias;
	 }
	 
	 // T5.2
	 public List<DiferenciaExamenes> obtenerDiferenciaExamenes(Map<Integer, List<Examen>> examenesAlumnos, List<Estudiante> estudiantes) {
		 Map<Integer, Estudiante> mapaEstudiantes = new HashMap<Integer, Estudiante>();
		 for (Estudiante estudiante : estudiantes) {
			 mapaEstudiantes.put(estudiante.getNIA(), estudiante);
		 }
		 
		 List<DiferenciaExamenes> diferenciasExamenes = new ArrayList<DiferenciaExamenes>();
		 for (Entry<Integer, List<Examen>> e : examenesAlumnos.entrySet()) {
			 List<Float> diferencias = new ArrayList<Float>();
			 diferencias.add(e.getValue().get(1).getResultados().get(0) - e.getValue().get(0).getResultados().get(0));
			 diferencias.add(e.getValue().get(1).getResultados().get(1) - e.getValue().get(0).getResultados().get(1));
			 DiferenciaExamenes diferencia = new DiferenciaExamenes(mapaEstudiantes.get(e.getKey()), diferencias);
			 diferenciasExamenes.add(diferencia);
		 }
		 
		 diferenciasExamenes.sort(new ComparadorDiferencias());
		 return diferenciasExamenes;
	 }
	 
	// comparador para ordenar la lista de resultados por diferencias
	class ComparadorDiferencias implements Comparator<DiferenciaExamenes> {
		
		@Override
		public int compare(DiferenciaExamenes d1, DiferenciaExamenes d2) {
			int comparacion = Float.compare(d2.getDiferencias().get(0), d1.getDiferencias().get(0));
			if (comparacion != 0) {
				return comparacion;
			}
			
			return d1.getEstudiante().getApellido().compareTo(d2.getEstudiante().getApellido());
		}
	}
}
