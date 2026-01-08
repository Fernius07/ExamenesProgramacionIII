// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.swing.solucion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import es.deusto.prog.examen.Actividad;
import es.deusto.prog.examen.swing.HorarioGimnasioModel;

public class HorarioGimnasioJTableSolucion extends JFrame {
    private static final long serialVersionUID = 1L;
    private List<Actividad> actividades;
    
    // Se añade un atributo para almacenar la posición del cursor
    private Point puntoCursor;

	// TAREA 3: Renderizado de JTable.
	// El objetivo de esta tarea es modificar la funcionalidad de visualización
	// de calendario semanal de sesiones en un JTable que fue parte del examen 
	// parcial noviembre. En este caso, queremos que al pasar el ratón sobre una
	// sesión de una actividad, la celda correspondiente muestre el nombre del 
	// tipo de la Actividad y debajo, el número de plazas disponibles y las plazas
	// máximas de la actividad. Además, en caso de que el número de plazas 
	// disponibles sea igual a 0, todo el texto aparece en color rojo. La fuente 
	// utilizada para el nuevo contenido de las celdas debe ser “Arial” de 18 
	// puntos y negrita.
	// Por otro lado, si no hay plazas disponibles, cuando se muestre la imagen 
	// de la actividad el color de fondo también debe ser rojo.
	public HorarioGimnasioJTableSolucion(List<Actividad> actividades) {
		this.actividades = actividades;

		HorarioGimnasioModel modeloTabla = new HorarioGimnasioModel(this.actividades);
		JTable tablaActividades = new JTable(modeloTabla);
		
		tablaActividades.setShowGrid(false);
		tablaActividades.getTableHeader().setReorderingAllowed(false);
		tablaActividades.setRowHeight(64);
		
		tablaActividades.getTableHeader().setDefaultRenderer((table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel(value.toString());
			
			result.setHorizontalAlignment(SwingConstants.CENTER);
			result.setFont(new Font("Arial", Font.BOLD, 18));
			result.setBackground(Color.WHITE);
			result.setOpaque(true);
			
			return result;
		});	

		TableCellRenderer cellRenderer = (table, value, isSelected, hasFocus, row, column) -> {
			JLabel result = new JLabel();
			result.setHorizontalAlignment(SwingConstants.CENTER);

			if (column == 0) {
				result.setFont(new Font("Arial", Font.BOLD, 18));
				result.setText((String) value);
			} else if (value instanceof Actividad) {				
				Actividad actividad = (Actividad) value;
				
				// Si el cursor está sobre la tabla
				if (puntoCursor != null) {
					// Se obtiene la fila y columna del cursor
					int filaCursor = tablaActividades.rowAtPoint(puntoCursor);
					int columnaCursor = tablaActividades.columnAtPoint(puntoCursor);
					
					// Si la fila y columna del cursor coinciden con la fila y columna actual
					if (filaCursor == row && columnaCursor == column) {
						JPanel panellActividad = new JPanel(new GridLayout(2, 1));
						JLabel lblActividad = new JLabel(actividad.getTipo().toString());
						lblActividad.setHorizontalAlignment(JLabel.CENTER);
						lblActividad.setFont(new Font("Arial", Font.BOLD, 18));
						JLabel lblPlazas = new JLabel(actividad.getPlazasDisponibles() + " / " + actividad.getPlazasMaximas());
						lblPlazas.setHorizontalAlignment(JLabel.CENTER);
						lblPlazas.setFont(new Font("Arial", Font.PLAIN, 18));
						// Si no hay plazas disponibles, el color del texto es ROJO
						if (actividad.getPlazasDisponibles() == 0) {
							lblActividad.setForeground(Color.RED);
							lblPlazas.setForeground(Color.RED);							
						}
						
						panellActividad.add(lblActividad);
						panellActividad.add(lblPlazas);						
						panellActividad.setBackground(actividad.getTipo().getColor());
						return panellActividad;
					}
                }
				
				result.setIcon(actividad.getTipo().getIcon());
				
				// Si hay plazas disponibles no hay plazas disponibles, el color de fondo es rojo.
				if (actividad.getPlazasDisponibles() > 0) {
					result.setBackground(actividad.getTipo().getColor());
				} else {
					result.setBackground(Color.RED);
				}
				
				result.setToolTipText(actividad.getTipo().toString());
				result.setOpaque(true);				
			} else {
				result.setText("");
			}
				
			return result;
		};

		tablaActividades.setDefaultRenderer(Object.class, cellRenderer);	
		
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
                        System.exit(0);
                    }
                }
            }
        };
        
        // Se define el MouseAdapter para actualizar la posición del cursor
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				puntoCursor = e.getPoint();
				tablaActividades.repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				puntoCursor = null;
				tablaActividades.repaint();
			}
		};
		
		// Se añade el MouseAdapter a la tabla
		tablaActividades.addMouseMotionListener(mouseAdapter);      
        
        tablaActividades.addKeyListener(keyAdapter);        
        this.addKeyListener(keyAdapter);
        this.setFocusable(true);

		this.add(tablaActividades.getTableHeader(), BorderLayout.NORTH);
		this.add(tablaActividades, BorderLayout.CENTER);

		JLabel lblFooter = new JLabel("<html><a href=\"\">Icons created by Freepik - Flaticon (https://www.flaticon.com/authors/freepik)</a><html>", JLabel.RIGHT);
		lblFooter.setBackground(Color.WHITE);
		lblFooter.setForeground(Color.BLUE);
		lblFooter.setOpaque(true);		
		this.add(lblFooter, BorderLayout.SOUTH);
		
		this.setTitle("Horario Semanal de Actividades");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
}