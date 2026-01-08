package es.deusto.prog3.examen202401ord.datos;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Gestor de persistencia en base de datos
 * @author prog3 @ ingenieria.deusto.es
 */
public class GestorPersistencia {

	private Connection conexionBD = null;
	private Statement sentenciaBD = null;
	private Logger logger = null;
	
	/** Inicializa una BD SQLITE y devuelve una conexión con ella. Genera un logger por defecto para registrar las operaciones de base de datos
	 * @param nombreBD	Nombre de fichero de la base de datos
	 * @return	Conexión con la base de datos indicada. Si hay algún error, se devuelve null
	 */
	public Connection init( String nombreBD ) {
		try {
		    Class.forName("org.sqlite.JDBC");
		    Connection con = DriverManager.getConnection("jdbc:sqlite:" + nombreBD + ".db" );
			logger = Logger.getLogger( "GestorPersistencia-" + nombreBD );
			conexionBD = con;
		    return con;
		} catch (ClassNotFoundException | SQLException | NullPointerException e) {
			conexionBD = null;
			if (logger!=null) logger.log( Level.SEVERE, "Error en conexión de base de datos " + nombreBD + ".db", e );
			return null;
		}
	}
	
	/** Informa si ya existe el fichero de base de datos
	 * @param nombreBD	Nombre de base de datos
	 * @return	true si ya existe, false si no
	 */
	public boolean existeBD( String nombreBD ) {
		File fichero = new File( nombreBD + ".db" );
		return fichero.exists() && fichero.length()>0;
	}
	
	/** Modifica el logger por defecto de la base de datos
	 * @param logger	Nuevo logger. Si es null se desactiva el registro
	 */
	public void setLogger( Logger logger ) {
		this.logger = logger;
	}
	
	/** Devuelve la conexión abierta con base de datos SQLite
	 * @return	Conexión actual, null si no está abierta
	 */
	public Connection getConnection() {
		return conexionBD;
	}

	// Coge statement por defecto o genera uno si no existía
	private Statement tomarOCrearStatement() throws SQLException {
		if (sentenciaBD!=null) {
			return sentenciaBD;
		}
		if (conexionBD==null) {
			throw new SQLException( "Conexión BD no inicializada" );
		}
		sentenciaBD = conexionBD.createStatement();
		sentenciaBD.setQueryTimeout(30);  // poner timeout 30 msg
		return sentenciaBD;
	}
	
	/** Crea las tablas correspondientes al gestor de persistencia en una base de datos (si ya existían, las borra y las vuelve a crear)
	 * La conexión ya debe estar creada y abierta la base de datos - {@link #init(String)}
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario). Si es null se usa sentencia por defecto
	 * @param datosPrueba	true si se quieren crear con los datos de prueba de las tablas
	 * @return	sentencia de trabajo si se crea correctamente, null si hay cualquier error
	 */
	public Statement crearTablasBD( Statement statement, boolean datosPrueba ) {
		try {
			if (statement==null) {
				statement = tomarOCrearStatement();
			}
			statement.executeUpdate("drop table if exists usuario");
			String com = "create table usuario " +
					"(nick string, password string, nombre string, apellidos string" +
					", telefono integer, fechaultimologin bigint, emails string)";
			statement.executeUpdate( com );
			if (logger!=null) logger.log( Level.INFO, "BD: " + com );
			statement.executeUpdate("drop table if exists frase");
			com = "create table frase " +
					"(id INTEGER PRIMARY KEY AUTOINCREMENT, emisor string, receptor string, fecha bigint, texto varchar, idConv int)";
			statement.executeUpdate( com );
			if (logger!=null) logger.log( Level.INFO, "BD: " + com );
			statement.executeUpdate("drop table if exists conversacion");
			com = "create table conversacion " +
					"(id INTEGER PRIMARY KEY AUTOINCREMENT, usuario string, fecha bigint)";
			statement.executeUpdate( com );
			if (logger!=null) logger.log( Level.INFO, "BD: " + com );
			sentenciaBD = statement;
			
			if (datosPrueba) {
				com = "insert into usuario ( nick, password, nombre, apellidos, telefono, fechaultimologin, emails ) " +
					"values ('admin', 'admin', 'Admin', 'Admin Admin', 123456789, -1, 'admin@deusto.es')";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into usuario ( nick, password, nombre, apellidos, telefono, fechaultimologin, emails ) " +
						"values ('a', 'a', 'A', 'B C', 987654321, -1, 'a@deusto.es')";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into usuario ( nick, password, nombre, apellidos, telefono, fechaultimologin, emails ) " +
						"values ('b', 'b', 'B', 'C D', 978645312, -1, 'b@deusto.es')";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				
				com = "insert into conversacion ( id, usuario, fecha ) " +
					  "values (1, 'a', 1704101209807)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into conversacion ( id, usuario, fecha ) " +
					  "values (2, 'a', 1704111894312)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (1, 'a', 'ChatNoGPT', 1704101189263, 'Tengo dudas sobre la existencia', 1)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (2, 'ChatNoGPT', 'a', 1704101189718, '¿Cómo equilibras tus emociones en situaciones como esa?', 1)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (3, 'a', 'ChatNoGPT', 1704101212372, 'intentando mirar hacia adelante', 1)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (4, 'ChatNoGPT', 'a', 1704101212812, 'Eso suena como un gran cambio ¿Cómo lo enfrentaste?', 1)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (5, 'a', 'ChatNoGPT', 1704101257447, 'Con mucha entereza', 1)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (6, 'ChatNoGPT', 'a', 1704101257702, 'Eso suena complicado ¿puedes simplificarlo?', 1)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
				      "values (7, 'a', 'ChatNoGPT', 1704111899031, 'No me encuentro bien esta mañana', 2)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (8, 'ChatNoGPT', 'a', 1704111899559, 'Eso es bastante curioso. ¿Puedes explicar por qué?', 2)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (9, 'a', 'ChatNoGPT', 1704111911993, 'Porque ayer fue una noche de locura', 2)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (10, 'ChatNoGPT', 'a', 1704111912457, '¿Cómo mantienes tu motivación en situaciones así?', 2)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (11, 'a', 'ChatNoGPT', 1704111930777, 'Pues a base de refrescos de cola sin azúcar', 2)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (12, 'ChatNoGPT', 'a', 1704111931278, 'Eso suena complicado. ¿Puedes simplificarlo?', 2)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (13, 'a', 'ChatNoGPT', 1704111937977, 'A base de cola', 2)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (14, 'ChatNoGPT', 'a', 1704111938383, '¿Cómo afecta eso tu vida diaria?', 2)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (15, 'a', 'ChatNoGPT', 1704111948984, 'Fatal, luego estoy hecho un trapo', 2)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (16, 'ChatNoGPT', 'a', 1704111949382, 'Eso suena como un gran cambio ¿Cómo lo enfrentaste?', 2)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (17, 'a', 'ChatNoGPT', 1704111997577, 'Pues mareado, desconectado, desanimado...', 2)";
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
				com = "insert into frase ( id, emisor, receptor, fecha, texto, idConv ) " +
					  "values (18, 'ChatNoGPT', 'a', 1704111997950, 'Eso parece importante para ti. ¿Por qué es así?', 2)";	
				if (logger!=null) logger.log( Level.INFO, "BD: " + com );
				statement.executeUpdate( com );
			}
			return statement;
		} catch (SQLException e) {
			if (logger!=null) logger.log( Level.SEVERE, "Error en creación de tablas", e );
			return null;
		}
	}
	
	/** Añade el usuario a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param usuario	Usuario a añadir a tabla
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario). Si es null se usa sentencia por defecto
	 * @return	true si la inserción es correcta, false en caso contrario
	 */
	public boolean anyadirUsuario( Usuario usuario, Statement st ) {
		String sentSQL = null;
		try {
			if (st==null) {
				st = tomarOCrearStatement();
			}
			String listaEms = "";
			String sep = "";
			for (String email : usuario.getListaEmails()) {
				listaEms = listaEms + sep + email;
				sep = ",";
			}
			sentSQL = "insert into usuario values(" +
					"'" + usuario.getNick() + "', " +
					"'" + usuario.getPassword() + "', " +
					"'" + usuario.getNombre() + "', " +
					"'" + usuario.getApellidos() + "', " +
					usuario.getTelefono() + ", " +
					usuario.getFechaUltimoLogin() + ", " +
					"'" + listaEms + "')";
			int val = st.executeUpdate( sentSQL );
			if (logger!=null) logger.log( Level.INFO, "Añadida " + val + " fila a base de datos\t" + sentSQL );
			if (val!=1) return false;  // Se tiene que añadir 1 - error si no
			return true;
		} catch (SQLException e) {
			if (logger!=null) logger.log( Level.SEVERE, "Error en añadido de usuario: " + sentSQL, e );
			return false;
		}
	}

	/** Realiza una consulta a la tabla abierta de usuarios de la BD, usando la sentencia SELECT de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario). Si es null se usa sentencia por defecto
	 * @param clausulaWhere	Selección particular de datos con sintaxis where de sql (vacía o null para seleccionar toda la tabla)
	 * @return	lista de usuarios cargados desde la base de datos, null si hay cualquier error
	 */
	public ArrayList<Usuario> leerUsuarios( Statement st, String clausulaWhere ) {
		String sentSQL = null;
		ArrayList<Usuario> ret = new ArrayList<>();
		try {
			if (st==null) {
				st = tomarOCrearStatement();
			}
			sentSQL = "select * from usuario";
			if (clausulaWhere!=null && !clausulaWhere.equals(""))
				sentSQL = sentSQL + " where " + clausulaWhere;
			ResultSet rs = st.executeQuery( sentSQL );
			if (logger!=null) logger.log( Level.INFO, "Búsqueda en base de datos\t" + sentSQL );
			while (rs.next()) {
				Usuario u = new Usuario();
				u.setNick( rs.getString( "nick" ) );
				u.setPassword( rs.getString( "password" ) );
				u.setNombre( rs.getString( "nombre" ) );
				u.setApellidos( rs.getString( "apellidos" ) );
				u.setTelefono( rs.getInt( "telefono" ) );
				u.setFechaUltimoLogin( rs.getLong( "fechaultimologin" ) );
				ArrayList<String> l = new ArrayList<String>(); 
				StringTokenizer stt = new StringTokenizer( rs.getString("emails"), "," );
				while (stt.hasMoreTokens()) {
					l.add( stt.nextToken() );
				}
				u.setListaEmails( l );
				ret.add( u );
			}
			rs.close();
			return ret;
		} catch (SQLException e) {
			if (logger!=null) logger.log( Level.SEVERE, "Error en búsqueda de usuarios: " + sentSQL, e );
			return null;
		}
	}

	/** Añade una frase a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param frase	Frase a añadir a tabla. Se actualiza con el id autogenerado en la base de datos
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario). Si es null se usa sentencia por defecto
	 * @return	true si la inserción es correcta, false en caso contrario
	 */
	public boolean anyadirFrase( Frase frase, Statement st ) {
		String sentSQL = null;
		try {
			if (st==null) {
				st = tomarOCrearStatement();
			}
			sentSQL = "insert into frase ( emisor, receptor, fecha, texto, idConv ) values (" +
					"'" + frase.getEmisor() + "', " +
					"'" + frase.getReceptor() + "', " +
					frase.getFecha() + ", " +
					"'" + frase.getTexto() + "', " +
					frase.getIdConv() + ")";
			int val = st.executeUpdate( sentSQL );
			if (logger!=null) logger.log( Level.INFO, "Añadida " + val + " fila a base de datos\t" + sentSQL );
			if (val!=1) return false;  // Se tiene que añadir 1 - error si no
			
			// Actualizar el id de la frase con el autogenerado
			ResultSet rrss = st.getGeneratedKeys();  // Genera un resultset ficticio con las claves generadas del último comando
			rrss.next();  // Avanza a la única fila 
			int pk = rrss.getInt( 1 );  // Coge la única columna (la primary key autogenerada)
			frase.setId( pk );
			
			return true;
		} catch (SQLException e) {
			if (logger!=null) logger.log( Level.SEVERE, "Error en añadido de usuario: " + sentSQL, e );
			return false;
		}
	}

	/** Realiza una consulta a la tabla abierta de frases de la BD, usando la sentencia SELECT de SQL
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario). Si es null se usa sentencia por defecto
	 * @param clausulaWhere	Selección particular de datos con sintaxis where de sql (vacía o null para seleccionar toda la tabla)
	 * @return	lista de frases cargadas desde la base de datos, null si hay cualquier error
	 */
	public ArrayList<Frase> leerFrases( Statement st, String clausulaWhere ) {
		String sentSQL = null;
		ArrayList<Frase> ret = new ArrayList<>();
		try {
			if (st==null) {
				st = tomarOCrearStatement();
			}
			sentSQL = "select * from frase";
			if (clausulaWhere!=null && !clausulaWhere.equals(""))
				sentSQL = sentSQL + " where " + clausulaWhere;
			ResultSet rs = st.executeQuery( sentSQL );
			if (logger!=null) logger.log( Level.INFO, "Búsqueda en base de datos\t" + sentSQL );
			while (rs.next()) {
				Frase f = new Frase();
				f.setId( rs.getInt( "id" ) );
				f.setEmisor( rs.getString( "emisor" ) );
				f.setReceptor( rs.getString( "receptor" ) );
				f.setFecha( rs.getLong( "fecha" ) );
				f.setTexto( rs.getString( "texto" ) );
				f.setIdConv( rs.getInt( "idConv" ) );
				ret.add( f );
			}
			rs.close();
			return ret;
		} catch (SQLException e) {
			if (logger!=null) logger.log( Level.SEVERE, "Error en búsqueda en base de datos: " + sentSQL, e );
			return null;
		}
	}
	
	/** Cierra la base de datos abierta
	 */
	public void cerrarBD() {
		try {
			if (sentenciaBD!=null) sentenciaBD.close();
			if (conexionBD!=null) conexionBD.close();
		} catch (SQLException e) {
			if (logger!=null) logger.log( Level.SEVERE, "Error en cierre de BD", e );
		}
	}

	/** Añade una conversación a la tabla abierta de BD, usando la sentencia INSERT de SQL
	 * @param conv	Conversación a añadir a tabla. Se actualiza con el id autogenerado en la base de datos
	 * @param st	Sentencia ya abierta de Base de Datos (con la estructura de tabla correspondiente al usuario). Si es null se usa sentencia por defecto
	 * @return	true si la inserción es correcta, false en caso contrario
	 */
	public boolean anyadirConv( Conversacion conv, Statement st ) {
		String sentSQL = null;
		try {
			if (st==null) {
				st = tomarOCrearStatement();
			}
			sentSQL = "insert into conversacion ( usuario, fecha ) values (" +
					"'" + conv.getNickUsuario() + "', " +
					conv.getFecha() + ")";
			int val = st.executeUpdate( sentSQL );
			if (logger!=null) logger.log( Level.INFO, "Añadida " + val + " fila a base de datos\t" + sentSQL );
			if (val!=1) return false;  // Se tiene que añadir 1 - error si no
			
			// Actualizar el id de la conversación con el autogenerado
			ResultSet rrss = st.getGeneratedKeys();  // Genera un resultset ficticio con las claves generadas del último comando
			rrss.next();  // Avanza a la única fila 
			int pk = rrss.getInt( 1 );  // Coge la única columna (la primary key autogenerada)
			conv.setId( pk );
			
			return true;
		} catch (SQLException e) {
			if (logger!=null) logger.log( Level.SEVERE, "Error en añadido de usuario: " + sentSQL, e );
			return false;
		}
	}

	
	/** Carga las conversaciones de un usuario desde la BD, usando SELECT en las tablas de conversacion y frase
	 * @param nickUsuario	Nick del usuario del que cargar conversaciones
	 * @return	lista de conversaciones cargadas desde la base de datos, null si hay cualquier error
	 */
	public ArrayList<Conversacion> cargaConversaciones( String nickUsuario ) {
		// TODO T1
		
		try {
			ArrayList<Conversacion> result = new ArrayList<>();
			
			Conversacion conversacion = null;
			ArrayList<Frase> frases = null;
			Frase frase = null;
			String sqlConversaciones = String.format("SELECT * FROM conversacion WHERE usuario = \'%s\' ORDER BY id ASC", nickUsuario);			
			String sqlFrases = "SELECT * FROM frase WHERE idConv = %d ORDER BY fecha ASC";
			Statement stmtFrases = null;
			ResultSet rsFrases = null;			
			
			// Se recuperan las conversaciones y se añaden al resultado
			Statement stmtConversaciones = conexionBD.createStatement();			
			ResultSet rsConversaciones = stmtConversaciones.executeQuery(sqlConversaciones);			
			
			while (rsConversaciones.next()) {
				// Se crea la nueva conversación
				conversacion = new Conversacion(rsConversaciones.getString("usuario"), rsConversaciones.getLong("fecha"));
				// Se actualiza el Id ya que no se inicializa en el constructor 
				conversacion.setId(rsConversaciones.getInt("id"));
				
				//Se recuperan las frases de la conversación
				frases = new ArrayList<>();
				stmtFrases = conexionBD.createStatement();
				rsFrases = stmtFrases.executeQuery(String.format(sqlFrases, conversacion.getId()));
				
				while (rsFrases.next()) {
					frase = new Frase(rsFrases.getString("emisor"),
						     rsFrases.getString("receptor"),									  
						     rsFrases.getLong("fecha"),
						     rsFrases.getString("texto"),
						     rsFrases.getInt("idConv"));					
					// Se actualiza el Id ya que no se inicializa en el constructor.
					frase.setId(rsFrases.getInt("id"));
					
					// Se crean las frases y se añaden al Arraylist frases 
					frases.add(frase);					
				}
				
				// Se liberan los recursos JDBC de la consulta de frases
				rsFrases.close();
				stmtFrases.close();
				
				// Se añaden las frases a la conversación
				conversacion.setFrases(frases);
				
				// Se añade la conversación al resultado
				result.add(conversacion);				
			}
			
			// Se liberan los recursos JDBC de la consulta de conversaciones
			rsConversaciones.close();
			stmtConversaciones.close();
			
			return result;
		} catch (Exception ex) {
			if (logger!=null) logger.log( Level.SEVERE, 
										  String.format("Error recuperando conversaciones de '%s': %s", nickUsuario, ex.getMessage()));
			return null;
		}
		
		// Modificar las conversaciones de prueba recogiéndolas en su lugar desde la base de datos
		///return generaConversacionesPrueba();  // quitar esta línea y sustituir por proceso de BD
	}
		// Devuelve lista de dos conversaciones de prueba
		protected ArrayList<Conversacion> generaConversacionesPrueba() {
			ArrayList<Conversacion> ret = new ArrayList<>();
			ArrayList<Frase> lF = new ArrayList<Frase>();
			lF.add( new Frase( "a", "ChatNoGPT", 1704101189263L, "[ATENCIÓN: DATOS DE PRUEBA] Tengo dudas sobre la existencia", 1 ) );
			lF.add( new Frase( "ChatNoGPT", "a", 1704101189718L, "¿Cómo equilibras tus emociones en situaciones como esa?", 1 ) );
			lF.add( new Frase( "a", "ChatNoGPT", 1704101212372L, "intentando mirar hacia adelante", 1 ) );
			lF.add( new Frase( "ChatNoGPT", "a", 1704101212812L, "Eso suena como un gran cambio ¿Cómo lo enfrentaste?", 1 ) );
			lF.add( new Frase( "a", "ChatNoGPT", 1704101257447L, "Con mucha entereza", 1 ) );
			lF.add( new Frase( "ChatNoGPT", "a", 1704101257702L, "Eso suena complicado ¿puedes simplificarlo?", 1 ) );
			Conversacion c = new Conversacion( "a", 1704101209807L );
			c.setId( 1 );
			c.getFrases().addAll( lF );
			ret.add( c );

			lF = new ArrayList<Frase>();
			lF.add( new Frase( "a", "ChatNoGPT", 1704111899031L, "[ATENCIÓN: DATOS DE PRUEBA] No me encuentro bien esta mañana", 2 ) );
			lF.add( new Frase( "ChatNoGPT", "a", 1704111899559L, "Eso es bastante curioso. ¿Puedes explicar por qué?", 2 ) );
			lF.add( new Frase( "a", "ChatNoGPT", 1704111911993L, "Porque ayer fue una noche de locura", 2 ) );
			lF.add( new Frase( "ChatNoGPT", "a", 1704111912457L, "¿Cómo mantienes tu motivación en situaciones así?", 2 ) );
			lF.add( new Frase( "a", "ChatNoGPT", 1704111930777L, "Pues a base de refrescos de cola sin azúcar", 2 ) );
			lF.add( new Frase( "ChatNoGPT", "a", 1704111931278L, "Eso suena complicado. ¿Puedes simplificarlo?", 2 ) );
			lF.add( new Frase( "a", "ChatNoGPT", 1704111937977L, "A base de cola", 2 ) );
			lF.add( new Frase( "ChatNoGPT", "a", 1704111938383L, "¿Cómo afecta eso tu vida diaria?", 2 ) );
			lF.add( new Frase( "a", "ChatNoGPT", 1704111948984L, "Fatal, luego estoy hecho un trapo", 2 ) );
			lF.add( new Frase( "ChatNoGPT", "a", 1704111949382L, "Eso suena como un gran cambio ¿Cómo lo enfrentaste?", 2 ) );
			lF.add( new Frase( "a", "ChatNoGPT", 1704111997577L, "Pues mareado, desconectado, desanimado...", 2 ) );
			lF.add( new Frase( "ChatNoGPT", "a", 1704111997950L, "Eso parece importante para ti. ¿Por qué es así?", 2 ) );
			c = new Conversacion( "a", 1704111894312L );
			c.setId( 2 );
			c.getFrases().addAll( lF );
			ret.add( c );
			
			return ret;
		}
}
