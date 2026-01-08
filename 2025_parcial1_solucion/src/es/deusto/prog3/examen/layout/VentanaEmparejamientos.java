package es.deusto.prog3.examen.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.deusto.prog3.examen.domain.Emparejamiento;
import es.deusto.prog3.examen.domain.Jugador;

public class VentanaEmparejamientos extends JFrame {
		
	private static final long serialVersionUID = 1L;
	
	private int MAX_EMPAREJAMIENTOS = 3;
	
	public VentanaEmparejamientos(Map<LocalDate, List<Emparejamiento>> emparejamientos) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Emparejamientos Campeonato");
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		DefaultListModel<LocalDate> modeloLista = new DefaultListModel<>();	
		for (LocalDate dia : emparejamientos.keySet()) {
			modeloLista.addElement(dia);
		}
		
		JList<LocalDate> jList = new JList<>(modeloLista);
		add(jList, BorderLayout.WEST);
		
		jList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					List<Emparejamiento> emparejamientosDia = emparejamientos.get(jList.getSelectedValue());
					mostrarEmparejamientos(emparejamientosDia);
					revalidate();
				}
			}
		});
		
		setVisible(true);
	}
	
	private void mostrarEmparejamientos(List<Emparejamiento> emparejamientosDia) {
		// TAREA 1.1 Implementa aquí la solución
		
		// añadir los paneles de emparejamientos (maxímo 4 en un día)
		JPanel panelPrincipal = new JPanel(new GridLayout(MAX_EMPAREJAMIENTOS, 1));
		for (int i = 0; i < emparejamientosDia.size(); i++) {
			Emparejamiento emparejamiento = emparejamientosDia.get(i);
			JPanel panelEmparejamiento = crearPanelEmparejamiento(emparejamiento, emparejamiento.getHora());
			panelPrincipal.add(panelEmparejamiento);
		}
		
		// añadimos paneles vacíos para completar el grid hasta el máximo de un día
		for (int i = 0; i < MAX_EMPAREJAMIENTOS - emparejamientosDia.size(); i++) {
			JPanel panelVacio = new JPanel();
			panelPrincipal.add(panelVacio);
			
			panelVacio.setBackground(Color.WHITE);
			panelVacio.setOpaque(true);
		}
		
		add(panelPrincipal, BorderLayout.CENTER);
	}
	
	// crea el panel de un emparejamiento (dos jugadores en un panel horizontal)
	private JPanel crearPanelEmparejamiento(Emparejamiento emparejamiento, LocalTime hora) {
		JPanel panelEmparejamiento = new JPanel(new GridLayout(1, 2));
		panelEmparejamiento.add(crearPanelJugador(emparejamiento.getJugador1(), hora));
		panelEmparejamiento.add(crearPanelJugador(emparejamiento.getJugador2(), hora));
		return panelEmparejamiento;
	}

	// crea el panel para un jugador
	private JPanel crearPanelJugador(Jugador jugador, LocalTime hora) {	
		JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setBorder(new EmptyBorder(12, 12, 12, 12));
        panel.setBackground(Color.WHITE);

        JPanel panelDatos = new JPanel(new GridLayout(4, 1, 6, 6));
        panelDatos.setOpaque(false);

        panelDatos.add(crearPanelDato("Nombre: ", jugador.getNombre()));
        panelDatos.add(crearPanelDato("Apellido: ", jugador.getApellido()));
        panelDatos.add(crearPanelDato("Puntuación: ", Integer.toString(jugador.getPuntuacion())));
        panelDatos.add(crearPanelDato("País: ", jugador.getPais().toString()));

        panel.add(panelDatos, BorderLayout.CENTER);

        JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panelImagen.setOpaque(false);

        Dimension dimension = new Dimension(60, 60);
        JLabel labelFoto = new JLabel(cargarImagen(jugador.getPerfil(), dimension));
        labelFoto.setPreferredSize(dimension);

        panelImagen.add(labelFoto);
        panel.add(panelImagen, BorderLayout.EAST);

        JLabel titulo = new JLabel(String.format("Enfrentamiento (%s)", hora));
        titulo.setOpaque(true);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 16f));
        JPanel cabecera = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        cabecera.add(titulo);
        panel.add(cabecera, BorderLayout.NORTH);

        return panel;
	}
	
	private static JPanel crearPanelDato(String etiqueta, String valor) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        p.setOpaque(false);

        JLabel etiquetaLabel = new JLabel(etiqueta);
        etiquetaLabel.setFont(etiquetaLabel.getFont().deriveFont(Font.BOLD));
        JLabel valorLabel = new JLabel(valor);

        p.add(etiquetaLabel);
        p.add(valorLabel);
        return p;
    }
	
	private ImageIcon cargarImagen(String imagen, Dimension dimension) {
		ImageIcon imageIcon = new ImageIcon(imagen);
		Image img = imageIcon.getImage();
		Image newimg = img.getScaledInstance((int) dimension.getWidth(), (int) dimension.getHeight(),  java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}
}
