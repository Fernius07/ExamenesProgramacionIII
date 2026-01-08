package es.deusto.prog3.examen202401ord.main;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;

import javax.swing.SwingUtilities;

import es.deusto.prog3.examen202401ord.datos.Conversacion;
import es.deusto.prog3.examen202401ord.datos.Frase;
import es.deusto.prog3.examen202401ord.datos.GestorPersistencia;
import es.deusto.prog3.examen202401ord.gui.VentanaChat;
import es.deusto.prog3.examen202401ord.gui.VentanaLogin;
import es.deusto.prog3.examen202401ord.logica.GestorUsuarios;

/** Clase principal de examen
 * @author prog3 @ ingenieria.deusto.es
 */
public class MainPrueba {

	private static GestorPersistencia bd = new GestorPersistencia();
//	private static GestorUsuarios gestor = new GestorUsuarios( bd );
	
	public static final String NOMBRE_CHAT = "ChatNoGPT";
	public static final String NOMBRE_BD = "examen2401o";
	
    public static void main(String[] args) {
    	bd.init( NOMBRE_BD );
        crearTablasBDPrueba(bd);
        Conversacion c = new Conversacion( "b", System.currentTimeMillis() );
        c.getFrases().add( new Frase( "b", NOMBRE_CHAT, System.currentTimeMillis(), "test", 1000000) );
        bd.anyadirConv( c, null );
    //	gestor = new GestorUsuarios( bd );
    	List<Conversacion> l = bd.cargaConversaciones( "a" );
		int idConvAnterior = -1;
		long fechaConvAnterior = -1;
		boolean err = false;
		boolean errIdsF = false;
		boolean errOrdF = false;
		boolean errIdsC = false;
		if (l.size()!=2) {
			System.out.println( "2 convs NO = " + l.size() );
			err = true;
		}
		int cont = 0;
		int id = 1;
		for (Conversacion conv : l) {
			if (cont==0 && conv.getId()!=1) {
				System.out.println( "Error: conv no ordenada por id " ); err = true;
			}
			if (conv.getId()==1) {
				if (conv.getFrases().size()!=6) { System.out.println( "Error: frases conv 1 = " + conv.getFrases().size() ); err = true; }
			} else if (conv.getId()==2) {
				if (conv.getFrases().size()!=12) { System.out.println( "Error: frases conv 1 = " + conv.getFrases().size() ); err = true; }
			} else {
				if (!errIdsC) System.out.println( "Error: id conversación <> 1 o 2" ); errIdsC = true; err = true;
			}
			long fecha = 0;
			for (Frase frase : conv.getFrases()) {
				if (!frase.getEmisor().equals("a") && !frase.getReceptor().equals("a")) { System.out.println( "ERROR USUARIO NO A" ); err = true; }
				if ((cont%2==0 && !frase.getEmisor().equals("a")) || (cont%2!=0 && !frase.getReceptor().equals("a"))) {
					{ System.out.println( "ERROR ORDEN A-CHATNOGPT" ); err = true; }
				}
				if (id != frase.getId()) {
					{ if (!errIdsF) System.out.println( "ERROR IDS FRASES! " ); err = true; errIdsF = true; }
				}
				// if (err) { System.out.println( frase ); }
				cont++;
				id++;
				if (fecha >= frase.getFecha()) {
					if (!errOrdF) System.out.println( "Error: frases no ordenadas por fecha" ); err = true; errOrdF = true;
				}
				fecha = frase.getFecha();
			}
		}
    	if (!err) {
    		System.out.println( "OK!" );
    	}
    	
//    	SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//            	VentanaLogin v = new VentanaLogin( gestor );
//            	new VentanaChat( gestor );
//                v.setVisible( true );
//            }
//        });
    }

    
	public static void crearTablasBDPrueba( GestorPersistencia bd ) {
		String com = null;
		try {
			Statement statement = bd.getConnection().createStatement();
			statement.executeUpdate("drop table if exists usuario");
			com = "create table usuario " +
					"(nick string, password string, nombre string, apellidos string" +
					", telefono integer, fechaultimologin bigint, emails string)";
			statement.executeUpdate( com );
			statement.executeUpdate("drop table if exists frase");
			com = "create table frase " +
					"(id INTEGER PRIMARY KEY AUTOINCREMENT, emisor string, receptor string, fecha bigint, texto varchar, idConv int)";
			statement.executeUpdate( com );
			statement.executeUpdate("drop table if exists conversacion");
			com = "create table conversacion " +
					"(id INTEGER PRIMARY KEY AUTOINCREMENT, usuario string, fecha bigint)";
			statement.executeUpdate( com );
				com = "insert into usuario ( nick, password, nombre, apellidos, telefono, fechaultimologin, emails ) " +
					"values ('admin', 'admin', 'Admin', 'Admin Admin', 123456789, -1, 'admin@deusto.es')";
				statement.executeUpdate( com );
				com = "insert into usuario ( nick, password, nombre, apellidos, telefono, fechaultimologin, emails ) " +
						"values ('a', 'a', 'A', 'B C', 987654321, -1, 'a@deusto.es')";
				statement.executeUpdate( com );
				com = "insert into usuario ( nick, password, nombre, apellidos, telefono, fechaultimologin, emails ) " +
						"values ('b', 'b', 'B', 'C D', 978645312, -1, 'b@deusto.es')";
				statement.executeUpdate( com );
				
				com = "insert into conversacion ( id, usuario, fecha ) " +
					  "values (2, 'a', 1704111894312)";
				statement.executeUpdate( com );
				com = "insert into conversacion ( id, usuario, fecha ) " +
						  "values (1, 'a', 1704101209807)";
					statement.executeUpdate( com );
				
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (4, 'ChatNoGPT', 'a', 1704101212812, 'Eso suena como un gran cambio ¿Cómo lo enfrentaste?', 1)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (5, 'a', 'ChatNoGPT', 1704101257447, 'Con mucha entereza', 1)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (6, 'ChatNoGPT', 'a', 1704101257702, 'Eso suena complicado ¿puedes simplificarlo?', 1)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (1, 'a', 'ChatNoGPT', 1704101189263, 'Tengo dudas sobre la existencia', 1)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (2, 'ChatNoGPT', 'a', 1704101189718, '¿Cómo equilibras tus emociones en situaciones como esa?', 1)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (3, 'a', 'ChatNoGPT', 1704101212372, 'intentando mirar hacia adelante', 1)";
				statement.executeUpdate( com );
				
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (17, 'a', 'ChatNoGPT', 1704111997577, 'Pues mareado, desconectado, desanimado...', 2)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (18, 'ChatNoGPT', 'a', 1704111997950, 'Eso parece importante para ti. ¿Por qué es así?', 2)";	
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
				      "values (7, 'a', 'ChatNoGPT', 1704111899031, 'No me encuentro bien esta mañana', 2)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (8, 'ChatNoGPT', 'a', 1704111899559, 'Eso es bastante curioso. ¿Puedes explicar por qué?', 2)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (9, 'a', 'ChatNoGPT', 1704111911993, 'Porque ayer fue una noche de locura', 2)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (10, 'ChatNoGPT', 'a', 1704111912457, '¿Cómo mantienes tu motivación en situaciones así?', 2)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (11, 'a', 'ChatNoGPT', 1704111930777, 'Pues a base de refrescos de cola sin azúcar', 2)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (12, 'ChatNoGPT', 'a', 1704111931278, 'Eso suena complicado. ¿Puedes simplificarlo?', 2)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (13, 'a', 'ChatNoGPT', 1704111937977, 'A base de cola', 2)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (14, 'ChatNoGPT', 'a', 1704111938383, '¿Cómo afecta eso tu vida diaria?', 2)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (15, 'a', 'ChatNoGPT', 1704111948984, 'Fatal, luego estoy hecho un trapo', 2)";
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (16, 'ChatNoGPT', 'a', 1704111949382, 'Eso suena como un gran cambio ¿Cómo lo enfrentaste?', 2)";
				statement.executeUpdate( com );
		} catch (SQLException e) {
			System.out.println( "ERROR EXCEPCION " + com );
		}
	}
    
}
