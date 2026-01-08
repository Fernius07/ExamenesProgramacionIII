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
import javax.swing.border.Border;

import es.deusto.prog.examen.parcial.Actividad;
import es.deusto.prog.examen.parcial.Sesion;
import es.deusto.prog.examen.parcial.Sesion.DiaSemana;

public class HorarioGimnasioLayout extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private int HORA_INICIO = 9;
	private int HORA_FINAL = 14;
	private int NUM_HORAS = HORA_FINAL - HORA_INICIO;
	
	private List<Actividad> actividades;
	
	private JPanel[][] panelesActividad;

	public HorarioGimnasioLayout(List<Actividad> actividades) {
		this.actividades = actividades;
		
		this.setTitle("Horario Semanal de Actividades");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		colocarPaneles();
		colocarActividades();
	}
	
	private void colocarPaneles() {
		setLayout(new GridLayout(NUM_HORAS + 1, DiaSemana.values().length + 1, 5, 5));
		
		// colocar la cabecera
		// panel vacio
		add(new JPanel());
		
		// los días de la semana
		for (DiaSemana dia : DiaSemana.values()) {
			JLabel diaLabel = new JLabel(dia.toString());
			JPanel jPanel = new JPanel();
			jPanel.add(diaLabel);
			add(jPanel);
		}
		
		panelesActividad = new JPanel[NUM_HORAS][DiaSemana.values().length];
		for (int i = HORA_INICIO; i < HORA_FINAL; i++) {
			JLabel horaLabel = new JLabel(String.format("%d:00", i));
			JPanel jPanel = new JPanel();
			jPanel.add(horaLabel);
			add(jPanel);
			
			for (int j = 0; j < DiaSemana.values().length; j++) {
				JPanel panelActividad = new JPanel();
				Border border = BorderFactory.createLineBorder(Color.BLACK);
				panelActividad.setBorder(border);
				add(panelActividad);
				
				// guardar para luego
				panelesActividad[i - HORA_INICIO][j] = panelActividad;
			}
		}
	}
	
	private void colocarActividades() {
		for (Actividad actividad : actividades) {
			for (Sesion sesion : actividad.getSesiones()) {
				DiaSemana dia = sesion.getDia();
				int hora = sesion.getHoraInicio();
				
				int fila = hora - HORA_INICIO;
				int columna = dia.ordinal();
				
				JLabel actividadLabel = new JLabel(actividad.getTipo().name(), JLabel.CENTER);
				panelesActividad[fila][columna].setLayout(new BorderLayout());
				panelesActividad[fila][columna].add(actividadLabel, BorderLayout.CENTER);
				panelesActividad[fila][columna].setBackground(actividad.getTipo().getColor());
			}
		}
	}
}