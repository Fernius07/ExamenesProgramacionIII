// IAG (Claude 3.5 Sonnet y ChatGPT 4o)
// ADAPTADO: El código ha sido creado con Claude y refinado con ChatGPT 4o.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import es.deusto.prog.examen.Actividad;

public class HorarioGimnasioJTable extends JFrame {
	private static final long serialVersionUID = 1L;
	private List<Actividad> actividades;
	protected Point puntocursor;
	
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
	public HorarioGimnasioJTable(List<Actividad> actividades) {
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
				
				if (puntocursor != null) {
					
				}
				result.setIcon(actividad.getTipo().getIcon());
				result.setBackground(actividad.getTipo().getColor());
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
        
        MouseAdapter mouseAdapter = new MouseAdapter() {
        	@Override
        	public void mouseMoved(MouseEvent e) {
        		puntocursor = e.getPoint();
        		tablaActividades.repaint();
        	}
        	
        	@Override
        	public void mouseExited(MouseEvent e) {
        		puntocursor = null;
        		tablaActividades.repaint();
        	}
		};
        
        tablaActividades.addMouseMotionListener(mouseAdapter);
        tablaActividades.addKeyListener(keyAdapter);
        this.addMouseMotionListener(mouseAdapter);
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