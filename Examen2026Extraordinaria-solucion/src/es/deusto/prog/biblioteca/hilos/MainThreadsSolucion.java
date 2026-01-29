package es.deusto.prog.biblioteca.hilos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class MainThreadsSolucion extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final int FILAS = 5;  // 5 estantes
	private static final int COLUMNAS = 8;  // 8 secciones por estante
	
	private JPanel[][] paneles;
	private JLabel lblEstado;
	private JButton btnIniciar;
	private JButton btnPausar;
	private JButton btnDetener;
	
	// SOLUCIÓN: Lista de hilos para controlar la simulación
	private List<HiloIluminacion> hilos;
	private volatile boolean pausado = false;
	private volatile boolean detenido = false;
	
	public MainThreadsSolucion() {
		setLayout(new BorderLayout());
		
		hilos = new ArrayList<>();
		
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
		
		// SOLUCIÓN: Botón Iniciar
		btnIniciar.addActionListener(e -> {
			btnIniciar.setEnabled(false);
			btnPausar.setEnabled(true);
			btnDetener.setEnabled(true);
			lblEstado.setText("Sistema de iluminación activo");
			
			detenido = false;
			pausado = false;
			
			// Crear y arrancar un hilo para cada panel
			for (int i = 0; i < FILAS; i++) {
				for (int j = 0; j < COLUMNAS; j++) {
					HiloIluminacion hilo = new HiloIluminacion(i, j);
					hilos.add(hilo);
					hilo.start();
				}
			}
		});
		
		// SOLUCIÓN: Botón Pausar/Reanudar
		btnPausar.addActionListener(e -> {
			if (btnPausar.getText().equals("Pausar")) {
				pausado = true;
				btnPausar.setText("Reanudar");
				lblEstado.setText("Sistema pausado");
			} else {
				pausado = false;
				synchronized (this) {
					this.notifyAll();
				}
				btnPausar.setText("Pausar");
				lblEstado.setText("Sistema de iluminación activo");
			}
		});
		
		// SOLUCIÓN: Botón Detener
		btnDetener.addActionListener(e -> {
			detenido = true;
			pausado = false;
			
			// Despertar todos los hilos pausados para que puedan terminar
			synchronized (this) {
				this.notifyAll();
			}
			
			// Esperar a que todos los hilos terminen
			for (HiloIluminacion hilo : hilos) {
				try {
					hilo.join();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			
			hilos.clear();
			resetearInterfaz();
		});
		
		setTitle("Simulador de Iluminación - Biblioteca");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	// SOLUCIÓN: Clase interna para el hilo de iluminación
	private class HiloIluminacion extends Thread {
		private final int fila;
		private final int columna;
		
		public HiloIluminacion(int fila, int columna) {
			this.fila = fila;
			this.columna = columna;
		}
		
		@Override
		public void run() {
			while (!detenido) {
				// Verificar si está pausado
				synchronized (MainThreadsSolucion.this) {
					while (pausado && !detenido) {
						try {
							MainThreadsSolucion.this.wait();
						} catch (InterruptedException e) {
							return;
						}
					}
				}
				
				if (detenido) break;
				
				// Cambiar el color del panel
				SwingUtilities.invokeLater(() -> {
					Color colorActual = paneles[fila][columna].getBackground();
					if (colorActual.equals(Color.LIGHT_GRAY)) {
						paneles[fila][columna].setBackground(Color.YELLOW);
					} else {
						paneles[fila][columna].setBackground(Color.LIGHT_GRAY);
					}
				});
				
				// Esperar un tiempo aleatorio
				try {
					Thread.sleep(tiempoAleatorio());
				} catch (InterruptedException e) {
					return;
				}
			}
		}
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
		SwingUtilities.invokeLater(() -> new MainThreadsSolucion());
	}
}
