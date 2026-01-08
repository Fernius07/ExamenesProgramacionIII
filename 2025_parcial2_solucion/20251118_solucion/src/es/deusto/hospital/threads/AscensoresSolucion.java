package es.deusto.hospital.threads;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class AscensoresSolucion extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int NUM_ASCENSORES = 4;

	// Botones de control global de la simulación
	private JButton btnIniciar;
	private JButton btnParar;
	
	// Controles e indicadores por cada ascensor
	private JLabel[] lblPlantaActual = new JLabel[NUM_ASCENSORES];
	private JLabel[] lblDestino = new JLabel[NUM_ASCENSORES];
	private JLabel[] lblEstado = new JLabel[NUM_ASCENSORES];
	private JButton[] btnDetener = new JButton[NUM_ASCENSORES];
	
	// Plantas del edificio
	protected static int[] PLANTAS = {-1, -2, 0, 1, 2, 3, 4, 5, 6};

	// Estados posibles del ascensor
	public enum EstadoAscensor {
		PARADO(new Color(221, 226, 229)),
		EN_MOVIMIENTO(new Color(139, 214, 116)),
		EN_PLANTA(new Color(255, 228, 138)),
		FUERA_DE_SERVICIO(new Color(242, 139, 130));
		
		private Color color;
		
		private EstadoAscensor(Color color) {
			this.color = color;
		}
		
		public Color getColor() {
			return color;
		}
	}
	
	// TAREA 3 - Hilos: Añade la lógica para la simulación del funcionamiento de los ascensores
	
	// Hilos de los ascensores
	private HiloAscensor[] hilosAscensores = new HiloAscensor[NUM_ASCENSORES];

	public AscensoresSolucion() {
		setLayout(new BorderLayout());

		// Configuración de los paneles de los ascensores
		JPanel panelAscensores = new JPanel(new GridLayout(1, NUM_ASCENSORES, 10, 0));

		for (int i = 0; i < NUM_ASCENSORES; i++) {
			panelAscensores.add(crearPanelAscensor(i));
		}

		add(panelAscensores, BorderLayout.CENTER);

		// Configuración del botón "Iniciar simulación"
		btnIniciar = new JButton("Iniciar simulación");
		btnIniciar.setEnabled(true);
		btnIniciar.addActionListener(e -> {			
			
			// TAREA-2: Introduce aquí el código necesario para iniciar la simulación
			
			btnIniciar.setEnabled(false);
			btnParar.setEnabled(true);
			
			for (int i = 0; i < NUM_ASCENSORES; i++) {
				cambiarEstadoAscensor(i, EstadoAscensor.PARADO);
				cambairPlantaAscensor(i, 0);
				habilitarBotonAscensor(i,  true);
				
				// Crear y arrancar el hilo del ascensor
				hilosAscensores[i] = new HiloAscensor(i);
				hilosAscensores[i].start();				
			}
		});

		// Configuración del botón "Detener simulación"
		btnParar = new JButton("Detener simulación");
		btnParar.setEnabled(false);
		btnParar.addActionListener(e -> {

			// TAREA 3 - Hilos: Introduce aquí el código para detener la simulación
			
			btnParar.setEnabled(false);
			btnIniciar.setEnabled(true);

			for (int i = 0; i < NUM_ASCENSORES; i++) {
				// Detener el hilo del ascensor
				hilosAscensores[i].interrupt();				
				habilitarBotonAscensor(i,  false);
			}
		});

		JPanel panelBotonGlobal = new JPanel();
		panelBotonGlobal.add(btnIniciar);
		panelBotonGlobal.add(btnParar);
		add(panelBotonGlobal, BorderLayout.SOUTH);

		// Configuración general de la ventana
		setTitle("Simulador de Ascensores - Sala de Control");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setSize(920, 400);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	private JPanel crearPanelAscensor(int num) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Ascensor " + (num + 1)));

		// Display grande para la planta actual
		lblPlantaActual[num] = new JLabel("0", JLabel.CENTER);
		lblPlantaActual[num].setPreferredSize(new Dimension(150, 120));
		lblPlantaActual[num].setFont(new Font("Verdana", Font.BOLD, 120));
		lblPlantaActual[num].setOpaque(true);
		lblPlantaActual[num].setBackground(new Color(3, 94, 123));
		lblPlantaActual[num].setForeground(new Color(187, 222, 240));

		// Info destino + estado
		JPanel panelInfo = new JPanel(new GridLayout(2, 1));
		lblDestino[num] = new JLabel(" ", JLabel.CENTER);
		lblDestino[num].setFont(new Font("Verdana", Font.PLAIN, 12));
		lblDestino[num].setOpaque(true);
		lblDestino[num].setForeground(new Color(200, 230, 250));
		lblDestino[num].setBackground(new Color(3, 94, 123));
		
		lblEstado[num] = new JLabel(" ", JLabel.CENTER);
		lblEstado[num].setFont(new Font("Verdana", Font.BOLD, 12));
		lblEstado[num].setOpaque(true);
		lblEstado[num].setBackground(new Color(3, 94, 123));
		panelInfo.add(lblDestino[num]);
		panelInfo.add(lblEstado[num]);

		// Configuración del botón "Pausar/Reanudar" el ascensor
		JPanel panelBotones = new JPanel();

		// Configuración del botón "Detener" el ascensor
		btnDetener[num] = new JButton("Fuera de Servicio");
		btnDetener[num].setEnabled(false);
		btnDetener[num].addActionListener(e -> {

			// TAREA 3 - Hilos: Introduce aquí el el código para detener un ascensor
			hilosAscensores[num].interrupt();
			
			cambiarEstadoAscensor(num, EstadoAscensor.FUERA_DE_SERVICIO);
			btnDetener[num].setEnabled(false);			
		});

		panelBotones.add(btnDetener[num]);

		panel.add(panelInfo, BorderLayout.NORTH);
		panel.add(lblPlantaActual[num], BorderLayout.CENTER);
		panel.add(panelBotones, BorderLayout.SOUTH);

		return panel;
	}
	
	protected void cambairPlantaAscensor(int numAscensor, int planta) {
		SwingUtilities.invokeLater(() -> lblPlantaActual[numAscensor].setText(Integer.toString(planta)));
	}
	
	protected void cambiarDestinoAscensor(int numAscensor, int plantaDestino) {
		SwingUtilities.invokeLater(() -> lblDestino[numAscensor].setText("Destino: " + plantaDestino));
	}
	
	protected void cambiarEstadoAscensor(int numAscensor, EstadoAscensor estado) {
		SwingUtilities.invokeLater(() -> {
			lblEstado[numAscensor].setText(estado.toString());
			lblEstado[numAscensor].setForeground(estado.getColor());	
		});
	}
	
	private class HiloAscensor extends Thread {
		private int numAscensor;
		private int plantaActual = 0;
		private int plantaDestino = 0;

		public HiloAscensor(int numAscensor) {
			this.numAscensor = numAscensor;
		}
		
		@Override
		public void run() {
			//Mientras el hilo no sea interrumpido
			while (!isInterrupted()) {
				// Se genera el destino de manera aleatoria entre las plantas disponibles
				plantaDestino = PLANTAS[(int) (Math.random() * PLANTAS.length)];
				
				// Se actualiza la interfaz con el nuevo destino
				cambiarDestinoAscensor(numAscensor, plantaDestino);
				// Se actualiza el estado del ascensor a EN_MOVIMIENTO
				cambiarEstadoAscensor(numAscensor, EstadoAscensor.EN_MOVIMIENTO);
				
				// Se simula el movimiento del ascensor hacia la planta destino
				// con un pausa de 500ms entre plantas
				int step = (plantaDestino > plantaActual) ? 1 : -1;
				
				while (plantaActual != plantaDestino && !isInterrupted()) {					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						this.interrupt();
						return;
					}
					
					plantaActual += step;
					
					// Se actualiza la planta actual del ascensor en la interfaz
					cambairPlantaAscensor(numAscensor, plantaActual);
				}
				
				// Se actualiza el estado del ascensor a EN_PLANTA
				cambiarEstadoAscensor(numAscensor, EstadoAscensor.EN_PLANTA);
				
				// Una vez alcanzado el destino, se simula una parada de 2 segundos				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					this.interrupt();
					return;
				}				
			}
		}		
	}
			
	protected void habilitarBotonAscensor(int num, boolean value) {
		btnDetener[num].setEnabled(value);
	}	

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new AscensoresSolucion());
	}
}