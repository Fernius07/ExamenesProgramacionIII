// IAG (ChatGPT 4o-mini)
// ADAPTADO: El código ha sido creado con con ChatGPT 4o-mini.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.recursividad;

import java.util.Arrays;
import java.util.List;

import es.deusto.prog.examen.Ejercicio;
import es.deusto.prog.examen.Ejercicio.GrupoMuscular;
import es.deusto.prog.examen.Ejercicio.Nivel;

public class GeneradorRutinas {

	//TODO: TAREA 2: Recursividad.
	// El objetivo de esta tarea es la generación de rutinas de entrenamiento.
	// Una rutina de entrenamiento tiene una duración específica y se compone 
	// de un conjunto de ejercicios (sin repetición) correspondientes a uno o 
	// varios grupos musculares y de un nivel de dominio. A partir de una lista 
	// de ejercicios, debes generar rutinas de entrenamiento mediante un proceso 
	// recursivo. Para lograrlo, tienes que implementar el método generarRutinas(). 
	// Este método recibe una lista de ejercicios, una duración en minutos, un 
	// nivel y una lista de grupos musculares. A partir de esa información, debes
	// generar todas las rutinas posibles (combinaciones de ejercicios) que cumplan
	// lo siguiente:
	// - La duración total de la rutina debe ser igual a la duración indicada.
	// - La rutina debe incluir sólo ejercicios del nivel indicado.
	// - La rutina debe incluir únicamente ejercicios de los grupos musculares 
	//   indicados (pudiendo ser los ejercicios sólo de uno, más de uno, o todos
	//   los grupos musculares indicados).
	// - La rutina no puede incluir ejercicios repetidos.
	// - No puede haber rutinas equivalentes (es decir, los mismos ejercicios en
	//   distinto orden).
	public static void generarRutinas(List<Ejercicio> ejercicios, int duracion, Nivel nivel, List<GrupoMuscular> gruposMusculares) {

	}
		
	public static void main(String[] args) {
		System.out.println("Sesión 1: 20 minutos, nivel MAEDIO, grupos musculares PECHO, ESPALDA y ABDOMINALES");
		generarRutinas(ejercicios, 20, Nivel.MEDIO, Arrays.asList(GrupoMuscular.PECHO, GrupoMuscular.ESPALDA, GrupoMuscular.ABDOMINALES));		
		
		System.out.println("\nSesión 2: 60 minutos, nivel INICIAL, grupos musculares CARDIO y PIERNAS");
		generarRutinas(ejercicios, 60, Nivel.INICIAL, Arrays.asList(GrupoMuscular.CARDIO, GrupoMuscular.PIERNAS));		
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