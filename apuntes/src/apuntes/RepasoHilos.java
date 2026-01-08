package apuntes;

import javax.swing.*;
import java.awt.*;

// ==========================================================
//   CLASE PRINCIPAL: REPASO HILOS (CONCURRENCIA)
// ==========================================================
public class RepasoHilos {

	// Variables simuladas de interfaz para que el código compile
	private JProgressBar barraProgreso = new JProgressBar();
	private JLabel lblEstado = new JLabel();
	private JLabel lblReloj = new JLabel();
	private JLabel lblTelesilla = new JLabel();

	// Variable para guardar referencia al hilo y poder pararlo
	private Thread hiloCarga;

	// -------------------------------------------------------------------------
	// CASO A: LA BARRA DE CARGA (El Clásico)
	// Objetivo: Bucle for determinado + Thread.sleep + invokeLater
	// -------------------------------------------------------------------------
	public void iniciarCarga() {
		// 1. Definimos el Hilo
		hiloCarga = new Thread(() -> {
			try {
				// Simulamos una carga del 0 al 100%
				for (int i = 0; i <= 100; i++) {

					// A. VELOCIDAD: Pausa de 50ms entre pasos
					Thread.sleep(50);

					// B. ACTUALIZAR VISTA (Dentro de la cápsula Swing)
					final int porcentaje = i; // Variable final efectiva

					SwingUtilities.invokeLater(() -> {
						// Actualizamos la barra
						barraProgreso.setValue(porcentaje);

						// Opcional: Cambiar texto
						lblEstado.setText("Compactando nieve... " + porcentaje + "%");

						// Opcional: Cambiar color si llega al final
						if (porcentaje == 100) {
							lblEstado.setText("¡Estación Abierta!");
							lblEstado.setForeground(Color.GREEN);
						}
					});
				}
			} catch (InterruptedException e) {
				// Si nos interrumpen (botón stop), salimos sin hacer ruido
				return;
			}
		});

		// 2. ¡IMPORTANTE! Arrancar el hilo
		hiloCarga.start();
	}

	// -------------------------------------------------------------------------
	// CASO B: EL RELOJ / CRONÓMETRO (Cuenta Atrás)
	// Objetivo: Bucle while (tiempo > 0) + Matemáticas de tiempo
	// -------------------------------------------------------------------------
	public void iniciarReloj() {
		Thread hiloReloj = new Thread(() -> {
			// 2 horas y 30 mins = 9000 segundos
			int segundosRestantes = 9000;

			try {
				while (segundosRestantes > 0) {
					// A. ESPERAR 1 SEGUNDO REAL
					Thread.sleep(1000);
					segundosRestantes--;

					// B. MATEMÁTICAS DE RELOJ (Segundos -> HH:MM:SS)
					int horas = segundosRestantes / 3600;
					int mins = (segundosRestantes % 3600) / 60;
					int segs = segundosRestantes % 60;

					// C. FORMATEAR TEXTO (Truco: %02d pone el cero delante: 05:09)
					String textoReloj = String.format("%02d:%02d:%02d", horas, mins, segs);

					// D. ACTUALIZAR VISTA
					// Necesitamos variables final o final efectivas para la lambda
					final int h = horas;
					final int m = mins;

					SwingUtilities.invokeLater(() -> {
						lblReloj.setText(textoReloj);

						// Efecto visual de alarma: Si queda poco tiempo, ponlo rojo
						if (h == 0 && m < 5) {
							lblReloj.setForeground(Color.RED);
						}
					});
				}

				// AL TERMINAR EL BUCLE
				SwingUtilities.invokeLater(() -> {
					JOptionPane.showMessageDialog(null, "¡FIN DE LA JORNADA!");
				});

			} catch (InterruptedException e) {
				return;
			}
		});

		hiloReloj.start();
	}

	// -------------------------------------------------------------------------
	// CASO C: EL TELESILLA (Movimiento Infinito)
	// Objetivo: Bucle while(true) + Array circular
	// -------------------------------------------------------------------------
	public void iniciarTelesilla() {
		Thread hiloTelesilla = new Thread(() -> {
			String[] estados = { "Subiendo esquiadores...", "En la cima", "Empty (Bajando vacio)" };

			int index = 0;

			try {
				// Bucle infinito
				while (true) {
					// A. MOSTRAR ESTADO ACTUAL
					final String textoActual = estados[index];

					SwingUtilities.invokeLater(() -> {
						lblTelesilla.setText(textoActual);
					});

					// B. ESPERAR (Tiempo que tarda en cambiar de estado)
					Thread.sleep(2000); // 2 segundos

					// C. AVANZAR AL SIGUIENTE (Circular: 0->1->2 -> 0)
					index++;
					if (index >= estados.length) {
						index = 0;
					}
				}
			} catch (InterruptedException e) {
				// Esto pasará cuando cierres la ventana y el hilo muera. Es normal.
			}
		});

		hiloTelesilla.start();
	}

	// -------------------------------------------------------------------------
	// 4. BOTÓN DE PÁNICO (Parar el Hilo)
	// -------------------------------------------------------------------------
	public void configurarBotonStop(JButton botonParar) {
		botonParar.addActionListener(e -> {
			if (hiloCarga != null && hiloCarga.isAlive()) {
				hiloCarga.interrupt(); // Esto dispara la 'InterruptedException' y rompe el bucle
				lblEstado.setText("SIMULACIÓN DETENIDA");
			}
		});
	}
}