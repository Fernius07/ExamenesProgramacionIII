// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.parcial.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.deusto.prog.examen.parcial.Actividad;
import es.deusto.prog.examen.parcial.Sesion;
import es.deusto.prog.examen.parcial.Sesion.DiaSemana;

public class HorarioGimnasioLayoutSolucion extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final int HORA_INICIO = 9;
	private static final int HORA_FIN = 14;
	private static final int HORAS_TOTALES = HORA_FIN - HORA_INICIO;
	private List<Actividad> actividades;

	// Matriz de paneles para facilitar la configuración.
	// Alternativamente puede ser una matriz de JLabel.
	private JPanel[][] panelesActividades;

	public HorarioGimnasioLayoutSolucion(List<Actividad> actividades) {
		this.actividades = actividades;

		// TODO: TAREA 1: Configurar el layaout del horario de actividades
		this.inicializarMatrizPaneles();
		this.colocarActividades();

		this.setTitle("Horario Semanal de Actividades");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}

	private void inicializarMatrizPaneles() {
		// Panel principal con GridLayout
		JPanel mainPanel = new JPanel(new GridLayout(HORAS_TOTALES + 1, DiaSemana.values().length, 5, 5));

		// Añadir días de la semana
		mainPanel.add(new JLabel(""));
		for (DiaSemana dia : DiaSemana.values()) {
			mainPanel.add(new JLabel(dia.toString(), JLabel.CENTER));
		}

		// Crear matriz de paneles para las actividades
		panelesActividades = new JPanel[HORAS_TOTALES][DiaSemana.values().length];

		// Crear y añadir paneles con las horas y huecos para las actividades
		for (int hora = 0; hora < HORAS_TOTALES; hora++) {
			// Añadir label de hora
			mainPanel.add(new JLabel(String.format("%02d:00", (HORA_INICIO + hora)), JLabel.CENTER));

			// Crear paneles vacíos para cada día/hora
			for (int dia = 0; dia < DiaSemana.values().length; dia++) {
				panelesActividades[hora][dia] = new JPanel(new BorderLayout());
				panelesActividades[hora][dia].setBorder(BorderFactory.createLineBorder(Color.GRAY));
				mainPanel.add(panelesActividades[hora][dia]);
			}
		}

		add(mainPanel, BorderLayout.CENTER);
	}

	private void colocarActividades() {
		// Recorrer las actividades
		for (Actividad actividad : actividades) {
			// Recorrer las sesiones de cada actividad
			for (Sesion sesion : actividad.getSesiones()) {
				// Obtener la ubicación de la sesión en el panel de actividades
				int hora = sesion.getHoraInicio() - HORA_INICIO;
				int dia = sesion.getDia().ordinal();

				if (hora >= 0 && hora < HORAS_TOTALES) {
					// Recuperar el panel correspondiente a la sesión y ajustar el color
					JPanel panel = panelesActividades[hora][dia];
					panel.setBackground(actividad.getTipo().getColor());

					// Añadir label con el nombre de la actividad
					JLabel actividadLabel = new JLabel(actividad.getTipo().toString(), JLabel.CENTER);
					actividadLabel.setForeground(Color.BLACK);
					panel.add(actividadLabel, BorderLayout.CENTER);
				}
			}
		}
	}
}