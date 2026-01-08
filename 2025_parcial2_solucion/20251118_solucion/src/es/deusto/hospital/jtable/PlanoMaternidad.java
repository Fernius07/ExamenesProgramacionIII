package es.deusto.hospital.jtable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class PlanoMaternidad extends JFrame {

	private static final long serialVersionUID = 1L;

	public PlanoMaternidad(List<Parto> partos) {
		setLayout(new BorderLayout());

		String[] columnas = { "HABITACIÓN", "MADRE", "BEBÉ", "ESTADO" };
		String[][] datos = new String[partos.size()][columnas.length];

		// TAREA 2.2 - Renderizado de JTable: Introduce aquí tu código

		JTable tablaHabitaciones = new JTable(datos, columnas);

		JScrollPane scrollPane = new JScrollPane(tablaHabitaciones);
		add(scrollPane, BorderLayout.CENTER);

		// Pie de página
		JLabel lblFooter = new JLabel("<html>Icons created by Pixel perfect & Md Tanvirul Haque - https://www.flaticon.com<html>");
		lblFooter.setForeground(Color.BLUE);
		lblFooter.setHorizontalAlignment(JLabel.CENTER);

		add(lblFooter, BorderLayout.SOUTH);

		// Configuración de la ventana
		setTitle("Maternidad - Plano de habitaciones");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 550);
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

		SwingUtilities.invokeLater(() -> new PlanoMaternidad(partos));
	}
}