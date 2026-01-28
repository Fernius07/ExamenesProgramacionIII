/**
 * Este código ha sido elaborado a partir de una versión generada con Gemini 3.
 * La versión final ha sido revisada y adaptada para garantizar su corrección
 * y la ausencia de errores.
 */

package es.deusto.ingenieria.esqui.jdbc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import es.deusto.ingenieria.esqui.domino.Ciudad.Pais;
import es.deusto.ingenieria.esqui.domino.Estacion;
import es.deusto.ingenieria.esqui.domino.EstadoEstacion;
import es.deusto.ingenieria.esqui.domino.EstadoEstacion.Apertura;
import es.deusto.ingenieria.esqui.domino.EstadoEstacion.Clima;

public class MainJDBC extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final GestorBD GESTOR_BD = new GestorBDSolucion();    
    
	private JTree treeEstaciones;
    private JRadioButton rbAbierta, rbCerrada;
    private JComboBox<Clima> cbClima;
    private JSpinner spTemperatura, spKmAbiertos;
    private JButton btnActualizar;
    private Estacion estacionSeleccionada;

    public MainJDBC(List<Estacion> estaciones) {
        setLayout(new BorderLayout());
        
        // Imágenes de íconos de apertura
        ImageIcon iconoAbierta = new ImageIcon(new ImageIcon("resources/images/ABIERTA.png")
        						.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        ImageIcon iconoCerrada = new ImageIcon(new ImageIcon("resources/images/CERRADa.png")
								.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		ImageIcon iconoDesconocida = new ImageIcon(new ImageIcon("resources/images/DESCONOCIDA.png")
								.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Estaciones de esquí");        
        Map<Pais, DefaultMutableTreeNode> nodosPais = new HashMap<>();
        Collections.sort(estaciones);
        
        estaciones.forEach(e -> {
            Pais p = e.getCiudad().getPais();
          
            if (!nodosPais.containsKey(p)) {
                DefaultMutableTreeNode nodoP = new DefaultMutableTreeNode(p);
                nodosPais.put(p, nodoP);
                root.add(nodoP);
            }
            
            nodosPais.get(p).add(new DefaultMutableTreeNode(e));
        });

        treeEstaciones = new JTree(new DefaultTreeModel(root));
        treeEstaciones.setCellRenderer((tree, value, sel, exp, leaf, row, focus) -> {
            DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            JLabel label = (JLabel) renderer.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, focus);

            if (node.getUserObject() instanceof Pais) {
            	Pais p = (Pais) node.getUserObject();            	
				label.setIcon(new ImageIcon(new ImageIcon(String.format("resources/images/%s.png", p.name()))
						.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
                label.setText(p.getNombre());
            } else if (node.getUserObject() instanceof Estacion) {
                Estacion e = (Estacion) node.getUserObject(); 
                label.setText(e.getNombre());
                
                Apertura apertura = e.getEstado() != null ? e.getEstado().getApertura() : Apertura.DESCONOCIDA;                                
                
                switch (apertura) {
					case ABIERTA:
						label.setIcon(iconoAbierta);
						label.setText(String.format("%s (%d km.)", e.getNombre(), (int) e.getEstado().getKmEsquiables()));
						label.setForeground(java.awt.Color.GREEN.darker());
						break;
					case CERRADA:
						label.setIcon(iconoCerrada);
						label.setForeground(java.awt.Color.RED.darker());
						break;
					case DESCONOCIDA:
					default:
						label.setIcon(iconoDesconocida);
						label.setForeground(java.awt.Color.YELLOW.darker());
						break;
				}                
            }
            
			if (sel) {
				label.setForeground(java.awt.Color.WHITE);
			}					
            
            return label;
        });

        JScrollPane scrollPane = new JScrollPane(treeEstaciones);
        scrollPane.setPreferredSize(new java.awt.Dimension(300, 0));
        add(scrollPane, BorderLayout.WEST);

        JPanel panelCentral = new JPanel(new BorderLayout());
        JPanel panelFormulario = new JPanel(new GridLayout(5, 1));  
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Editar Estado de Estación"));
        panelCentral.add(panelFormulario, BorderLayout.CENTER);
        
        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(new JLabel("Apertura: "));
        rbAbierta = new JRadioButton("Abierta");
        rbCerrada = new JRadioButton("Cerrada");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rbAbierta);
        buttonGroup.add(rbCerrada);
        p1.add(rbAbierta);
        p1.add(rbCerrada);
        panelFormulario.add(p1);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("Clima: "));
        cbClima = new JComboBox<>(Clima.values());
        
        cbClima.setRenderer((list, value, index, isSelected, cellHasFocus) -> {
            Color bgColor = isSelected ? list.getSelectionBackground() : list.getBackground();
            Color fgColor = isSelected ? list.getSelectionForeground() : list.getForeground();
        	
        	JLabel label = new JLabel();
        	label.setPreferredSize(new Dimension(200, 40));
            label.setOpaque(true);
            label.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
            label.setBackground(bgColor);
            label.setForeground(fgColor);
            
            if (value != null) {
                Clima c = (Clima) value;                               
				label.setIcon(new ImageIcon(new ImageIcon("resources/images/" + c.name() + ".png")
						.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
				label.setText(c.name());
            }
            
            return label;
        });
        
        p2.add(cbClima);
        panelFormulario.add(p2);

        JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3.add(new JLabel("Temperatura (°C): "));
        spTemperatura = new JSpinner(new SpinnerNumberModel(0, -40, 40, 1));
        panelFormulario.add(p3);
        p3.add(spTemperatura);

        JPanel p4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p4.add(new JLabel("KM Esquiables abiertos: "));
        spKmAbiertos = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 500.0, 0.1));
        p4.add(spKmAbiertos);
        panelFormulario.add(p4);

        JPanel p5 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnActualizar = new JButton("Actualizar estado");
        btnActualizar.setEnabled(false);
        p5.add(btnActualizar);
        panelFormulario.add(p5);

        add(panelCentral, BorderLayout.CENTER);

        setFormularioHabilitado(false);
        
        treeEstaciones.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeEstaciones.getLastSelectedPathComponent();
            
            if (node != null && node.getUserObject() instanceof Estacion) {            	
                estacionSeleccionada = (Estacion) node.getUserObject();                
                panelFormulario.setBorder(BorderFactory.createTitledBorder(String.format("Editar estado de Estación '%s'", estacionSeleccionada.getNombre())));
                setFormularioHabilitado(true);
                
                SpinnerNumberModel sm = (SpinnerNumberModel) spKmAbiertos.getModel();
                sm.setMaximum((double) estacionSeleccionada.getKmEsquiablesTotal());

                EstadoEstacion estado = estacionSeleccionada.getEstado();            
            	Apertura apertura = estado != null ? estado.getApertura() : Apertura.DESCONOCIDA;
            	
            	switch (apertura) {
					case ABIERTA:
						rbAbierta.setSelected(true);
						break;
					case CERRADA:
						rbCerrada.setSelected(true);
						break;
					case DESCONOCIDA:
					default:
						rbAbierta.setSelected(false);
						rbCerrada.setSelected(false);
						break;
            	}
                
                if (estado != null) {
                	cbClima.setSelectedItem(estado.getClima());
                    spTemperatura.setValue(estado.getTemperatura());
                    spKmAbiertos.setValue((double) estado.getKmEsquiables());
                } else {
                	buttonGroup.clearSelection();
                    cbClima.setSelectedIndex(0);
                    spTemperatura.setValue(0);
                    spKmAbiertos.setValue(0.0);
                }
            }  else {
            	panelFormulario.setBorder(BorderFactory.createTitledBorder("Editar estado de Estación"));
                estacionSeleccionada = null;
                buttonGroup.clearSelection();
                cbClima.setSelectedIndex(0);
                spTemperatura.setValue(0);
                spKmAbiertos.setValue(0.0);
                setFormularioHabilitado(false);
            }
        });

        btnActualizar.addActionListener(e -> {
            if (estacionSeleccionada != null) {
                Apertura ap = rbAbierta.isSelected() ? Apertura.ABIERTA : Apertura.CERRADA;
                Clima cl = (Clima) cbClima.getSelectedItem();
                int temp = (int) spTemperatura.getValue();
                float km = ((Double) spKmAbiertos.getValue()).floatValue();                
                EstadoEstacion nuevoEstado = new EstadoEstacion(cl, ap, temp, km, new Date());
                
                if (GESTOR_BD.actualizarEstadoEstacion(estacionSeleccionada.getNombre(), nuevoEstado)) {
					JOptionPane.showMessageDialog(this, "Estado actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	                estacionSeleccionada.setEstado(nuevoEstado);
				} else {
					JOptionPane.showMessageDialog(this, "Error al actualizar el estado.", "Error", JOptionPane.ERROR_MESSAGE);
				}
            }
        });
        
		JLabel lblFooter = new JLabel("Iconos creados por Freepik - https://www.flaticon.com/authors/freepik");
		lblFooter.setForeground(Color.BLUE);
		lblFooter.setHorizontalAlignment(JLabel.CENTER);
		lblFooter.setBorder(new LineBorder(Color.GRAY));		
		add(lblFooter, BorderLayout.SOUTH);
        
        setTitle("Gestión de estado de estaciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setFormularioHabilitado(boolean habilitado) {
        rbAbierta.setEnabled(habilitado);
        rbCerrada.setEnabled(habilitado);
        cbClima.setEnabled(habilitado);
        spTemperatura.setEnabled(habilitado);
        spKmAbiertos.setEnabled(habilitado);
        btnActualizar.setEnabled(habilitado);
    }
    
    public static void main(String[] args) {
        List<Estacion> estaciones = GESTOR_BD.getEstaciones(GESTOR_BD.getCiudades());        
        SwingUtilities.invokeLater(() -> new MainJDBC(estaciones));
    }
}