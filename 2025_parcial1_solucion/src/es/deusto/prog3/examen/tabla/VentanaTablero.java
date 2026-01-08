package es.deusto.prog3.examen.tabla;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import es.deusto.prog3.examen.domain.PiezaAjedrez; 

public class VentanaTablero extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private PiezaAjedrez[][] tablero;

	public VentanaTablero() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Chess Master 2000");
		setSize(800, 650);
		setLocationRelativeTo(null);
		 
		inicializarTablero();
		
		// TAREA 1.2 Implementa aquí la tarea
		
		//String[] cabecera = { "a", "b", "c", "d", "e", "f", "g", "h" };
		//JTable tablaJuego = new JTable(convertirAString(tablero), cabecera);
		
		JTable tablaJuego = new JTable(new TableroModel(tablero));
		JScrollPane scrollPane = new JScrollPane(tablaJuego);
		add(scrollPane);
		
		// evita poder ordenar las columnas
		tablaJuego.getTableHeader().setReorderingAllowed(false);
		tablaJuego.setShowGrid(false); // quitamos la rejilla
		
		// establecemos un tamaño para las celdas
		tablaJuego.setRowHeight(64);
		
		// TAREA 1.3 Implementa aquí la tarea

		// TAREA 1.4 Implementa aquí la tarea
		
		// renderer usado tanto para la cabecera como para la última fila del tablero
		TableCellRenderer rendererLetras = new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				return renderizarCoordenada(value);
			}
		};
		
		// establecemos el renderer para la cabecera
		tablaJuego.getTableHeader().setDefaultRenderer(rendererLetras);
		
		// y el renderer para las celdas de la talba
		tablaJuego.setDefaultRenderer(Object.class, new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				if (row == 8) {
					// si es la última fila se renderiza como la cabecera
					return renderizarCoordenada(value);
				}
				
				// también las columnas de numeración se renderizan como la cabecera
				if (column == 0 || column == 9) {
					return renderizarCoordenada(Integer.toString((int) value));
				}
				
				// estamos renderizando una celda del tablero
				PiezaAjedrez pieza = (PiezaAjedrez) value;
				
				JLabel label = new JLabel();
				label.setHorizontalAlignment(JLabel.CENTER);
				
				//si la pieza no es vacía establecemos el icono correspondiente
				if (pieza != PiezaAjedrez.VACIO) {
					ImageIcon icon = new ImageIcon(pieza.getImagen());
					label.setIcon(icon);
				}
								
				// establecemos el color de la celda según el patrón
				Color colorClaro = new Color(239, 238, 211);
				Color colorOscuro = new Color(120, 151, 88);	
				Color color = ((row + column) % 2 == 0) ? colorClaro : colorOscuro;
				label.setOpaque(true);
				label.setBackground(color);
				
				return label;
			}
		});
		
		tablaJuego.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int fila = tablaJuego.rowAtPoint(e.getPoint());
				int columna = tablaJuego.columnAtPoint(e.getPoint());
					
				if (columna > 0 && columna < tablero.length + 1) {
					tablero[tablero.length - fila - 1][columna - 1] = PiezaAjedrez.PEON_BLANCO;
					tablaJuego.repaint();
				}
			}
			
		});
		
		setVisible(true);
	}
	
	private Component renderizarCoordenada(Object value) {
		JLabel label = new JLabel(value.toString());
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 20));
		label.setOpaque(true);
		label.setBackground(Color.WHITE);
		return label;
	}

	private void inicializarTablero() {
		tablero = new PiezaAjedrez[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tablero[i][j] = PiezaAjedrez.VACIO;
			}
		}
		
		tablero[0][5] = PiezaAjedrez.REY_BLANCO;
		tablero[1][0] = PiezaAjedrez.PEON_BLANCO;
		tablero[1][3] = PiezaAjedrez.PEON_NEGRO;
		tablero[1][7] = PiezaAjedrez.PEON_NEGRO;
		tablero[2][1] = PiezaAjedrez.PEON_BLANCO;
		tablero[4][7] = PiezaAjedrez.REY_NEGRO;
		tablero[6][4] = PiezaAjedrez.PEON_NEGRO;
		tablero[7][2] = PiezaAjedrez.TORRE_BLANCA;
	}
	
	Object[][] convertirAString(PiezaAjedrez[][] tablero) {
		Object[][] tableroString = new Object[8][8];
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				tableroString[i][j] = tablero[i][j].getSimbolo();
			}
		}
		
		return tableroString;
	}
	
}
