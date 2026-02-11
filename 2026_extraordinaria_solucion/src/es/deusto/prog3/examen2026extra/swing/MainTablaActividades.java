package es.deusto.prog3.examen2026extra.swing;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import es.deusto.prog3.examen2026extra.Actividad;
import es.deusto.prog3.examen2026extra.Actividad.Intensidad;
import es.deusto.prog3.examen2026extra.Actividad.Tipo;

public class MainTablaActividades extends JFrame {

    private static final long serialVersionUID = 1L;

    public MainTablaActividades() {
        setTitle("Examen 2026 - JTable");
        setSize(720, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        List<Actividad> actividades = new ArrayList<>();
        actividades.add(new Actividad("Correr", 30, Tipo.CARDIO, Intensidad.MEDIA));
        actividades.add(new Actividad("Spinning", 45, Tipo.CARDIO, Intensidad.ALTA));
        actividades.add(new Actividad("Peso muerto", 20, Tipo.FUERZA, Intensidad.ALTA));
        actividades.add(new Actividad("Core", 15, Tipo.FUERZA, Intensidad.MEDIA));
        actividades.add(new Actividad("Estiramientos", 12, Tipo.MOVILIDAD, Intensidad.BAJA));

        TablaActividadesModel model = new TablaActividadesModel(actividades);
        JTable tabla = new JTable(model);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainTablaActividades().setVisible(true));
    }
}
