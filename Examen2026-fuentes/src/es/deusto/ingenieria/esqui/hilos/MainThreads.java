/**
 * Este código ha sido elaborado a partir de una versión generada con Gemini 3.
 * La versión final ha sido revisada y adaptada para garantizar su corrección
 * y la ausencia de errores.
 */

package es.deusto.ingenieria.esqui.hilos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import es.deusto.ingenieria.esqui.domino.Ciudad;
import es.deusto.ingenieria.esqui.domino.Ciudad.Pais;
import es.deusto.ingenieria.esqui.jdbc.GestorBD;
import es.deusto.ingenieria.esqui.domino.Itinerario;


// TAREA 4 - HILOS  
// Debes implementar la lógica de la simulación (utilizando hilos) que haga que
// el autobús se desplace de izquierda a derecha.
public class MainThreads extends JFrame {

	private static final long serialVersionUID = 1L;
	private static GestorBD GESTOR_BD = new GestorBD();

	private JTree treeItinerarios;
	private JPanelCarretera panelSimulacion;
	private JLabel lblEstado;
	private JButton btnIniciar;
	private JButton btnPausa;
	private JButton btnFinalizar;
	private JButton btnRapido;

	private Itinerario itinerarioSeleccionado;
	
	public MainThreads(List<Itinerario> itinerarios) {
		setLayout(new BorderLayout());

		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Itinerarios");

		Map<Pais, Map<Ciudad, List<Itinerario>>> mapa = new TreeMap<>();
		Collections.sort(itinerarios);
				
		itinerarios.forEach(it -> {
			mapa.computeIfAbsent(it.getOrigen().getPais(), k -> new TreeMap<>((c1, c2) -> c1.getNombre().compareTo(c2.getNombre())))
				.computeIfAbsent(it.getOrigen(), k -> new ArrayList<>())
				.add(it);
		});
		
		mapa.forEach((pais, ciudades) -> {
			DefaultMutableTreeNode nodoPais = new DefaultMutableTreeNode(pais);
			
			ciudades.forEach((ciudad, its) -> {
				DefaultMutableTreeNode nodoCiudad = new DefaultMutableTreeNode(ciudad);				
				its.forEach(it -> nodoCiudad.add(new DefaultMutableTreeNode(it)));				
				nodoPais.add(nodoCiudad);
			});
			
			raiz.add(nodoPais);
		});		
		
		treeItinerarios = new JTree(new DefaultTreeModel(raiz));
		
		ImageIcon iconoBusStop = new ImageIcon(new ImageIcon("resources/images/BUS_STOP.png")
				.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		ImageIcon iconoBus = new ImageIcon(new ImageIcon("resources/images/BUS.png")
				.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		
		treeItinerarios.setCellRenderer((tree, value, sel, exp, leaf, row, focus) -> {
            DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            JLabel label = (JLabel) renderer.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, focus);
			
			if (node.getUserObject() instanceof Pais) {
				Pais p = (Pais) node.getUserObject();
				label.setText(p.getNombre());
				label.setIcon(new ImageIcon(new ImageIcon("resources/images/" + p.name() + ".png")
						.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
			} else if (node.getUserObject() instanceof Ciudad) {
				label.setText(((Ciudad) node.getUserObject()).getNombre());
				label.setIcon(iconoBusStop);
			} else if (node.getUserObject() instanceof Itinerario) {
				Itinerario it = (Itinerario) node.getUserObject();
				label.setText(String.format("-> %s (%d min.)", it.getDestino().getNombre(), it.getDuracion()));
				label.setIcon(iconoBus);
			}
			
			return label;
		});

		treeItinerarios.addTreeSelectionListener(e -> {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeItinerarios.getLastSelectedPathComponent();
			if (node != null) {
				if (node.getUserObject() instanceof Itinerario) {
					itinerarioSeleccionado = (Itinerario) node.getUserObject();
					prepararSimulacion(itinerarioSeleccionado);
				} else {
					itinerarioSeleccionado = null;
					lblEstado.setText("Seleccione un itinerario");
					setBotonesesHabilitados(false);
				}
			}
		});
		
		JScrollPane scrollTree = new JScrollPane(treeItinerarios);
		scrollTree.setPreferredSize(new Dimension(320, 0));
		add(scrollTree, BorderLayout.WEST);

		JPanel panelCentral = new JPanel(new BorderLayout());
		lblEstado = new JLabel("Seleccione un itinerario", JLabel.CENTER);
		lblEstado.setFont(lblEstado.getFont().deriveFont(Font.BOLD, 16f));
		lblEstado.setForeground(Color.YELLOW.brighter());
		lblEstado.setBackground(new Color(128, 128, 128));
		lblEstado.setOpaque(true);
		panelCentral.add(lblEstado, BorderLayout.NORTH);

		panelSimulacion = new JPanelCarretera();
		panelCentral.add(panelSimulacion, BorderLayout.CENTER);

		JPanel panelBotones = new JPanel(new FlowLayout());
		btnIniciar = new JButton("Iniciar");
		btnPausa = new JButton("Pausar");
		btnFinalizar = new JButton("Cancelar");
		btnRapido = new JButton("Modo 2x");

		panelBotones.add(btnIniciar);
		panelBotones.add(btnPausa);
		panelBotones.add(btnFinalizar);
		panelBotones.add(btnRapido);
		panelCentral.add(panelBotones, BorderLayout.SOUTH);
		add(panelCentral, BorderLayout.CENTER);

		// Botón Iniciar
		// INSERETAR TU CÓDIGO PARA INICIAR LA SIMULACIÓN
		btnIniciar.addActionListener(e -> {
			if (itinerarioSeleccionado != null) {				
				btnIniciar.setEnabled(false);
				btnPausa.setEnabled(true);
				btnFinalizar.setEnabled(true);
				btnRapido.setEnabled(true);
				treeItinerarios.setEnabled(false);				
			}
		});

		// Botón Pausar / Reanudar
		// INSERETAR TU CÓDIGO PARA PAUSAR Y REANUDAR LA SIMULACIÓN
		btnPausa.addActionListener(e -> {
			switch (btnPausa.getText()) {
				case "Pausar":
					btnPausa.setText("Reanudar");
					break;
				case "Reanudar":
					btnPausa.setText("Pausar");
					break;					
			}
		});

		// Botón Finalizar
		// INSERETAR TU CÓDIGO PARA FINALIZAR LA SIMULACIÓN
		btnFinalizar.addActionListener(e -> {
			resetearInterfaz();
		});

		// Botón Modo 2x / Modo 1x
		// INSERETAR TU CÓDIGO PARA CAMBIAR LA VELOCIDAD DE LA SIMULACIÓN
		btnRapido.addActionListener(e -> {
			switch (btnRapido.getText()) {
				case "Modo 2x":
					btnRapido.setText("Modo 1x");
					break;
				case "Modo 1x":
					btnRapido.setText("Modo 2x");
					break;					
			}		
		});

		setBotonesesHabilitados(false);
		
		JLabel lblFooter = new JLabel("Iconos creados por Freepik - https://www.flaticon.com/authors/freepik");
		lblFooter.setForeground(Color.BLUE);
		lblFooter.setHorizontalAlignment(JLabel.CENTER);
		lblFooter.setBorder(new LineBorder(Color.GRAY));		
		add(lblFooter, BorderLayout.SOUTH);
		
		setTitle("Simulador de Itinerarios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 500);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	// Preparar la interfaz para iniciar la simulación
	private void prepararSimulacion(Itinerario it) {
		actualizarMensaje(String.format("Simulando itineraio '%s' -> '%s'", 
										it.getOrigen().getNombre(), 
										it.getDestino().getNombre()));
		actualizarProgreso(0);
		setBotonesesHabilitados(true);
		btnPausa.setEnabled(false);
		btnFinalizar.setEnabled(false);
		btnRapido.setEnabled(false);
	}
	
	// Resetear la interfaz tras finalizar la simulación
	public void resetearInterfaz() {
		SwingUtilities.invokeLater(() -> {
			setBotonesesHabilitados(false);
			btnIniciar.setEnabled(true);
			btnPausa.setText("Pausar");
			treeItinerarios.setEnabled(true);
		});
	}

	// Habilitar o deshabilitar los botones
	private void setBotonesesHabilitados(boolean habilitado) {
		btnIniciar.setEnabled(habilitado);
		btnPausa.setEnabled(habilitado);
		btnFinalizar.setEnabled(habilitado);
		btnRapido.setEnabled(habilitado);
	}

	// Actualizar el progreso de la simulación en el panel
	// Debe recibir un valor entre 0 y 100
	public void actualizarProgreso(int valor) {
		SwingUtilities.invokeLater(() -> panelSimulacion.setProgreso(valor));
	}

	// Actualizar el mensaje de texto del estado de la simulación
	public void actualizarMensaje(String texto) {
		SwingUtilities.invokeLater(() -> lblEstado.setText(texto));
	}
	
	public static void main(String[] args) {
		List<Ciudad> ciudades = GESTOR_BD.getCiudades();		
		Collections.sort(ciudades);		
		List<Itinerario> itinerarios = GESTOR_BD.getItinerarios(ciudades);
		
		// Para simplificar las pruebas, se omiten los itinerarios de más de 100 minutos.
		itinerarios.removeIf(it -> it.getDuracion() > 100);
		
		SwingUtilities.invokeLater(() -> new MainThreads(itinerarios));
	}
}