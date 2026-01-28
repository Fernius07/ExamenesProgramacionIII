/**
 * Este código ha sido elaborado a partir de una versión generada con Gemini 3.
 * La versión final ha sido revisada y adaptada para garantizar su corrección
 * y la ausencia de errores.
 */

package es.deusto.ingenieria.esqui.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.deusto.ingenieria.esqui.domino.Ciudad;
import es.deusto.ingenieria.esqui.domino.Estacion;
import es.deusto.ingenieria.esqui.domino.EstadoEstacion;
import es.deusto.ingenieria.esqui.domino.Itinerario;
import es.deusto.ingenieria.esqui.domino.Ciudad.Pais;
import es.deusto.ingenieria.esqui.domino.EstadoEstacion.Apertura;
import es.deusto.ingenieria.esqui.domino.EstadoEstacion.Clima;

public class GestorBD {

	private static final String SQLITE_FILE = "resources/db/esqui.db";
    protected static final String CONNECTION_STRING = "jdbc:sqlite:" + SQLITE_FILE;
          
    public void crearTablas() {
    	try {
    		File dbFile = new File(SQLITE_FILE);
    		
    		File dir = dbFile.getParentFile();
    		
    		if (dir != null && !dir.exists()) {
    			dir.mkdirs();
    		}
    		
    		if (dbFile.exists()) {
    			dbFile.delete();    			
    		}
    	} catch (Exception e) {
    		System.err.format("[DB] Error al preparar el archivo de BBDD: %s\n", e.getMessage());
    	}
    	
    	String sqlCiudad = "CREATE TABLE CIUDAD ("
                + "ID INTEGER PRIMARY KEY, "
                + "NOMBRE TEXT NOT NULL UNIQUE, "
                + "PAIS TEXT NOT NULL)";

    	String sqlItinerario = "CREATE TABLE ITINERARIO ("
                + "ID INTEGER PRIMARY KEY, "
                + "ORIGEN_ID INTEGER NOT NULL, "
                + "DESTINO_ID INTEGER NOT NULL, "
                + "DURACION INTEGER NOT NULL, "
                + "FOREIGN KEY(ORIGEN_ID) REFERENCES CIUDAD(ID), "
                + "FOREIGN KEY(DESTINO_ID) REFERENCES CIUDAD(ID))";
    	
    	String sqlEstacion = "CREATE TABLE ESTACION ("
                + "ID INTEGER PRIMARY KEY, "
                + "NOMBRE TEXT NOT NULL UNIQUE, "
                + "CIUDAD_ID INTEGER NOT NULL, "
                + "KM_ESQUIABLES_TOTAL REAL NOT NULL, "
                + "FOREIGN KEY(CIUDAD_ID) REFERENCES CIUDAD(ID))";
    	
    	String sqlEstadoEstacion = "CREATE TABLE ESTADO_ESTACION ("
                + "ESTACION_ID INTEGER PRIMARY KEY, "
                + "CLIMA TEXT NOT NULL, "
                + "APERTURA TEXT NOT NULL, "
                + "TEMPERATURA INTEGER NOT NULL, "
                + "KM_ESQUIABLES_ACTUALES REAL NOT NULL, "
                + "ACTUALIZACION INTEGER NOT NULL, "
                + "FOREIGN KEY(ESTACION_ID) REFERENCES ESTACION(ID))";    	

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement()) {
            
            stmt.execute(sqlCiudad);
            stmt.execute(sqlItinerario);
            stmt.execute(sqlEstacion);
            stmt.execute(sqlEstadoEstacion);            
        } catch (SQLException e) {
            System.err.format("[DB] Error al crear tablas: %s\n", e.getMessage());
        }
    }
    
    public void inicializarBBDD(List<Ciudad> ciudades,
    						  	List<Itinerario> itinerarios,
    						  	List<Estacion> estaciones) {
    	try (Connection con = DriverManager.getConnection(CONNECTION_STRING);) {    		
    		// Insertar Ciudades
    		String sqlCiudad = "INSERT INTO CIUDAD (NOMBRE, PAIS) VALUES (?, ?)";
    		
    		try (PreparedStatement pstmt = con.prepareStatement(sqlCiudad, Statement.RETURN_GENERATED_KEYS)) {
    			for (Ciudad c : ciudades) {
    				pstmt.setString(1, c.getNombre());
    				pstmt.setString(2, c.getPais().toString());
    				pstmt.executeUpdate();
    				
    				// Obtener ID generado
    				ResultSet rs = pstmt.getGeneratedKeys();
    				// Asignar ID al objeto Ciudad
    				if (rs.next()) {
    					c.setId(rs.getLong(1));
    				}
    			}
    		}
    		
    		// Insertar Itinerarios
    		String sqlItinerario = "INSERT INTO ITINERARIO (ORIGEN_ID, DESTINO_ID, DURACION) VALUES (?, ?, ?)";
    		
    		try (PreparedStatement pstmt = con.prepareStatement(sqlItinerario, Statement.RETURN_GENERATED_KEYS)) {
    			for (Itinerario i : itinerarios) {
    				pstmt.setLong(1, i.getOrigen().getId());
    				pstmt.setLong(2, i.getDestino().getId());
    				pstmt.setInt(3, i.getDuracion());
    				pstmt.executeUpdate();
    				
    				// Obtener ID generado
    				ResultSet rs = pstmt.getGeneratedKeys();    				
    				// Asignar ID al objeto Itinerario
    				if (rs.next()) {
    					i.setId(rs.getLong(1));
    				}
    			}
    		}
    		
    		// Insertar Estaciones
    		String sqlEstacion = "INSERT INTO ESTACION (NOMBRE, CIUDAD_ID, KM_ESQUIABLES_TOTAL) VALUES (?, ?, ?)";
    		String sqlEstadoEstacion = "INSERT INTO ESTADO_ESTACION (ESTACION_ID, CLIMA, APERTURA, TEMPERATURA, KM_ESQUIABLES_ACTUALES, ACTUALIZACION) VALUES (?, ?, ?, ?, ?, ?)";
    		
    		try (PreparedStatement pstmtEstacion = con.prepareStatement(sqlEstacion, Statement.RETURN_GENERATED_KEYS);
    			 PreparedStatement pstmtEstado = con.prepareStatement(sqlEstadoEstacion)) {
    			for (Estacion e : estaciones) {
    				pstmtEstacion.setString(1, e.getNombre());
    				pstmtEstacion.setLong(2, e.getCiudad().getId());
    				pstmtEstacion.setFloat(3, e.getKmEsquiablesTotal());
    				pstmtEstacion.executeUpdate();
    				
    				// Obtener ID generado
    				ResultSet rs = pstmtEstacion.getGeneratedKeys();
    				long estacionId = -1;
    				// Asignar ID al objeto Estacion
    				if (rs.next()) {
    					estacionId = rs.getLong(1);
    					e.setId(estacionId);
    				}
    				
    				// Insertar EstadoEstacion si existe
    				if (estacionId != -1 && e.getEstado() != null) {
    					EstadoEstacion ee = e.getEstado();
    					pstmtEstado.setLong(1, estacionId);
    					pstmtEstado.setString(2, ee.getClima().toString());
    					pstmtEstado.setString(3, ee.getApertura().toString());
    					pstmtEstado.setInt(4, ee.getTemperatura());
    					pstmtEstado.setFloat(5, ee.getKmEsquiables());
    					pstmtEstado.setLong(6, ee.getActualizacion().getTime());
    					pstmtEstado.executeUpdate();
    				}
    			}
    		}

    	} catch (SQLException e) {
    		System.err.format("[DB] Error al insertar datos: %s\n", e.getMessage());
    	}
    }
    
    public List<Ciudad> getCiudades() {
        List<Ciudad> ciudades = new ArrayList<>();
        String sql = "SELECT * FROM CIUDAD";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ciudades.add(new Ciudad(rs.getLong("ID"), rs.getString("NOMBRE"), Pais.valueOf(rs.getString("PAIS"))));
            }
        } catch (SQLException e) {
            System.err.format("[DB] Error al recuperar ciudades: %s\n", e.getMessage());
        }
        
        return ciudades;
    }
    
    public List<Itinerario> getItinerarios(List<Ciudad> ciudades) {
        List<Itinerario> itinerarios = new ArrayList<>();
        // Crear un mapa de ciudades por ID para acceso rápido al buscar origen/destino
        Map<Long, Ciudad> ciudadMap = new HashMap<>();
        ciudades.forEach(c -> ciudadMap.put(c.getId(), c));
        
        String sql = "SELECT * FROM ITINERARIO";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                long id = rs.getLong("ID");
                long origenId = rs.getLong("ORIGEN_ID");
                long destinoId = rs.getLong("DESTINO_ID");
                int duracion = rs.getInt("DURACION");
                
                // Obtener objetos Ciudad desde el mapa
                Ciudad origen = ciudadMap.get(origenId);
                Ciudad destino = ciudadMap.get(destinoId);
                
                if (origen != null && destino != null) {
                    itinerarios.add(new Itinerario(id, origen, destino, duracion));
                }
            }
        } catch (SQLException e) {
            System.err.format("[DB] Error al cargar itinerarios: %s\n", e.getMessage());
        }
        
        return itinerarios;
    }
    
    public List<Estacion> getEstaciones(List<Ciudad> ciudades) {
        List<Estacion> estaciones = new ArrayList<>();
        Map<Long, Ciudad> ciudadMap = new HashMap<>();
        // Crear un mapa de ciudades por ID para acceso rápido al buscar ciudad
        ciudades.forEach(c -> ciudadMap.put(c.getId(), c));
        
        String sql = "SELECT * FROM ESTACION";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                long id = rs.getLong("ID");
                String nombre = rs.getString("NOMBRE");
                long ciudadId = rs.getLong("CIUDAD_ID");
                float kmTotal = rs.getFloat("KM_ESQUIABLES_TOTAL");
                
                // Obtener EstadoEstacion desde la base de datos
                EstadoEstacion estado = getEstadoEstacion(con, id);
                // Obtener objeto Ciudad desde el mapa
                Ciudad ciudad = ciudadMap.get(ciudadId);
                
                if (ciudad != null) {
                    estaciones.add(new Estacion(id, nombre, ciudad, kmTotal, estado));
                }
            }
        } catch (SQLException e) {
            System.err.format("[DB] Error al cargar estaciones: %s\n", e.getMessage());
        }
        
        return estaciones;
    }
    
    public EstadoEstacion getEstadoEstacion(Connection con, long estacionId) {
        String sql = "SELECT * FROM ESTADO_ESTACION WHERE ESTACION_ID = ?";
        
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setLong(1, estacionId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Clima clima = Clima.valueOf(rs.getString("CLIMA"));
                    Apertura apertura = Apertura.valueOf(rs.getString("APERTURA"));
                    int temp = rs.getInt("TEMPERATURA");
                    float kmAct = rs.getFloat("KM_ESQUIABLES_ACTUALES");
                    Date act = new Date(rs.getLong("ACTUALIZACION"));
                    
                    return new EstadoEstacion(clima, apertura, temp, kmAct, act);
                }
            }
        } catch (SQLException e) {
            System.err.format("[DB] Error al cargar estado de estación %d: %s\n", estacionId, e.getMessage());
        }
        
        return null;
    }
    
    // TODO TAREA 3: BASE DE DATOS
	// Implementa el método 'actualizarEstadoEstacion'. El método recibe como parámetros el nombre 
    // de una estación y un objeto EstadoEstacion. Con esos datos, debes actualizar la tabla 
    // ESTADO_ESTACION. Podría ocurrir que para una estación no existan datos en la tabla 
    // ESTADO_ESTACION; en ese caso, en vez de actualizar debes insertar los datos. Tinenes 
    // que implementar toda la lógica necesaria dentro del método propuesto y para resolver 
    // la tarea no puedes invocar ningún otro método de la clase GestorBD.
	public boolean actualizarEstadoEstacion(String nombreEstacion, EstadoEstacion nuevoEstado) {
		
		// INCPORPORA AQUÍ TU CÓDIGO PARA ACTUALIZAR EL ESTADO DE LA ESTACIÓN EN LA BASE DE DATOS
		
		return false;
	}
}