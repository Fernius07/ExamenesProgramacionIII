package es.deusto.prog.examen.swing;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import es.deusto.prog.examen.Persona;

public class TablaPersonas extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tablaPersonas;

    public TablaPersonas(List<Persona> personas) {
        super("Tabla de personas");
        setSize(800, 600);

        TablaPersonasModelo modelo = new TablaPersonasModelo(personas);
        tablaPersonas = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(tablaPersonas);
        add(scrollPane);

        // TODO Tarea 1 - Swing: Añade aquí el código para la visualización
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
