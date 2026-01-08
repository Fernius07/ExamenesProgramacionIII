// IAG (ChatGPT 4o-mini)
// ADAPTADO: El código ha sido creado con con ChatGPT 4o-mini.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import es.deusto.prog.examen.Actividad;
import es.deusto.prog.examen.Actividad.TipoActividad;
import es.deusto.prog.examen.Persona;
import es.deusto.prog.examen.Sesion;

public class GestorBDInitializer {

	private static final String SQLITE_FILE = "resources/db/gym.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + SQLITE_FILE;
    
	public GestorBDInitializer() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.format("* Error al cargar el driver de la BBDD: %s\\n", e.getMessage());
		}
	}    
    
    public void crearTablas() {
    	// Borrar el fichero de la base de datos si existe
    	try {
    		File dbFile = new File(SQLITE_FILE);
    		
    		if (dbFile.exists()) {
    			dbFile.delete();
    		}
    	} catch (Exception e) {
    		System.err.format("* Error al borrar el fichero '%s' de la base de datos: %s\n", SQLITE_FILE, e.getMessage());
    		System.exit(1);
    	}    	
    	
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            String sqlActividad = "CREATE TABLE IF NOT EXISTS ACTIVIDAD (\n"
                    + " ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " TIPO TEXT NOT NULL,\n"
                    + " PLAZAS_MAXIMAS INTEGER NOT NULL\n"
                    + ");";

            String sqlSesion = "CREATE TABLE IF NOT EXISTS SESION (\n"                   
                    + " ID_ACTIVIDAD INTEGER NOT NULL,\n"
                    + " DIA TEXT NOT NULL,\n"
                    + " HORA_INICIO INTEGER NOT NULL,\n"
                    + " PRIMARY KEY (ID_ACTIVIDAD, DIA, HORA_INICIO),\n"
                    + " FOREIGN KEY(ID_ACTIVIDAD) REFERENCES ACTIVIDAD(ID)\n"
                    + ");";

            String sqlPersona = "CREATE TABLE IF NOT EXISTS PERSONA (\n"
                    + " ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " NOMBRE TEXT NOT NULL,\n"
                    + " EMAIL TEXT NOT NULL,\n"
                    + " TIPO_ABONO TEXT NOT NULL\n"
                    + ");";

            String sqlPersonaActividad = "CREATE TABLE IF NOT EXISTS PERSONA_ACTIVIDAD (\n"
                    + " ID_PERSONA INTEGER NOT NULL,\n"
                    + " ID_ACTIVIDAD INTEGER NOT NULL,\n"
                    + " PRIMARY KEY (ID_PERSONA, ID_ACTIVIDAD),\n"
                    + " FOREIGN KEY(ID_PERSONA) REFERENCES PERSONA(ID),\n"
                    + " FOREIGN KEY(ID_ACTIVIDAD) REFERENCES ACTIVIDAD(ID)\n"
                    + ");";

            String sqlAuditoria = "CREATE TABLE IF NOT EXISTS AUDITORIA (\n"
                    + " ID_PERSONA INTEGER NOT NULL,\n"
                    + " ID INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + " FECHA TEXT NOT NULL,\n"
                    + " ABONO_ANTERIOR TEXT NOT NULL,\n"
                    + " ABONO_NUEVO TEXT NOT NULL,\n"
                    + " FOREIGN KEY(ID_PERSONA) REFERENCES ACTIVIDAD(ID)\n"
                    + ");";

            try (Statement stmt = con.createStatement()) {
                stmt.execute(sqlActividad);
                stmt.execute(sqlSesion);
                stmt.execute(sqlPersona);
                stmt.execute(sqlPersonaActividad);
                stmt.execute(sqlAuditoria);
            }
            
            System.out.println("- Tablas creadas.");

        } catch (SQLException e) {
            System.err.format("* Error al crear las tablas: %s\n", e.getMessage());
        }
    }

    // Insertar una Actividad
    public void insertActividad(Actividad actividad) {
        String sql = "INSERT INTO ACTIVIDAD (TIPO, PLAZAS_MAXIMAS) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, actividad.getTipo().toString());
            pstmt.setInt(2, actividad.getPlazasMaximas());
            pstmt.executeUpdate();
            System.out.format("- Actividad '%s' insertada\n", actividad.getTipo());
            
            // Obtener el ID de la Actividad insertada
            Actividad actividadAux = getActividadByTipo(actividad.getTipo());
            actividad.setId(actividadAux.getId());
        } catch (SQLException e) {
            System.err.format("* Error al insertar Actividad '%s': %s\n", actividad.getTipo(), e.getMessage());
        }
    }

    // Insertar una Sesión
    public void insertSesion(Sesion sesion) {
        String sql = "INSERT INTO SESION (ID_ACTIVIDAD, DIA, HORA_INICIO) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {        	
            pstmt.setLong(1, sesion.getActividad().getId());
            pstmt.setString(2, sesion.getDia().toString());
            pstmt.setInt(3, sesion.getHoraInicio());
            pstmt.executeUpdate();
            System.out.format("- Sesión %s el %s a las %s insertada\n", sesion.getActividad().getTipo(), sesion.getDia(), sesion.getHoraInicio());
            
        } catch (SQLException e) {
            System.err.format("* Error al insertar Sesión %s el %s a las %s': %s\n", 
            		sesion.getActividad().getTipo(), sesion.getDia(), sesion.getHoraInicio(), e.getMessage());
        }
    }

    // Insertar una Persona
    public void insertPersona(Persona persona) {
        String sql = "INSERT INTO PERSONA (NOMBRE, EMAIL, TIPO_ABONO) VALUES (?, ?, ?)";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getEmail());
            pstmt.setString(3, persona.getTipoAbono().toString());
            pstmt.executeUpdate();
            System.out.format("- Persona '%s' insertada\n", persona.getNombre());
            
            // Obtener el ID de la Persona insertada
            Persona personaAux = getPersonaByEmail(persona.getEmail());
            persona.setId(personaAux.getId());            
        } catch (SQLException e) {
            System.err.format("* Error al insertar Persona '%s': %s\n", persona.getNombre(), e.getMessage());
        }
    }

    // Insertar relación entre Persona y Actividad
    public void insertPersonaActividad(Persona persona, Actividad actividad) {
        String sql = "INSERT INTO PERSONA_ACTIVIDAD (ID_PERSONA, ID_ACTIVIDAD) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, persona.getId());            
        	pstmt.setLong(2, actividad.getId());
            pstmt.executeUpdate();
            System.out.format("- Actividad '%s' asignada a '%s'\n", actividad.getTipo(), persona.getNombre());
        } catch (SQLException e) {
            System.err.format("* Error al asignar Actividad '%s' a la Persona '%s': %s\n", actividad.getTipo(), persona.getNombre(), e.getMessage());
        }
    }

    // Obtener una Actividad por TipoActividad
    public Actividad getActividadByTipo(TipoActividad tipo) {
        String sql = "SELECT * FROM ACTIVIDAD WHERE TIPO = ?";
        Actividad actividad = null;

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, tipo.toString());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                actividad = new Actividad(rs.getLong("ID"), tipo, rs.getInt("PLAZAS_MAXIMAS"));
            }
        } catch (SQLException e) {
            System.err.format("* Error al obtener Actividad '%s': %s\n", tipo, e.getMessage());
        }

        return actividad;
    }
    
    // Obtener una Actividad por ID
    public Actividad getActividadById(long id) {
        String sql = "SELECT * FROM ACTIVIDAD WHERE ID = ?";
        Actividad actividad = null;

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                actividad = new Actividad(rs.getLong("ID"), TipoActividad.valueOf(rs.getString("TIPO")), rs.getInt("PLAZAS_MAXIMAS"));
            }
        } catch (SQLException e) {
            System.err.format("* Error al obtener Actividad '%d': %s\n", id, e.getMessage());
        }

        return actividad;
    }
    
    // Obtener una Persona por email
    public Persona getPersonaByEmail(String email) {
        String sql = "SELECT * FROM PERSONA WHERE EMAIL = ?";
        Persona persona = null;

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                persona = new Persona(rs.getLong("ID"), rs.getString("NOMBRE"), rs.getString("EMAIL"), Persona.TipoAbono.valueOf(rs.getString("TIPO_ABONO")));
            }
        } catch (SQLException e) {
            System.err.format("* Error al obtener Persona '%s': %s\n", email, e.getMessage());
        }

        return persona;
    }
    
    // Obtener una Persona por ID
    public Persona getPersonaById(Long id) {
        String sql = "SELECT * FROM PERSONA WHERE ID = ?";
        Persona persona = null;

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                persona = new Persona(rs.getLong("ID"), rs.getString("NOMBRE"), rs.getString("EMAIL"), Persona.TipoAbono.valueOf(rs.getString("TIPO_ABONO")));
            }
        } catch (SQLException e) {
        	System.err.format("* Error al obtener Persona '%d': %s\n", id, e.getMessage());
        }

        return persona;
    }
}