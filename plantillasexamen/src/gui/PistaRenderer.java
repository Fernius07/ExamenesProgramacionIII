package gui;

import domain.Dificultad;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.*;

public class PistaRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value instanceof Dificultad) {
			Dificultad dif = (Dificultad) value;
			this.setText(dif.toString());
			this.setHorizontalAlignment(CENTER);
			this.setOpaque(true); // Necesario para que se vea el fondo

			if (!isSelected) { // Solo cambiamos color si NO está seleccionada
				switch (dif) {
				case VERDE:
					this.setBackground(new Color(144, 238, 144)); // Verde claro
					this.setForeground(Color.BLACK);
					break;
				case AZUL:
					this.setBackground(new Color(173, 216, 230)); // Azul claro
					this.setForeground(Color.BLACK);
					break;
				case ROJA:
					this.setBackground(new Color(255, 99, 71)); // Rojo tomate
					this.setForeground(Color.WHITE);
					break;
				case NEGRA:
					this.setBackground(Color.BLACK);
					this.setForeground(Color.WHITE);
					break;
				}
			}
		} else {
			// Restaurar estilo por defecto para otras columnas si se reutiliza
			if (!isSelected) {
				this.setBackground(table.getBackground());
				this.setForeground(table.getForeground());
			}
		}
		return this;
	}
}