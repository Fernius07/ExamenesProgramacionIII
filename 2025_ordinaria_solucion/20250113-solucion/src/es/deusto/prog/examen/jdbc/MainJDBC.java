package es.deusto.prog.examen.jdbc;

import javax.swing.SwingUtilities;

public class MainJDBC {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	VentanaGestorAbonos gestorPersonas = new VentanaGestorAbonos();
        	gestorPersonas.setVisible(true);
        });
    }
}