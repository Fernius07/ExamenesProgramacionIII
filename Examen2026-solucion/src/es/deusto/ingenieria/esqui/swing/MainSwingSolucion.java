/**
 * Este código ha sido elaborado a partir de una versión generada con Gemini 3.
 * La versión final ha sido revisada y adaptada para garantizar su corrección
 * y la ausencia de errores.
 */

package es.deusto.ingenieria.esqui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import es.deusto.ingenieria.esqui.domino.Ciudad;
import es.deusto.ingenieria.esqui.domino.Ciudad.Pais;
import es.deusto.ingenieria.esqui.domino.Estacion;
import es.deusto.ingenieria.esqui.domino.EstadoEstacion;
import es.deusto.ingenieria.esqui.domino.EstadoEstacion.Apertura;
import es.deusto.ingenieria.esqui.jdbc.GestorBD;

public class MainSwingSolucion extends JFrame {
    private static final long serialVersionUID = 1L;

	public MainSwingSolucion(List<Estacion> estaciones) {
        setLayout(new BorderLayout());

        Collections.sort(estaciones);
        EstacionTableModelSolucion model = new EstacionTableModelSolucion(estaciones);

        JTable tablaEstaciones = new JTable(model);
		tablaEstaciones.setShowGrid(true);
		tablaEstaciones.setGridColor(Color.LIGHT_GRAY);
        
        // --- TAREA 3.1: JTable ---
        tablaEstaciones.setDefaultRenderer(Object.class, (table, value, isSelected, hasFocus, row, column) -> {
        	// Colores base de la tabla en función del estado de selección
            Color bgColor = isSelected ? table.getSelectionBackground() : table.getBackground();
            Color fgColor = isSelected ? table.getSelectionForeground() : table.getForeground();

            // CASO 1: Columna ESTACIÓN (Panel con Grid 2x1)
            if (column == 1 && value instanceof Estacion e) {
                JPanel pEstacion = new JPanel(new GridLayout(2, 1));
                pEstacion.setBackground(bgColor);
                
                JLabel lblNombre = new JLabel(e.getNombre());
                lblNombre.setFont(lblNombre.getFont().deriveFont(Font.BOLD));
                lblNombre.setForeground(fgColor);
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String fechaStr = (e.getEstado() != null) ? sdf.format(e.getEstado().getActualizacion()) : "Sin datos";
                JLabel lblFecha = new JLabel(String.format("Actualización: %s", fechaStr));
                lblFecha.setFont(lblFecha.getFont().deriveFont(Font.ITALIC));
                lblFecha.setForeground(fgColor);
                
                pEstacion.add(lblNombre);
                pEstacion.add(lblFecha);
                
                return pEstacion;
            }

            // CASO 2: Columna ESTADO (Label con fondo de color)
            if (value instanceof Apertura a) {
                JPanel pEstado = new JPanel();
                pEstado.setBackground(bgColor);
                
                JLabel lblApertura = new JLabel(String.format("  %s  ", a.name()));
                lblApertura.setOpaque(true);
                lblApertura.setFont(lblApertura.getFont().deriveFont(Font.BOLD, 14f));
                lblApertura.setForeground(Color.WHITE);
                
                // Color según apertura
                if (a.equals(Apertura.ABIERTA)) {
                    lblApertura.setBackground(Color.GREEN.darker());
                } else {
                    lblApertura.setBackground(Color.RED);
                }
                
                pEstado.add(lblApertura);
                
                return pEstado;
            }

            // CASO 3: Resto de columnas (JLabel por defecto)
            JLabel labelCelda = new JLabel();
            
            labelCelda.setOpaque(true);
            labelCelda.setBackground(bgColor);
            labelCelda.setForeground(fgColor);
            labelCelda.setHorizontalAlignment(JLabel.CENTER);

            if (value instanceof Ciudad c) {
				labelCelda.setText(c.getNombre());
				labelCelda.setHorizontalAlignment(JLabel.LEFT);
            } else if (value instanceof Pais p) {
                labelCelda.setIcon(new ImageIcon("resources/images/" + p.name() + ".png"));
            } else if (value instanceof EstadoEstacion estado && column == 4) {
                labelCelda.setIcon(new ImageIcon("resources/images/" + estado.getClima().name() + ".png"));
                labelCelda.setText(String.format(" %d °C", estado.getTemperatura()));
                if (estado.getTemperatura() < 0 && !isSelected) labelCelda.setForeground(Color.RED);
            } else if (value instanceof Estacion e && column == 5) {
                float kmAct = (e.getEstado() != null) ? e.getEstado().getKmEsquiables() : 0.0f;                
                labelCelda.setText(String.format("%.0f / %.0f", kmAct, e.getKmEsquiablesTotal()));
                
                if (kmAct > 0.0f) {
                	labelCelda.setForeground(Color.GREEN.darker());
                	labelCelda.setFont(labelCelda.getFont().deriveFont(Font.BOLD));
                } else {
                	labelCelda.setForeground(Color.RED);
                }
                	
            }

            return labelCelda;
        });
        
        tablaEstaciones.setRowHeight(40);
        tablaEstaciones.getColumnModel().getColumn(1).setPreferredWidth(220);
        tablaEstaciones.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaEstaciones.getTableHeader().setPreferredSize(new Dimension(0, 35));
		
		JScrollPane scrollPane = new JScrollPane(tablaEstaciones);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);

        // --- TAREA 3.2: Gestión de eventos ---
        tablaEstaciones.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == '+' && tablaEstaciones.getSelectedRow() != -1) {
                    Estacion sel = estaciones.get(tablaEstaciones.getSelectedRow());
                    JOptionPane.showMessageDialog(null, "Actualizando información de: " + sel.getNombre());
                }
            }
        });
        
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

		SwingUtilities.invokeLater(() -> new MainSwingSolucion(estaciones));
	}

}