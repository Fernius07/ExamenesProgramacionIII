package es.deusto.hospital.jtable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import es.deusto.hospital.jtable.Parto.Estado;

public class PlanoMaternidadSolucion extends JFrame {

	private static final long serialVersionUID = 1L;

	public PlanoMaternidadSolucion(List<Parto> partos) {
		setLayout(new BorderLayout());

		//String[] columnas = { "HABITACIÓN", "MADRE", "BEBÉ", "ESTADO" };
		//String[][] datos = new String[partos.size()][columnas.length];

		// TAREA 2.2 - Renderizado de JTable: Introduce aquí tu código
		
		HabitacionModel habitacionModel = new HabitacionModel(partos);
		JTable tablaHabitaciones = new JTable(habitacionModel);

		// Se crea en Rendeder para la cabcera: texto en negrita y tamaño 14. Centrado en las columnas 0 y 3
		tablaHabitaciones.getTableHeader().setDefaultRenderer((table, value, isSelected , hasFocus, row, column) -> {
			JLabel header = new JLabel(value.toString());
			header.setFont(header.getFont().deriveFont(Font.BOLD, 14));
			header.setBackground(table.getTableHeader().getBackground());
			header.setForeground(table.getTableHeader().getForeground());
			header.setOpaque(true);
			
			if (column == 0) {
				header.setHorizontalAlignment(JLabel.CENTER);
			}

			return header;
		});
		
		// Se crear en Renderer personalizado para la tabla
		tablaHabitaciones.setDefaultRenderer(Object.class, (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel(value.toString());

			// Primera columna: imagen del número de la habitación
			if (column == 0) {
				result.setHorizontalAlignment(JLabel.CENTER);
				result.setIcon(new ImageIcon("resources/images/" + result.getText() + ".png"));
				result.setText("");
			}

			// Última columna: negrita y color de texto del estado
			if (column == 3) {
				Estado estado = (Estado) value;
				result.setIcon(estado.getIcon());
				result.setForeground(((Estado) value).getColor());
				result.setFont(result.getFont().deriveFont(Font.BOLD));
				result.setOpaque(true);					
			}

			// Color de texto y fondo cuando la fila está seleccionada.
			if (isSelected) {
				result.setBackground(table.getSelectionBackground());
				result.setForeground(table.getSelectionForeground());
			} else {
				result.setBackground(table.getBackground());
			}

			result.setOpaque(true);

			// Columna 2: un panel con la info de los bebés
			if (column == 2) {
				@SuppressWarnings("unchecked")
				List<Bebe> bebes = (List<Bebe>) value;

				JPanel panelBebes = new JPanel(new GridLayout(1, bebes.size(), 0, 0));
				panelBebes.setBackground(table.getBackground());
				panelBebes.setOpaque(true);
				
				bebes.forEach(b -> {
					JLabel labelBebe = new JLabel(b.getNombre());
					labelBebe.setIcon(b.getSexo().getIcon());

					if (isSelected) {
						labelBebe.setBackground(table.getSelectionBackground());
						labelBebe.setForeground(table.getSelectionForeground());
					} else {
						labelBebe.setBackground(table.getBackground());
					}

					labelBebe.setOpaque(true);

					panelBebes.add(labelBebe);
				});

				return panelBebes;
			}

			return result;
		});

		// Altura de la fila para que se visulicen bien las imágenes
		tablaHabitaciones.setRowHeight(36);
		// Ajustar anchura de la columna Bebes
		tablaHabitaciones.getColumnModel().getColumn(2).setPreferredWidth(150);
		// Impedir que se reordenen las columnas
		tablaHabitaciones.getTableHeader().setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(tablaHabitaciones);
		add(scrollPane, BorderLayout.CENTER);

		// Pie de página
		JLabel lblFooter = new JLabel("<html>Icons created by 'Freepik' & 'Md Tanvirul Haque' - https://www.flaticon.com<html>");
		lblFooter.setForeground(Color.BLUE);
		lblFooter.setHorizontalAlignment(JLabel.CENTER);

		add(lblFooter, BorderLayout.SOUTH);

		// Configuración de la ventana
		setTitle("Maternidad - Plano de habitaciones");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(680, 610);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		// Lista de partos de ejemplo
		List<Parto> partos = List.of(
				new Parto("H-1", "Emma Johansson", List.of(new Bebe("Liam", Bebe.Sexo.BOY)), Parto.Estado.POSTPARTO),
				new Parto("H-2", "Chiara Bianchi",
						List.of(new Bebe("¿?", Bebe.Sexo.UNKNOWN), new Bebe("¿?", Bebe.Sexo.UNKNOWN)),
						Parto.Estado.EN_PROCESO),
				new Parto("H-3", "Amara Olatunji", List.of(new Bebe("Kofi", Bebe.Sexo.BOY)), Parto.Estado.POSTPARTO),
				new Parto("H-4", "Hana Yamamoto",
						List.of(new Bebe("¿?", Bebe.Sexo.UNKNOWN), new Bebe("¿?", Bebe.Sexo.UNKNOWN)),
						Parto.Estado.PREPARTO),
				new Parto("H-5", "Claire Dubois", List.of(new Bebe("Lucas", Bebe.Sexo.BOY)), Parto.Estado.POSTPARTO),
				new Parto("H-6", "Amina El-Khatib",
						List.of(new Bebe("Salma", Bebe.Sexo.GIRL), new Bebe("Yasir", Bebe.Sexo.BOY)),
						Parto.Estado.POSTPARTO),
				new Parto("H-7", "Fernanda Oliveira", List.of(new Bebe("Matheus", Bebe.Sexo.BOY)),
						Parto.Estado.POSTPARTO),
				new Parto("H-8", "Sara Kovacs",
						List.of(new Bebe("Noemi", Bebe.Sexo.GIRL), new Bebe("Tamas", Bebe.Sexo.BOY)),
						Parto.Estado.POSTPARTO),
				new Parto("H-9", "Olivia Smith", List.of(new Bebe("¿?", Bebe.Sexo.UNKNOWN)), Parto.Estado.EN_PROCESO),
				new Parto("H-10", "María González",
						List.of(new Bebe("Diego", Bebe.Sexo.BOY), new Bebe("Lucía", Bebe.Sexo.GIRL)),
						Parto.Estado.POSTPARTO),
				new Parto("H-11", "Ananya Patel", List.of(new Bebe("Aarav", Bebe.Sexo.BOY)), Parto.Estado.POSTPARTO),
				new Parto("H-12", "Nora Petersen",
						List.of(new Bebe("¿?", Bebe.Sexo.UNKNOWN), new Bebe("¿?", Bebe.Sexo.UNKNOWN)),
						Parto.Estado.EN_PROCESO),
				new Parto("H-13", "Yara Haddad", List.of(new Bebe("Nadia", Bebe.Sexo.GIRL)), Parto.Estado.POSTPARTO),
				new Parto("H-14", "Julia Mendez", List.of(new Bebe("Thiago", Bebe.Sexo.BOY)), Parto.Estado.POSTPARTO),
				new Parto("H-15", "Aiko Tanaka", List.of(new Bebe("¿?", Bebe.Sexo.UNKNOWN)), Parto.Estado.PREPARTO));

		SwingUtilities.invokeLater(() -> new PlanoMaternidadSolucion(partos));
	}
}