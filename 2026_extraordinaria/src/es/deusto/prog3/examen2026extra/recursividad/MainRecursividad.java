package es.deusto.prog3.examen2026extra.recursividad;

import java.util.Arrays;
import java.util.List;

import es.deusto.prog3.examen2026extra.Actividad;
import es.deusto.prog3.examen2026extra.Actividad.Intensidad;
import es.deusto.prog3.examen2026extra.Actividad.Tipo;

public class MainRecursividad {

    // TODO Tarea 1: Implementar recursividad sin bucles
    public static int sumarMinutosRec(List<Actividad> acts, Tipo tipo, Intensidad intensidad) {
        return sumarMinutosRec(acts, tipo, intensidad, 0);
    }

    private static int sumarMinutosRec(List<Actividad> acts, Tipo tipo, Intensidad intensidad, int index) {
        // TODO Tarea 1: caso base + caso recursivo
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("CARDIO-MEDIA = " + sumarMinutosRec(actividades, Tipo.CARDIO, Intensidad.MEDIA));
        System.out.println("FUERZA-ALTA = " + sumarMinutosRec(actividades, Tipo.FUERZA, Intensidad.ALTA));
        System.out.println("MOVILIDAD-BAJA = " + sumarMinutosRec(actividades, Tipo.MOVILIDAD, Intensidad.BAJA));
    }

    private static final List<Actividad> actividades = Arrays.asList(
        new Actividad("Correr", 30, Tipo.CARDIO, Intensidad.MEDIA),
        new Actividad("Saltar comba", 15, Tipo.CARDIO, Intensidad.ALTA),
        new Actividad("Bici suave", 25, Tipo.CARDIO, Intensidad.BAJA),
        new Actividad("Sentadillas", 20, Tipo.FUERZA, Intensidad.MEDIA),
        new Actividad("Peso muerto", 18, Tipo.FUERZA, Intensidad.ALTA),
        new Actividad("Press banca", 22, Tipo.FUERZA, Intensidad.ALTA),
        new Actividad("Estiramientos", 12, Tipo.MOVILIDAD, Intensidad.BAJA),
        new Actividad("Yoga", 30, Tipo.MOVILIDAD, Intensidad.MEDIA),
        new Actividad("Movilidad cadera", 10, Tipo.MOVILIDAD, Intensidad.BAJA)
    );
}
