/**
 * Este código ha sido elaborado a partir de una versión generada con Gemini 3.
 * La versión final ha sido revisada y adaptada para garantizar su corrección
 * y la ausencia de errores.
 */

package es.deusto.ingenieria.esqui.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import es.deusto.ingenieria.esqui.domino.EstadoEstacion;

public class GestorBDSolucion extends GestorBD {	
	
	public GestorBDSolucion() {
		super();
	}

    // TODO TAREA 3: BASE DE DATOS
	// Implementa el método 'actualizarEstadoEstacion'. El método recibe como parámetros el nombre 
    // de una estación y un objeto EstadoEstacion. Con esos datos, debes actualizar la tabla 
    // ESTADO_ESTACION. Podría ocurrir que para una estación no existan datos en la tabla 
    // ESTADO_ESTACION; en ese caso, en vez de actualizar debes insertar los datos. Tinenes 
    // que implementar toda la lógica necesaria dentro del método propuesto y para resolver 
    // la tarea no puedes invocar ningún otro método de la clase GestorBD.
	public boolean actualizarEstadoEstacion(String nombreEstacion, EstadoEstacion nuevoEstado) {
		// Sentencia SQL para obtener el ID de la estación a partir de su nombre
		String sqlSelectId = "SELECT ID FROM ESTACION WHERE NOMBRE = ?";
		// Sentencia SQL para actualizar el estado de la estación
		String sqlUpdate = "UPDATE ESTADO_ESTACION SET CLIMA=?, APERTURA=?, TEMPERATURA=?, KM_ESQUIABLES_ACTUALES=?, ACTUALIZACION=? WHERE ESTACION_ID=?";
		// Sentencia SQL para insertar un nuevo estado de la estación, si no existía previamente
		String sqlInsert = "INSERT INTO ESTADO_ESTACION (ESTACION_ID, CLIMA, APERTURA, TEMPERATURA, KM_ESQUIABLES_ACTUALES, ACTUALIZACION) VALUES (?, ?, ?, ?, ?, ?)";

		boolean updated = false;
		long estacionId = -1;
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
			 PreparedStatement pstmtSelectId = con.prepareStatement(sqlSelectId);
			 PreparedStatement pstmtUpdate = con.prepareStatement(sqlUpdate);
			 PreparedStatement pstmtInsert = con.prepareStatement(sqlInsert)) {
			
			// 1. Obtener el ID de la estación
			pstmtSelectId.setString(1, nombreEstacion);
			
			try (ResultSet rs = pstmtSelectId.executeQuery()) {
				if (rs.next()) {
					estacionId = rs.getLong("ID");
				} else {
					System.err.printf("[DB ] No existe la astación '%s'.%n", nombreEstacion);
					
					return false;
				}
			}

			// 2. Se intenta actualizar el estado existente
			pstmtUpdate.setString(1, nuevoEstado.getClima().toString());
			pstmtUpdate.setString(2, nuevoEstado.getApertura().toString());
			pstmtUpdate.setInt(3, nuevoEstado.getTemperatura());
			pstmtUpdate.setFloat(4, nuevoEstado.getKmEsquiables());
			pstmtUpdate.setLong(5, nuevoEstado.getActualizacion().getTime());
			pstmtUpdate.setLong(6, estacionId);
			
			int affectedRows = pstmtUpdate.executeUpdate();
			
			// Si se actualizó una fila, la estación ya tenía un estado registrado
			if (affectedRows == 1) {
				// 3. Éxito en la actualización
				System.out.printf("[DB] El estado de la estación '%s' se ha actualizado correctamente.%n", nombreEstacion);
				
				updated = true;
			// Si no se actualizó ninguna fila, significa que no existía un estado previo y hay que insertarlo
			} else {
				// 4. Si no se actualizó, es porque no existía un registro de estado; intentamos insertar.
				System.out.printf("[DB] La estacion '%s' no tenía estado, se va a insertar...%n", nombreEstacion);
				
				pstmtInsert.setLong(1, estacionId);
				pstmtInsert.setString(2, nuevoEstado.getClima().toString());
				pstmtInsert.setString(3, nuevoEstado.getApertura().toString());
				pstmtInsert.setInt(4, nuevoEstado.getTemperatura());
				pstmtInsert.setFloat(5, nuevoEstado.getKmEsquiables());
				pstmtInsert.setLong(6, nuevoEstado.getActualizacion().getTime());
				
				if (pstmtInsert.executeUpdate() == 1) {
					System.out.printf("[DB] El estado de la estación '%s' se ha insertado correctamente.%n", nombreEstacion);
					updated = true;
				} else {
					System.err.printf("[DB] Error al insertar el estado para '%s'.%n", nombreEstacion);
				}
			}
			
		} catch (SQLException e) {
			System.err.printf("[DB] Error al actualizar el estado de '%s'.%n", e.getMessage());
			e.printStackTrace();
		}
		
		return updated;
	}
}