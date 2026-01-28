/**
 * Este código ha sido elaborado a partir de una versión generada con Gemini 3.
 * La versión final ha sido revisada y adaptada para garantizar su corrección
 * y la ausencia de errores.
 */

package es.deusto.ingenieria.esqui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import es.deusto.ingenieria.esqui.domino.Estacion;
import es.deusto.ingenieria.esqui.jdbc.GestorBD;

public class MainSwing extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public MainSwing(List<Estacion> estaciones) {				
		setLayout(new BorderLayout());
		
		Collections.sort(estaciones);
		EstacionTableModel modelEstaciones = new EstacionTableModel(estaciones);

		JTable tablaEstaciones = new JTable(modelEstaciones);
		tablaEstaciones.setShowGrid(true);
		tablaEstaciones.setGridColor(Color.LIGHT_GRAY);
		
		// TODO TAREA 3.1: JTable
		
		// INCPORPORA AQUÍ TU CÓDIGO PARA AJUSTAR EL RENDERIZADO DE LA TABLA DE ESTACIONES		
		
        tablaEstaciones.setRowHeight(40);
        tablaEstaciones.getColumnModel().getColumn(1).setPreferredWidth(220);
        tablaEstaciones.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaEstaciones.getTableHeader().setPreferredSize(new Dimension(0, 35));
		
		JScrollPane scrollPane = new JScrollPane(tablaEstaciones);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);

		// TODO TAREA 3.2: Gestión de eventos	
		
		// INCPORPORA AQUÍ TU CÓDIGO PARA MOSTRAR EL MENSAJE AL PULSAR LA TECLA '+' 
		
		JLabel lblFooter = new JLabel("Iconos creados por Freepik - https://www.flaticon.com/authors/freepik");
		lblFooter.setForeground(Color.BLUE);
		lblFooter.setHorizontalAlignment(JLabel.CENTER);
		lblFooter.setBorder(new LineBorder(Color.GRAY));		
		add(lblFooter, BorderLayout.SOUTH);

		setTitle("Estaciones de esquí");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		// Se obtienen los itinerarios desde la base de datos
		GestorBD gestorBD = new GestorBD();		
		List<Estacion> estaciones = gestorBD.getEstaciones(gestorBD.getCiudades());

		SwingUtilities.invokeLater(() -> new MainSwing(estaciones));
	}
}