// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.parcial.render;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import es.deusto.prog.examen.parcial.Actividad;

public class HorarioGimnasioJTableSolucion extends JFrame {
	private static final long serialVersionUID = 1L;
	private List<Actividad> actividades;

	public HorarioGimnasioJTableSolucion(List<Actividad> actividades) {
		this.actividades = actividades;

		//TODO: TAREA 2: Modificar el modelo de datos y renderizado de la tabla de actividades.
		// Crear el modelo de datos personalizado con la lista de actividades
		HorarioGimnasioModel modeloTabla = new HorarioGimnasioModel(this.actividades);
		// Crear la tabla de actividades usando el modelo de datos personalizado
		JTable tablaActividades = new JTable(modeloTabla);
		
		// Ocultar las líneas de la tabla
		tablaActividades.setShowGrid(false);
		// Evitar la reordenación de columnas
		tablaActividades.getTableHeader().setReorderingAllowed(false);
		// Establecer la altura de las filas
		tablaActividades.setRowHeight(64);
		
		// Renderizado personalizado para la cabecera
		tablaActividades.getTableHeader().setDefaultRenderer((table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel(value.toString());
			
			result.setHorizontalAlignment(SwingConstants.CENTER);
			result.setFont(new Font("Arial", Font.BOLD, 18));
			result.setBackground(Color.WHITE);
			result.setOpaque(true);
			
			return result;
		});	

		// Renderizado personalizado para las celdas de la tabla
		TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel();
			result.setHorizontalAlignment(SwingConstants.CENTER);

			// Configuración del renderizado para la primera columna (Horas)
			if (column == 0) {
				result.setFont(new Font("Arial", Font.BOLD, 18));
				result.setText((String) value);
			// Configuración del renderizado para las celdas que contengas Atividades
			} else if (value instanceof Actividad) {
				Actividad actividad = (Actividad) value;
				String imageName = actividad.getTipo().toString().toLowerCase() + ".png";
				String imagePath = "resources/images/" + imageName;				
				result.setIcon(new ImageIcon(imagePath));
				result.setBackground(actividad.getTipo().getColor());
				result.setToolTipText(actividad.getTipo().toString());
				result.setOpaque(true);
			// Configuración de celdas vacías
			} else {
				result.setText("");
			}
				
			return result;
		};

		// Asignar el renderer a todas las celdas de la tabla
		tablaActividades.setDefaultRenderer(Object.class, cellRenderer);	
		
		 //TODO: TAREA 3: Mostrar un mensaje de confirmación para cerrar de la aplicación al pulsar CTRL + E.
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_E && e.isControlDown()) {
                    int result = JOptionPane.showConfirmDialog(
                            null,
                            "¿Estás seguro de que quieres salir?",
                            "Confirmación de salida",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (result == JOptionPane.YES_OPTION) {
                        System.exit(0); // Cierra la aplicación al pulsar "Sí"
                    }
                }
            }
        };
        
        // Añadir el KeyAdapter a la tabla y al JFrame
        tablaActividades.addKeyListener(keyAdapter);        
        this.addKeyListener(keyAdapter);
        // Indicar que la ventana puede obtener el foco
        this.setFocusable(true);

		this.add(tablaActividades.getTableHeader(), BorderLayout.NORTH);
		this.add(tablaActividades, BorderLayout.CENTER);

		JLabel lblFooter = new JLabel("<html><a href=\"\">Icons created by Freepik - Flaticon (https://www.flaticon.com/authors/freepik)</a><html>", JLabel.RIGHT);
		lblFooter.setForeground(Color.BLUE);
		this.add(lblFooter, BorderLayout.SOUTH);

		this.setTitle("Horario Semanal de Actividades");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(800, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
}