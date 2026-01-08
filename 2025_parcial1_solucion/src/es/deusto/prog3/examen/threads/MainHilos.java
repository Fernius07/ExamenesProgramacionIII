package es.deusto.prog3.examen.threads;

import javax.swing.SwingUtilities;

public class MainHilos {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(VentanaJuego::new);
	}

}
