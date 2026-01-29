package es.deusto.prog.biblioteca.hilos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class MainThreads extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final int FILAS = 5;  // 5 estantes
	private static final int COLUMNAS = 8;  // 8 secciones por estante
	
	private JPanel[][] paneles;
	private JLabel lblEstado;
	private JButton btnIniciar;
	private JButton btnPausar;
	private JButton btnDetener;
	
	public MainThreads() {
		setLayout(new BorderLayout());
		
		// Panel superior con el estado
		lblEstado = new JLabel("Sistema de Iluminación de Biblioteca", JLabel.CENTER);
		lblEstado.setFont(lblEstado.getFont().deriveFont(Font.BOLD, 16f));
		lblEstado.setForeground(Color.WHITE);
		lblEstado.setBackground(new Color(51, 102, 153));
		lblEstado.setOpaque(true);
		lblEstado.setPreferredSize(new Dimension(0, 40));
		add(lblEstado, BorderLayout.NORTH);
		
		// Panel central con los estantes (grid de paneles)
		JPanel panelEstantes = new JPanel();
		panelEstantes.setLayout(new GridLayout(FILAS, COLUMNAS, 3, 3));
		panelEstantes.setBackground(Color.DARK_GRAY);
		panelEstantes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		paneles = new JPanel[FILAS][COLUMNAS];
		for (int i = 0; i < FILAS; i++) {
			for (int j = 0; j < COLUMNAS; j++) {
				JPanel panel = new JPanel();
				panel.setBackground(Color.LIGHT_GRAY);
				panel.setOpaque(true);
				Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
				panel.setBorder(border);
				
				panelEstantes.add(panel);
				paneles[i][j] = panel;
			}
		}
		
		add(panelEstantes, BorderLayout.CENTER);
		
		// Panel inferior con los botones
		JPanel panelBotones = new JPanel(new FlowLayout());
		panelBotones.setBackground(new Color(240, 240, 240));
		
		btnIniciar = new JButton("Iniciar Sistema");
		btnPausar = new JButton("Pausar");
		btnDetener = new JButton("Detener");
		
		btnPausar.setEnabled(false);
		btnDetener.setEnabled(false);
		
		panelBotones.add(btnIniciar);
		panelBotones.add(btnPausar);
		panelBotones.add(btnDetener);
		
		add(panelBotones, BorderLayout.SOUTH);
		
		// TODO TAREA 3 - HILOS
		// El objetivo de esta tarea es simular un sistema de iluminación automática
		// en una biblioteca. Cada sección de cada estante (representada por un panel)
		// debe encenderse (color AMARILLO) y apagarse (color LIGHT_GRAY) de forma
		// independiente y aleatoria, simulando sensores de movimiento.
		//
		// REQUISITOS:
		// 1. Cada panel debe tener su propio hilo que controle su iluminación
		// 2. Cada hilo debe encender/apagar su panel con intervalos aleatorios
		//    entre 800 y 2000 milisegundos
		// 3. El botón "Iniciar Sistema" debe arrancar todos los hilos
		// 4. El botón "Pausar/Reanudar" debe pausar o reanudar todos los hilos
		// 5. El botón "Detener" debe detener todos los hilos y resetear la interfaz
		// 6. Actualiza el lblEstado para mostrar mensajes apropiados
		
		// Botón Iniciar
		btnIniciar.addActionListener(e -> {
			// INCORPORA AQUÍ TU CÓDIGO PARA INICIAR LA SIMULACIÓN
			btnIniciar.setEnabled(false);
			btnPausar.setEnabled(true);
			btnDetener.setEnabled(true);
			lblEstado.setText("Sistema de iluminación activo");
		});
		
		// Botón Pausar/Reanudar
		btnPausar.addActionListener(e -> {
			// INCORPORA AQUÍ TU CÓDIGO PARA PAUSAR Y REANUDAR LA SIMULACIÓN
			if (btnPausar.getText().equals("Pausar")) {
				btnPausar.setText("Reanudar");
				lblEstado.setText("Sistema pausado");
			} else {
				btnPausar.setText("Pausar");
				lblEstado.setText("Sistema de iluminación activo");
			}
		});
		
		// Botón Detener
		btnDetener.addActionListener(e -> {
			// INCORPORA AQUÍ TU CÓDIGO PARA DETENER LA SIMULACIÓN
			resetearInterfaz();
		});
		
		setTitle("Simulador de Iluminación - Biblioteca");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	// Método para resetear la interfaz al estado inicial
	private void resetearInterfaz() {
		SwingUtilities.invokeLater(() -> {
			for (int i = 0; i < FILAS; i++) {
				for (int j = 0; j < COLUMNAS; j++) {
					paneles[i][j].setBackground(Color.LIGHT_GRAY);
				}
			}
			btnIniciar.setEnabled(true);
			btnPausar.setEnabled(false);
			btnPausar.setText("Pausar");
			btnDetener.setEnabled(false);
			lblEstado.setText("Sistema de Iluminación de Biblioteca");
		});
	}
	
	// Método auxiliar para obtener un tiempo aleatorio entre 800 y 2000 ms
	private int tiempoAleatorio() {
		return (int) (Math.random() * 1200) + 800;
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new MainThreads());
	}
}
