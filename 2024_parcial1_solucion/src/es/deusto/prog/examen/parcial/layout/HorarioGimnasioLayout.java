// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.parcial.layout;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;

import es.deusto.prog.examen.parcial.Actividad;

public class HorarioGimnasioLayout extends JFrame {
	private static final long serialVersionUID = 1L;
	private List<Actividad> actividades;

	public HorarioGimnasioLayout(List<Actividad> actividades) {
		this.actividades = actividades;

		//TODO: TAREA 1: Configurar el layaout del horario de actividades
		this.add(new JLabel("Añade aquí el horario de actividades", JLabel.CENTER));
		
		this.setTitle("Horario Semanal de Actividades");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
}