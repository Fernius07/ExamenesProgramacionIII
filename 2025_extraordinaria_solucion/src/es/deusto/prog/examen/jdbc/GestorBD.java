// IAG (ChatGPT 4o-mini)
// ADAPTADO: El código ha sido creado con con ChatGPT 4o-mini.
// La versión final ha sido convenientemente revisada para estar libre de errores.

package es.deusto.prog.examen.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import es.deusto.prog.examen.Actividad;
import es.deusto.prog.examen.Persona;
import es.deusto.prog.examen.Sesion;
import es.deusto.prog.examen.Persona.TipoAbono;
import es.deusto.prog.examen.Registro;

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
            System.err.format("\n* Error recuperando personas: %s.", e.getMessage());
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
        	System.err.format("\n* Error recuperando sesiones de '%s': %s.", 
        			actividad.getTipo(), e.getMessage());
        }
        
        return sesiones;
    }
    
	public boolean updatePersona(Persona persona) {
		boolean updated = false;
		
		String sqlUpdate = "UPDATE PERSONA SET TIPO_ABONO = ? WHERE ID = ?";
		String sqlDelete = "DELETE FROM PERSONA_ACTIVIDAD WHERE ID_PERSONA = ?";
		String sqlInsert = "INSERT INTO PERSONA_ACTIVIDAD (ID_PERSONA, ID_ACTIVIDAD) VALUES (?, ?)";
		
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pstUpdate = con.prepareStatement(sqlUpdate);
        	 PreparedStatement pstDelete = con.prepareStatement(sqlDelete);
        	 PreparedStatement pstInsert = con.prepareStatement(sqlInsert)){
			//Se actualiza el primer PreparedStatement para actualizar la tabla PERSONA
        	pstUpdate.setString(1, persona.getTipoAbono().toString());
        	pstUpdate.setLong(2, persona.getId());        	
        	//Si se actualiza la tabla persona correctamente
        	if (pstUpdate.executeUpdate() == 1) {
        		//Se actualiza el segundo PreparedStatement para borrar las actividades de la persona
        		pstDelete.setLong(1, persona.getId());
        		//Si se han borrado las actividades de la persona correctamente
        		if (pstDelete.executeUpdate() >= 0) {
        			//Para cada actividad en la que está apuntada la persona
        			for (Actividad actividad : persona.getActividades()) {
        				//Se actualiza el tercer PreparedStatement para añadir las actividades a la persona
        				pstInsert.setLong(1, persona.getId());
        				pstInsert.setLong(2, actividad.getId());
        				//Si no se puede insertar la relación entre pesona y actividad
        				int result3 = pstInsert.executeUpdate();
        				
        				if (result3 != 1) {
        					//Se devuelve error en la actualización
        					return false;
        				}
        			}

        			updated = true;
        		}
        	}        	
		} catch (Exception e) {
			System.err.format("\n* Error actualizando la persona '%s': %s", persona.getNombre(), e.getMessage());
			e.printStackTrace();
		}
		
        return updated;
	}

	public List<Registro> loadRegistries(LocalDate fechaFin) {
		List<Registro> registros = new ArrayList<>();
		String sql = "SELECT * FROM AUDITORIA WHERE FECHA <= ?";

		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, fechaFin.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				long idPersona = rs.getLong("ID_PERSONA");
				LocalDate fecha = LocalDate.parse(rs.getString("FECHA"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
				TipoAbono abonoAnterior = TipoAbono.valueOf(rs.getString("ABONO_ANTERIOR"));
				TipoAbono abonoNuevo = TipoAbono.valueOf(rs.getString("ABONO_NUEVO"));
				registros.add(new Registro(idPersona, fecha, abonoAnterior, abonoNuevo));
			}
		} catch (Exception e) {
			System.err.format("* Error al obtener registros: %s\n", e.getMessage());
		}

		return registros;
	}

	public Persona getPersona(long id) {
		Persona persona = null;
		String sql = "SELECT * FROM PERSONA WHERE ID = ?";

		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				String nombre = rs.getString("NOMBRE");
				String email = rs.getString("EMAIL");
				TipoAbono tipoAbono = TipoAbono.valueOf(rs.getString("TIPO_ABONO"));
				persona = new Persona(id, nombre, email, tipoAbono);
			}
		} catch (Exception e) {
			System.err.format("* Error al obtener persona: %s\n", e.getMessage());
		}

		return persona;
	}

	//TODO Tarea 3 - JDBC: Implementa este método
	public boolean insertRecord(Persona persona) {
		boolean updated = false;

		return updated;
	}
}