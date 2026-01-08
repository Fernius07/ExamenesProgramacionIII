// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.parcial.layout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import es.deusto.prog.examen.parcial.Actividad;
import es.deusto.prog.examen.parcial.Sesion;
import es.deusto.prog.examen.parcial.Sesion.DiaSemana;

public class MainLayout {
    public static void main(String[] args) {
        List<Actividad> actividades = inicializarActividades();
        
        SwingUtilities.invokeLater(() -> {
            HorarioGimnasioLayout ventana = new HorarioGimnasioLayout(actividades);
            ventana.setVisible(true);
        });
    }

    private static List<Actividad> inicializarActividades() {
        List<Actividad> actividades = new ArrayList<>();

        // Yoga - Lunes y Miércoles a las 9:00
        Actividad yoga = new Actividad(Actividad.TipoActividad.YOGA, 15);
        yoga.addSesion(new Sesion(yoga, DiaSemana.LUNES, 9));
        yoga.addSesion(new Sesion(yoga, DiaSemana.MIERCOLES, 9));
        actividades.add(yoga);

        // Pilates - Martes y Jueves a las 10:00
        Actividad pilates = new Actividad(Actividad.TipoActividad.PILATES, 12);
        pilates.addSesion(new Sesion(pilates, DiaSemana.MARTES, 10));
        pilates.addSesion(new Sesion(pilates, DiaSemana.JUEVES, 10));
        actividades.add(pilates);

        // Zumba - Martes y Jueves a las 11:00
        Actividad zumba = new Actividad(Actividad.TipoActividad.ZUMBA, 25);
        zumba.addSesion(new Sesion(zumba, DiaSemana.MARTES, 11));
        zumba.addSesion(new Sesion(zumba, DiaSemana.JUEVES, 11));
        actividades.add(zumba);

        // Boxeo - Lunes y Viernes a las 12:00
        Actividad boxeo = new Actividad(Actividad.TipoActividad.BOXEO, 10);
        boxeo.addSesion(new Sesion(boxeo, DiaSemana.LUNES, 12));
        boxeo.addSesion(new Sesion(boxeo, DiaSemana.VIERNES, 12));
        actividades.add(boxeo);
        
        // Spinning - Lunes, Miércoles y Viernes a las 13:00
        Actividad spinning = new Actividad(Actividad.TipoActividad.SPINNING, 20);
        spinning.addSesion(new Sesion(spinning, DiaSemana.LUNES, 13));
        spinning.addSesion(new Sesion(spinning, DiaSemana.MIERCOLES, 13));
        spinning.addSesion(new Sesion(spinning, DiaSemana.VIERNES, 13));
        actividades.add(spinning);

        return actividades;
    }
}