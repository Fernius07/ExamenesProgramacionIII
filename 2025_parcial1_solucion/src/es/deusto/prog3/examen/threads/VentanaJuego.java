package es.deusto.prog3.examen.threads;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.deusto.prog3.examen.domain.PiezaAjedrez;

public class VentanaJuego extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private int TABLERO_ANCHO = 12;
	private int TABLERO_ALTO = 12;
	
	private int TIEMPO_JUEGO = 15; // segundos
	private int TIEMPO_PEON = 1000; // milisegundos
	
	private JButton[] botones;
	private JButton iniciarJuego;
	private JLabel labelTiempo;
	
	private Thread threadTiempo, threadPeon;
	
	private ImageIcon imgPeon;
	private boolean peonCazado;

	public VentanaJuego() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Caza el peón");
		setSize(600, 600);
		setLocationRelativeTo(null);
		
		JPanel panelPrincipal = new JPanel();
		add(panelPrincipal, BorderLayout.CENTER);

		botones = new JButton[TABLERO_ANCHO * TABLERO_ALTO];
		panelPrincipal.setLayout(new GridLayout(TABLERO_ANCHO, TABLERO_ALTO));
		for (int i = 0; i < TABLERO_ANCHO * TABLERO_ALTO; i++) {
			botones[i] = new JButton();
			panelPrincipal.add(botones[i]);
			
			botones[i].addActionListener(e -> {
				clickBoton((JButton) e.getSource());
			});
		}
		
		JPanel panelInferior = new JPanel();
		add(panelInferior, BorderLayout.SOUTH);
		
		iniciarJuego = new JButton("Iniciar");
		panelInferior.add(iniciarJuego);
		
		iniciarJuego.addActionListener(e -> {
			iniciarJuego.setEnabled(false);
			peonCazado = false;
			
			// TAREA 1.5 Implementa aquí el código necesario
			iniciarTemporizador(TIEMPO_JUEGO);
			iniciarPeon();
		});
		
		labelTiempo = new JLabel();
		labelTiempo.setHorizontalAlignment(JLabel.CENTER);
		add(labelTiempo, BorderLayout.NORTH);
		actualizarTiempo(TIEMPO_JUEGO);
		
		imgPeon = new ImageIcon(PiezaAjedrez.PEON_BLANCO.getImagen());
		
		setVisible(true);	
	}
	
	// método llamado cuando se hace click 
	private void clickBoton(JButton boton) {
		if (boton.getIcon() != null) {
			peonCazado = true;
			
			// TAREA 1.5 Implementa aquí el código necesario para detener el juego
			threadPeon.interrupt();
			threadTiempo.interrupt();
			
			mostrarEnhorabuena();
			iniciarJuego.setEnabled(true);
		}
	}
	
	private void iniciarPeon() {
		threadPeon = new Thread(() -> {
			Random random = new Random();
			int celda = -1;
			while (!Thread.currentThread().isInterrupted()) {
				if (celda != -1) {
					final int celdaAnterior = celda;
					SwingUtilities.invokeLater(() -> botones[celdaAnterior].setIcon(null));
				}
				celda = random.nextInt(TABLERO_ALTO * TABLERO_ANCHO);
				
				final int celdaNueva = celda;
				SwingUtilities.invokeLater(() -> botones[celdaNueva].setIcon(imgPeon));
				try {
					Thread.sleep(TIEMPO_PEON);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
			
			botones[celda].setIcon(null);
		});
		
		threadPeon.start();
	}
	
	private void iniciarTemporizador(int tiempoInicial) {
		threadTiempo = new Thread(() -> {
			int contador = tiempoInicial;
	        try {
	            while (contador > 0 && !Thread.currentThread().isInterrupted()) {
	                final int valor = contador;
	                SwingUtilities.invokeLater(() -> actualizarTiempo(valor));

	                Thread.sleep(1000);
	                contador--;
	            }
	            
	            SwingUtilities.invokeLater(() -> actualizarTiempo(0));
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	       
	        threadPeon.interrupt();
	        
	        if (!peonCazado) {
	        	mostrarFinJuego();
	        }
	        
	        iniciarJuego.setEnabled(true);
		});
		
		threadTiempo.start();
	}
	
	// actualiza el label a partir del tiempo en segundos actual
	private void actualizarTiempo(int tiempoSegundos) {
		int minutos = (tiempoSegundos % 3600) / 60;
		int segundos = tiempoSegundos % 60;
		
		LocalTime tiempo = LocalTime.of(0, minutos, segundos);
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");
		labelTiempo.setText("Tiempo restante: " + formato.format(tiempo));
	}
	
	// mostrar mensaje de fin de juego
	private void mostrarFinJuego() {
		JOptionPane.showMessageDialog(VentanaJuego.this, "¡El tiempo se ha terminado!", "Fin del juego", JOptionPane.OK_OPTION);
	}
	
	private void mostrarEnhorabuena() {
		JOptionPane.showMessageDialog(VentanaJuego.this, "¡Enhorabuena! Has conseguido cazar el péon", "¡Bien hecho!", JOptionPane.OK_OPTION);
	}
}
