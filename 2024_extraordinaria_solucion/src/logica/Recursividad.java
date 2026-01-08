package logica;

import java.util.ArrayList;
import java.util.List;

// T3. Tarea recursividad
public class Recursividad {
	
	////
	private static float calcularMedia(List<Float> medias) {
		float media = 0.0f;
		for (float m : medias) {
			media += m;
		}
		return media / medias.size();
	}
	
	public static void generarGrupos(List<Integer> estudiantes, List<Float> medias, List<List<Integer>> grupos, int n) {
		//T3. Llama aquí a tu método recursivo
		generarGruposRec(estudiantes, medias, grupos, n, new ArrayList<Integer>(), new ArrayList<Float>());
	}

	//repartir los estudiantes en grupos
	private static void generarGruposRec(List<Integer> estudiantes, List<Float> medias, List<List<Integer>> grupos, int n, List<Integer> grupo, List<Float> mediasGrupo) {
		if (grupo.size() == n) {
			if (calcularMedia(mediasGrupo) >= 5.0f) {
				grupos.add(new ArrayList<>(grupo));
			}
            return;
		}
            
        for (int i = 0; i < estudiantes.size(); i++) {				
			grupo.add(estudiantes.get(i));
			mediasGrupo.add(medias.get(i));
			generarGruposRec(estudiantes.subList(i + 1, estudiantes.size()), medias.subList(i + 1, medias.size()), grupos, n, grupo, mediasGrupo);
			
			grupo.remove(grupo.size() - 1);
			mediasGrupo.remove(mediasGrupo.size() - 1);
		}
	}
}
