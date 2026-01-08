package aplicacion;

import javax.swing.SwingUtilities;

import gui.VentanaPrincipal;

public class Main {

	// inicio de la aplicación
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> { new VentanaPrincipal(); });
	}

}
