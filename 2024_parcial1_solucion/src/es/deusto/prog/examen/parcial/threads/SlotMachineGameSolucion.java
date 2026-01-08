// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.parcial.threads;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import es.deusto.prog.examen.parcial.Actividad.TipoActividad;

public class SlotMachineGameSolucion extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel[] slots;
	private JButton startButton = new JButton("Iniciar");
	private JButton stopButton = new JButton("Parar");
	private Thread[] threads;

	private List<TipoActividad> actividades = List.of(TipoActividad.values());
	private boolean premioObtenido = true;

	public SlotMachineGameSolucion() {
		JPanel slotPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		slots = new JLabel[3];

		// Se inicializan los slots con imágenes aleatorias
		for (int i = 0; i < 3; i++) {
			slots[i] = new JLabel();
			Random random = new Random();
			this.actualizarLabel(slots[i], actividades.get(random.nextInt(actividades.size())));
			slotPanel.add(slots[i]);
		}

		startButton.addActionListener(e -> {
			// Si se ha ganado un premio, no se puede seguir
			if (premioObtenido) {
				JOptionPane.showMessageDialog(this, "¡Ya has ganado un abono mensual, no puedes seguir jugando!", "Ya premiado", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			startGame();			
		});
		
		stopButton.addActionListener(e -> {
			stopButton.setEnabled(false);
			startButton.setEnabled(true);
			stopGame();
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);

		this.setLayout(new BorderLayout());
		this.add(slotPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);

		this.setTitle("Slot Machine de Actividades");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(400, 200);
		this.setLocationRelativeTo(null);
	}
	
	private void actualizarLabel(JLabel label, TipoActividad actividad) {
		// Se actualiza el label con la imagen de la actividad
		SwingUtilities.invokeLater(() -> {
			String imagePath = "resources/images/" + actividad.toString().toLowerCase() + ".png";
			label.setIcon(new ImageIcon(imagePath));
			label.setBackground(actividad.getColor());
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setOpaque(true);
		});
	}

	// TODO: Tarea 4: Iniciar el juego
	private void startGame() {
		// Se inician los hilos para cada slot
		threads = new Thread[3];

		// Se crea un hilo para cada slot
		for (int i = 0; i < threads.length; i++) {
			final int slotIndex = i;

			threads[i] = new Thread(() -> {
				Random random = new Random();

				// Se actualiza el slot con una imagen aleatoria
				while (!Thread.currentThread().isInterrupted()) {
					int actividadIndex = random.nextInt(actividades.size());
					actualizarLabel(slots[slotIndex], actividades.get(actividadIndex));

					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
						break;
					}
				}
			});

			threads[i].start();
		}
	}

	// TODO: Tarea 4: Detener el juego
	private void stopGame() {
		// Se detienen todos los hilos
		for (Thread thread : threads) {
			if (thread != null) {
				thread.interrupt();
			}
		}
		
		// ya que los hilos pueden no terminar al instante
		// vamos a comprobar que todos lo han hecho antes
		// de comprobar el resultado
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// no hacer nada en este caso
			}
		}

		// Verificar si todas las imágenes son iguales
		if (checkWin()) {
			JOptionPane.showMessageDialog(this, "¡Felicidades! ¡Has ganado un abono mensual!", "Premio", JOptionPane.INFORMATION_MESSAGE);
			premioObtenido = true;
		} else {
			JOptionPane.showMessageDialog(this, "¡Lo siento! Inténtalo de nuevo.", "Otra vez será", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// Verifica si todas las imágenes son iguales
	private boolean checkWin() {
		Icon firstIcon = slots[0].getIcon();

		for (int i = 1; i < slots.length; i++) {
			if (!slots[i].getIcon().equals(firstIcon)) {
				return false;
			}
		}

		return true;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			SlotMachineGameSolucion juego = new SlotMachineGameSolucion();
			juego.setVisible(true);
		});
	}
}