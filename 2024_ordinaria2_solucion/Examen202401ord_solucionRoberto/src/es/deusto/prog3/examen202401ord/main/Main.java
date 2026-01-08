package es.deusto.prog3.examen202401ord.main;

import javax.swing.SwingUtilities;

import es.deusto.prog3.examen202401ord.datos.GestorPersistencia;
import es.deusto.prog3.examen202401ord.gui.VentanaChat;
import es.deusto.prog3.examen202401ord.gui.VentanaLogin;
import es.deusto.prog3.examen202401ord.logica.GestorUsuarios;

/** Clase principal de examen
 * @author prog3 @ ingenieria.deusto.es
 */
public class Main {

	private static GestorPersistencia bd = new GestorPersistencia();
	private static GestorUsuarios gestor = new GestorUsuarios( bd );
	
	public static final String NOMBRE_CHAT = "ChatNoGPT";
	public static final String NOMBRE_BD = "examen2401o";
	
    public static void main(String[] args) {
    	bd.init( NOMBRE_BD );
    	if (!bd.existeBD( NOMBRE_BD )) {
        	bd.crearTablasBD( null, true );
    	}
    	gestor = new GestorUsuarios( bd );
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	VentanaLogin v = new VentanaLogin( gestor );
            	new VentanaChat( gestor );
                v.setVisible( true );
            }
        });
    }
    
}
