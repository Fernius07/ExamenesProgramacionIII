package es.deusto.prog.examen.swing.solucion;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import es.deusto.prog.examen.Persona;
import es.deusto.prog.examen.Persona.TipoAbono;

public class TablaPersonasSolucion extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tablaPersonas;

    private String abonoSeleccionado = null;

    public TablaPersonasSolucion(List<Persona> personas) {
        super("Tabla de personas");
        setSize(800, 600);

        TablaPersonasModeloSolucion modelo = new TablaPersonasModeloSolucion(personas);
        tablaPersonas = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(tablaPersonas);
        add(scrollPane);

        // TODO Tarea 1 - Swing: Añade aquí el código para la visualización
        tablaPersonas.setDefaultRenderer(Object.class, new TableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                
                JLabel label = new JLabel(value.toString());
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                if (column == 2) {
                    TipoAbono abono = (TipoAbono) value;
                    switch (abono) {
                        case SIMPLE:
                            label.setForeground(Color.ORANGE);
                            break;
                        case DUO:
                            label.setForeground(Color.BLUE);
                            break;
                        case COMPLETO:
                            label.setForeground(Color.GREEN);
                            break;
                        default:
                            break;
                    }

                    if (abonoSeleccionado != null && abonoSeleccionado.equals(abono.toString())) {
                        label.setBackground(Color.YELLOW);
                    }
                }
                return label;
            }
        });

        JPanel panelBotones = new JPanel(new FlowLayout());
        add(panelBotones, BorderLayout.NORTH);

        for (TipoAbono abono : TipoAbono.values()) {
            JButton boton = new JButton(abono.toString());
            panelBotones.add(boton);

            boton.addActionListener(e -> {
                abonoSeleccionado = abono.toString();
                tablaPersonas.repaint();
            });
        }
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
