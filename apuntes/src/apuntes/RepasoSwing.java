package apuntes;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

// --- CLASES AUXILIARES ---
// Asumimos que estas existen o las creamos aquí para que compile

enum Dificultad {
	VERDE, AZUL, ROJA, NEGRA
}

class Pista {
	private String nombre;
	private Dificultad dificultad;
	private double kilometros;
	private double tiempo;
	private boolean abierta;

	public Pista(String nombre, Dificultad d, double km, double tiempo, boolean abierta) {
		this.nombre = nombre;
		this.dificultad = d;
		this.kilometros = km;
		this.tiempo = tiempo;
		this.abierta = abierta;
	}

	public String getNombre() {
		return nombre;
	}

	public Dificultad getDificultad() {
		return dificultad;
	}

	public double getKilometros() {
		return kilometros;
	}

	public double getTiempo() {
		return tiempo;
	}

	public boolean isAbierta() {
		return abierta;
	}
}

// ==========================================================
//   CLASE PRINCIPAL: REPASO SWING (TABLAS)
// ==========================================================
public class RepasoSwing extends JFrame {

	private static final long serialVersionUID = 1L;

	// -------------------------------------------------------------------------
	// 1. EL MODELO (TableModel) - El Cerebro
	// Es el puente entre tu List<Pista> y la tabla visual.
	// -------------------------------------------------------------------------
	public class ModeloTablaPistas extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		// 1. LAS COLUMNAS (Lo que sale en la cabecera)
		private final String[] COLUMNAS = { "Nombre", "Dificultad", "Longitud (km)", "Estado" };
		private List<Pista> listaPistas;

		public ModeloTablaPistas(List<Pista> lista) {
			this.listaPistas = lista;
		}

		// 2. MÉTODOS OBLIGATORIOS (Sota, caballo y rey)
		@Override
		public int getRowCount() {
			return listaPistas.size();
		}

		@Override
		public int getColumnCount() {
			return COLUMNAS.length;
		}

		@Override
		public String getColumnName(int column) {
			return COLUMNAS[column]; // ¡ESTO PONE EL HEADER AUTOMÁTICO!
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Pista p = listaPistas.get(rowIndex);

			switch (columnIndex) {
			case 0:
				return p.getNombre();
			case 1:
				return p.getDificultad(); // Devuelve el Enum
			case 2:
				return p.getKilometros();
			case 3:
				return p.isAbierta() ? "ABIERTA" : "CERRADA";
			default:
				return null;
			}
		}
	}

	// -------------------------------------------------------------------------
	// 2. EL RENDERER - El Pintor
	// Pinta el fondo de la fila según el color de la pista.
	// -------------------------------------------------------------------------
	public class RendererColores extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {

			// 1. IMPORTANTE: Llamar al super primero (para que pinte el texto básico)
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			// 2. RECUPERAR EL DATO PARA DECIDIR EL COLOR
			// Truco: Pedimos a la tabla el valor de la columna Dificultad (1) y Estado (3)
			Object valorDificultad = table.getValueAt(row, 1);
			String estado = table.getValueAt(row, 3).toString();

			// 3. LÓGICA DE PINTURA
			if (!isSelected) {
				// Solo pintamos si NO está seleccionada (para no tapar el azul de selección)

				// CASO ESPECIAL: Pista Cerrada (Gris)
				if (estado.equals("CERRADA")) {
					setBackground(Color.LIGHT_GRAY);
					setForeground(Color.DARK_GRAY);
				}
				// CASO NORMAL: Color por Dificultad
				else if (valorDificultad != null) {
					String dif = valorDificultad.toString();

					switch (dif) {
					case "VERDE":
						setBackground(new Color(144, 238, 144)); // Verde suave
						setForeground(Color.BLACK);
						break;
					case "AZUL":
						setBackground(new Color(173, 216, 230)); // Azul suave
						setForeground(Color.BLACK);
						break;
					case "ROJA":
						setBackground(new Color(255, 99, 71)); // Rojo tomate
						setForeground(Color.WHITE);
						break;
					case "NEGRA":
						setBackground(Color.BLACK);
						setForeground(Color.WHITE);
						break;
					default:
						setBackground(Color.WHITE);
						setForeground(Color.BLACK);
					}
				}
			} else {
				// Si está seleccionada, dejamos los colores por defecto del sistema (Azul)
				setBackground(table.getSelectionBackground());
				setForeground(table.getSelectionForeground());
			}
			return this;
		}
	}

	// -------------------------------------------------------------------------
	// 3. EL MAIN (Configuración) - Montar el Puzzle
	// -------------------------------------------------------------------------
	public void configurarVentana(List<Pista> listaDePistas) {

		// 1. Crear la Tabla
		JTable tabla = new JTable();

		// 2. Enchufar el MODELO
		ModeloTablaPistas modelo = new ModeloTablaPistas(listaDePistas);
		tabla.setModel(modelo);

		// 3. Enchufar el RENDERER DE CELDAS (Colores)
		// Se lo aplicamos a TODAS las clases (Object.class) para que pinte toda la
		// tabla
		tabla.setDefaultRenderer(Object.class, new RendererColores());

		// 4. (Opcional) Enchufar el RENDERER DE CABECERA (Tuneado visual)
		TableCellRenderer headerRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel label = new JLabel(value.toString());
			label.setFont(new Font("Arial", Font.BOLD, 16)); // Fuente Grande
			label.setOpaque(true); // Vital para que se vea el fondo
			label.setBackground(Color.DARK_GRAY);
			label.setForeground(Color.WHITE); // Texto blanco
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setBorder(BorderFactory.createEtchedBorder());
			return label;
		};
		tabla.getTableHeader().setDefaultRenderer(headerRenderer);

		// 5. Configurar visualización básica
		tabla.setRowHeight(30); // Filas más altas
		tabla.setShowGrid(true);

		// 6. ¡VITAL! Meter la tabla en un ScrollPane o NO SE VERÁN LOS TÍTULOS
		JScrollPane scroll = new JScrollPane(tabla);
		this.add(scroll);

		this.setSize(600, 400);
		this.setVisible(true);
	}
}