package gui;

import domain.Dificultad;
import domain.Pista;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class VentanaResort extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tabla;
	private JLabel lblAlerta; // Para el Hilo

	public VentanaResort(Map<Dificultad, List<Pista>> mapaPistas) {
		this.setTitle("Gestión Resort Esquí - Examen");
		this.setSize(600, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());

		// 1. Preparar datos para la tabla (aplanar el mapa)
		List<Pista> todasLasPistas = new ArrayList<>();
		//mapaPistas.values().forEach(todasLasPistas::addAll);
		mapaPistas.values().forEach(lista -> todasLasPistas.addAll(lista));
		
		// Ordenar por Dificultad
		Collections.sort(todasLasPistas, Comparator.comparing(Pista::getDificultad));

		// 2. Configurar JTable
		PistasTableModel modelo = new PistasTableModel(todasLasPistas);
		tabla = new JTable(modelo);

		// 3. Asignar Renderer a la columna de Dificultad (índice 1)
		tabla.setDefaultRenderer(Dificultad.class, new PistaRenderer());

		this.add(new JScrollPane(tabla), BorderLayout.CENTER);

		// 4. Panel superior para el Hilo (Simulación de competencia CE2 - Threads)
		JPanel pnlNorte = new JPanel();
		lblAlerta = new JLabel("ESTADO DEL RESORT: NORMAL");
		lblAlerta.setFont(new Font("Arial", Font.BOLD, 16));
		lblAlerta.setOpaque(true);
		pnlNorte.add(lblAlerta);
		this.add(pnlNorte, BorderLayout.NORTH);

		iniciarHiloAlerta();
	}

	// TAREA HILOS: Banner parpadeante
	private void iniciarHiloAlerta() {
		Thread t = new Thread(() -> {
			boolean alerta = false;
			while (true) {
				try {
					Thread.sleep(1000); // Esperar 1 segundo

					final boolean estadoAlerta = !alerta; // Toggle

					SwingUtilities.invokeLater(() -> {
						if (estadoAlerta) {
							lblAlerta.setText("¡¡RIESGO DE ALUDES!!");
							lblAlerta.setBackground(Color.RED);
							lblAlerta.setForeground(Color.WHITE);
						} else {
							lblAlerta.setText("ESTADO DEL RESORT: PRECAUCIÓN");
							lblAlerta.setBackground(Color.YELLOW);
							lblAlerta.setForeground(Color.BLACK);
						}
					});

					alerta = estadoAlerta;

				} catch (InterruptedException e) {
					break;
				}
			}
		});
		t.start();
	}
}