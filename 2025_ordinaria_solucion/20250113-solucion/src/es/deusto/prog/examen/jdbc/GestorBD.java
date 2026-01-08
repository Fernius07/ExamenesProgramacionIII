// IAG (ChatGPT 4o-mini)
// ADAPTADO: El código ha sido creado con con ChatGPT 4o-mini.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import es.deusto.prog.examen.Actividad;
import es.deusto.prog.examen.Persona;
import es.deusto.prog.examen.Sesion;

public class GestorBD {
	
	private static final String SQLITE_FILE = "resources/db/gym.db";
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + SQLITE_FILE;
    
	public GestorBD() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.format("* Error al cargar el driver de la BBDD: %s\n", e.getMessage());
		}
	}

	public List<Actividad> loadActividades() {
		List<Actividad> actividades = new ArrayList<>();
		List<Persona> personas = loadPersonas();
		
		String sqlActividad = "SELECT * FROM ACTIVIDAD";
		String sqlPersonaActividad = "SELECT * FROM PERSONA_ACTIVIDAD WHERE ID_ACTIVIDAD = ?";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstActividad = con.prepareStatement(sqlActividad);
             PreparedStatement pstPersonaActividad = con.prepareStatement(sqlPersonaActividad)) {
            ResultSet rsActividad = pstActividad.executeQuery();
            
            while (rsActividad.next()) {
                long id = rsActividad.getLong("ID");
                Actividad.TipoActividad tipo = Actividad.TipoActividad.valueOf(rsActividad.getString("TIPO"));
                int plazasMaximas = rsActividad.getInt("PLAZAS_MAXIMAS");
                Actividad actividad = new Actividad(id, tipo, plazasMaximas);

                List<Sesion> sesiones = loadSesiones(actividad);
                sesiones.forEach(a -> actividad.addSesion(a));
                
                pstPersonaActividad.setLong(1, id);
                ResultSet rs2 = pstPersonaActividad.executeQuery();
                
                while (rs2.next()) {
                	int idPersona = rs2.getInt("ID_PERSONA");
                	personas.forEach(p -> {
                		if (p.getId() == idPersona) {
                			actividad.addPersona(p);
                			p.getActividades().add(actividad);
                		}
                	});
                }

                rs2.close();
                actividades.add(actividad);                
            }
            
            rsActividad.close();
        } catch (Exception e) {
        	System.err.format("\n* Error recuperando actividades: %s.", e.getMessage());
        }
				
		return actividades;
	}
			
	protected List<Persona> loadPersonas() {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT * FROM PERSONA";
        
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement pst = con.createStatement()) {
            ResultSet rs = pst.executeQuery(sql);
            
            while (rs.next()) {
                long id = rs.getLong("ID");
                String nombre = rs.getString("NOMBRE");
                String email = rs.getString("EMAIL");
                Persona.TipoAbono tipoAbono = Persona.TipoAbono.valueOf(rs.getString("TIPO_ABONO"));
                personas.add(new Persona(id, nombre, email, tipoAbono));
            }            
        } catch (Exception e) {
            System.err.format("* Error recuperando personas: %s.\n", e.getMessage());
        }
        
        return personas;
    }
	
    protected List<Sesion> loadSesiones(Actividad actividad) {
        List<Sesion> sesiones = new ArrayList<>();
        
        String sql = "SELECT * FROM SESION WHERE ID_ACTIVIDAD = ?";
        
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setLong(1, actividad.getId());
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Sesion.DiaSemana dia = Sesion.DiaSemana.valueOf(rs.getString("DIA"));
                int horaInicio = rs.getInt("HORA_INICIO");
                sesiones.add(new Sesion(actividad, dia, horaInicio));
            }
        } catch (Exception e) {
        	System.err.format("* Error recuperando sesiones de '%s': %s.\n", 
        			actividad.getTipo(), e.getMessage());
        }
        
        return sesiones;
    }
    
	// TODO: Tarea 1: Actualizar datos de una BBDD con JDBC. Este método tiene 
	// que actualizar el tipo de abono en la tabla PERSONA y además, hacer cambios
	// en la tabla PERSONA_ACTIVIDAD. En este último caso, debes tener en cuenta 
	// que la información de la tabla PERSONA_ACTIVIDAD es previa a la modificación 
	// del abono. Por lo tanto, no es correcta. La lista correcta de actividades en 
	// las que está apuntada una persona, está en el objeto Persona que recibe el 
	// método updatePersona(). Esto implica que debes realizar dos tipos de cambios
	// sobre la tabla PERSONA_ACTIVIDAD.
	public boolean updatePersona(Persona persona) {
		boolean updated = false;
				        
        return updated;
	}
}