// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.swing;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import es.deusto.prog.examen.Actividad;
import es.deusto.prog.examen.Persona;
import es.deusto.prog.examen.jdbc.GestorBD;

public class MainJTable {
    public static void main(String[] args) {
    	GestorBD gestorBD = new GestorBD();
        List<Actividad> actividades = gestorBD.loadActividades();;
        List<Persona> personas = new ArrayList<>(actividades.stream().map(Actividad::getPersonas).flatMap(List::stream).collect(Collectors.toSet()));

        SwingUtilities.invokeLater(() -> {
            new TablaPersonas(personas);
        });
    }
}