// IAG (ChatGPT 4o-mini)
// ADAPTADO: El código ha sido creado con con ChatGPT 4o-mini.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.recursividad.solucion;

import java.util.Arrays;
import java.util.List;

import es.deusto.prog.examen.Ejercicio;
import es.deusto.prog.examen.Ejercicio.GrupoMuscular;
import es.deusto.prog.examen.Ejercicio.Nivel;

public class MainRecursividad {

	//TODO: TAREA 4 - Recursividad: Implementa este método recursivo
	public static int sumarDuracionRec(List<Ejercicio> ejercicios, GrupoMuscular grupo, Nivel nivel) {
		return sumarRecursivo(ejercicios, grupo, nivel, 0);
	}
		
	private static int sumarRecursivo(List<Ejercicio> ejercicios2, GrupoMuscular grupo, Nivel nivel, int indice) {
		if (indice >= ejercicios2.size()) {
			return 0;
		}
		Ejercicio e = ejercicios2.get(indice);
		int suma = 0;
		
		if (e.getGrupoMuscular() == grupo && e.getNivel() == nivel) {
			suma = e.getDuracion();
		}
		
		return suma + sumarRecursivo(ejercicios2, grupo, nivel, indice + 1);
	}

	public static void main(String[] args) {
		System.out.format("Duración total Grupo: %s Nivel: %s -> %d\n", 
			GrupoMuscular.ESPALDA, Nivel.MEDIO, sumarDuracionRec(ejercicios, GrupoMuscular.ESPALDA, Nivel.MEDIO));

		System.out.format("Duración total Grupo: %s Nivel: %s -> %d\n", 
			GrupoMuscular.PIERNAS, Nivel.AVANZADO, sumarDuracionRec(ejercicios, GrupoMuscular.PIERNAS, Nivel.AVANZADO));

		System.out.format("Duración total Grupo: %s Nivel: %s -> %d\n", 
			GrupoMuscular.CARDIO, Nivel.INICIAL, sumarDuracionRec(ejercicios, GrupoMuscular.CARDIO, Nivel.INICIAL));
	}
	
	public static List<Ejercicio> ejercicios = Arrays.asList(
			new Ejercicio("Flexiones", 10, GrupoMuscular.PECHO, Nivel.INICIAL),
			new Ejercicio("Press Banca", 20, GrupoMuscular.PECHO, Nivel.MEDIO),
			new Ejercicio("Fondos en Paralelas", 15, GrupoMuscular.PECHO, Nivel.AVANZADO),
			new Ejercicio("Dominadas", 15, GrupoMuscular.ESPALDA, Nivel.MEDIO),
			new Ejercicio("Remo con Mancuernas", 10, GrupoMuscular.ESPALDA, Nivel.MEDIO),
			new Ejercicio("Peso Muerto", 25, GrupoMuscular.ESPALDA, Nivel.AVANZADO),
			new Ejercicio("Plancha", 5, GrupoMuscular.ABDOMINALES, Nivel.INICIAL),
			new Ejercicio("Crunch Abdominal", 10, GrupoMuscular.ABDOMINALES, Nivel.INICIAL),
			new Ejercicio("Elevaciones de Piernas", 10, GrupoMuscular.ABDOMINALES, Nivel.MEDIO),
			new Ejercicio("Plancha Lateral", 5, GrupoMuscular.ABDOMINALES, Nivel.MEDIO),
			new Ejercicio("Plancha con Movimiento", 15, GrupoMuscular.ABDOMINALES, Nivel.AVANZADO),
			new Ejercicio("Sentadillas", 20, GrupoMuscular.PIERNAS, Nivel.INICIAL),
			new Ejercicio("Zancadas", 15, GrupoMuscular.PIERNAS, Nivel.INICIAL),
			new Ejercicio("Prensa de Piernas", 25, GrupoMuscular.PIERNAS, Nivel.MEDIO),
			new Ejercicio("Peso Muerto Rumano", 20, GrupoMuscular.PIERNAS, Nivel.MEDIO),
			new Ejercicio("Sentadilla Frontal", 25, GrupoMuscular.PIERNAS, Nivel.AVANZADO),
			new Ejercicio("Curl Bíceps", 10, GrupoMuscular.BRAZOS, Nivel.INICIAL),
			new Ejercicio("Curl Martillo", 10, GrupoMuscular.BRAZOS, Nivel.INICIAL),
			new Ejercicio("Extensión de Tríceps", 15, GrupoMuscular.BRAZOS, Nivel.MEDIO),
			new Ejercicio("Dominadas Supinas", 20, GrupoMuscular.BRAZOS, Nivel.MEDIO),
			new Ejercicio("Press Francés", 15, GrupoMuscular.BRAZOS, Nivel.AVANZADO),
			new Ejercicio("Press Militar", 15, GrupoMuscular.HOMBROS, Nivel.INICIAL),
			new Ejercicio("Elevaciones Frontales", 10, GrupoMuscular.HOMBROS, Nivel.INICIAL),
			new Ejercicio("Elevaciones Laterales", 10, GrupoMuscular.HOMBROS, Nivel.MEDIO),
			new Ejercicio("Press Arnold", 20, GrupoMuscular.HOMBROS, Nivel.MEDIO),
			new Ejercicio("Remo al Mentón", 15, GrupoMuscular.HOMBROS, Nivel.AVANZADO),
			new Ejercicio("Sprint en Cinta", 5, GrupoMuscular.CARDIO, Nivel.INICIAL),
			new Ejercicio("Correr al Aire Libre", 30, GrupoMuscular.CARDIO, Nivel.MEDIO),
			new Ejercicio("Bicicleta Estática", 20, GrupoMuscular.CARDIO, Nivel.INICIAL),
			new Ejercicio("Remo Ergómetro", 15, GrupoMuscular.CARDIO, Nivel.MEDIO),
			new Ejercicio("Burpees", 10, GrupoMuscular.CARDIO, Nivel.AVANZADO),
			new Ejercicio("Estiramiento de Hombros", 5, GrupoMuscular.HOMBROS, Nivel.INICIAL),
			new Ejercicio("Estiramiento de Espalda", 10, GrupoMuscular.ESPALDA, Nivel.INICIAL),
			new Ejercicio("Estiramiento de Piernas", 10, GrupoMuscular.PIERNAS, Nivel.INICIAL),
			new Ejercicio("Estiramiento de Tríceps", 5, GrupoMuscular.BRAZOS, Nivel.INICIAL),
			new Ejercicio("Estiramiento Lumbar", 5, GrupoMuscular.ESPALDA, Nivel.MEDIO),
			new Ejercicio("Flexiones Diamante", 15, GrupoMuscular.PECHO, Nivel.AVANZADO),
			new Ejercicio("Flexiones Pliométricas", 15, GrupoMuscular.PECHO, Nivel.AVANZADO),
			new Ejercicio("Dominadas Asistidas", 10, GrupoMuscular.ESPALDA, Nivel.INICIAL),
			new Ejercicio("Kettlebell Swings", 15, GrupoMuscular.CARDIO, Nivel.MEDIO),
			new Ejercicio("Step-Ups", 20, GrupoMuscular.PIERNAS, Nivel.INICIAL),
			new Ejercicio("Saltos al Cajón", 15, GrupoMuscular.PIERNAS, Nivel.MEDIO),
			new Ejercicio("Remo con Barra", 20, GrupoMuscular.ESPALDA, Nivel.AVANZADO),
			new Ejercicio("Press Inclinado", 20, GrupoMuscular.PECHO, Nivel.MEDIO),
			new Ejercicio("Mountain Climbers", 10, GrupoMuscular.CARDIO, Nivel.INICIAL),
			new Ejercicio("Press de Hombros en Máquina", 20, GrupoMuscular.HOMBROS, Nivel.INICIAL),
			new Ejercicio("Escalada en Cinta", 10, GrupoMuscular.CARDIO, Nivel.MEDIO),
			new Ejercicio("Puente de Glúteos", 10, GrupoMuscular.PIERNAS, Nivel.INICIAL),
			new Ejercicio("Hip Thrust", 20, GrupoMuscular.PIERNAS, Nivel.MEDIO),
			new Ejercicio("Curl de Femoral", 15, GrupoMuscular.PIERNAS, Nivel.MEDIO));
}