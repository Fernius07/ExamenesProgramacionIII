// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.swing.solucion;

import java.util.List;

import javax.swing.SwingUtilities;

import es.deusto.prog.examen.Actividad;
import es.deusto.prog.examen.jdbc.GestorBD;

public class MainJTableSolucion {
    public static void main(String[] args) {
    	GestorBD gestorBD = new GestorBD();
        List<Actividad> actividades = gestorBD.loadActividades();;

        SwingUtilities.invokeLater(() -> {
        	HorarioGimnasioJTableSolucion ventana = new HorarioGimnasioJTableSolucion(actividades);
            ventana.setVisible(true);
        });
    }
}