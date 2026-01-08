// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.parcial.render;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import es.deusto.prog.examen.parcial.Actividad;

public class HorarioGimnasioJTable extends JFrame {
    private static final long serialVersionUID = 1L;
    // private List<Actividad> actividades;
    
    public HorarioGimnasioJTable(List<Actividad> actividades) {
    	// this.actividades = actividades;
    	
		//TODO: TAREA 2: Modificar el modelo de datos y renderizado de la tabla de actividades.
//        String[] columnNames = {"", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
//        String[][] data = {
//            {"09:00", "Yoga", "", "Yoga", "", ""},
//            {"10:00", "", "Pilates", "", "Pilates", ""},
//            {"11:00", "", "Zumba", "", "Zumba", ""},
//            {"12:00", "Boxeo", "", "", "", "Boxeo"},
//            {"13:00", "Spinning", "", "Spinning", "", "Spinning"}
//        };

//        JTable tablaActividades = new JTable(data, columnNames);
        HorarioGimnasioModel modelo = new HorarioGimnasioModel(actividades);
        JTable tablaActividades = new JTable(modelo);
        
        //TODO: TAREA 3: Mostrar un mensaje de confirmación para cerrar de la aplicación al pulsar CTRL + E.
        
		this.add(tablaActividades.getTableHeader(), BorderLayout.NORTH);
		this.add(tablaActividades, BorderLayout.CENTER);
		tablaActividades.setRowHeight(65);
		
		tablaActividades.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
					// una celda del header
					JLabel label = new JLabel(value.toString());
					label.setFont(new Font("Arial", Font.BOLD, 18));
					label.setHorizontalAlignment(JLabel.CENTER);
					label.setOpaque(true);
					label.setBackground(Color.WHITE);
					return label;
			}
		});
		
		tablaActividades.getTableHeader().setReorderingAllowed(false);
		tablaActividades.setShowGrid(false);
		
		tablaActividades.setDefaultRenderer(Object.class, new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				if (column == 0) {
					// estamos en la primera columna, y por lo tanto, es un entero
					JLabel label = new JLabel(value.toString());
					label.setFont(new Font("Arial", Font.BOLD, 18));
					label.setHorizontalAlignment(JLabel.CENTER);
					return label;
				}
				
				// estamos en una columna que contiene un objeto de tipo Actividad
				JLabel label = new JLabel("");
				if (value != null) {
					Actividad actividad = (Actividad) value;
					// cargar la imagen correspondiente a esta actividad y ponerla en el label
					ImageIcon icon = new ImageIcon("resources/images/" + actividad.getTipo().name().toLowerCase()  +".png");
					label.setIcon(icon);
					label.setOpaque(true);
					label.setBackground(actividad.getTipo().getColor());
					label.setHorizontalAlignment(JLabel.CENTER);
					label.setToolTipText(actividad.getTipo().name());
				}
				return label;
			}
		});

		JLabel lblFooter = new JLabel("<html><a href=\"\">Icons created by Freepik - Flaticon (https://www.flaticon.com/authors/freepik)</a><html>", JLabel.RIGHT);
		lblFooter.setForeground(Color.BLUE);
		this.add(lblFooter, BorderLayout.SOUTH);

		this.setTitle("Horario Semanal de Actividades");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(800, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
	    KeyListener keyListener = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_E && e.isControlDown()) {
					int result = JOptionPane.showConfirmDialog(HorarioGimnasioJTable.this, "¿Está seguro de que desea salir?", "Confirmación de salida", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				}
			}
		};
		
		addKeyListener(keyListener);
		tablaActividades.addKeyListener(keyListener);
    }
}