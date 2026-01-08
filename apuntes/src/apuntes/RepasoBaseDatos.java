package apuntes;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// --- CLASES AUXILIARES ---
enum TipoAbono {
	MENSUAL, ANUAL, BAJA
}

enum NivelMonitor {
	PRINCIPIANTE, AVANZADO
}

class Persona {
	private String nombre;
	private String email;
	private TipoAbono tipoAbono;
	private int id;

	public Persona(String nombre, TipoAbono abono) {
		this.nombre = nombre;
		this.tipoAbono = abono;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEmail() {
		return email;
	}

	public TipoAbono getTipoAbono() {
		return tipoAbono;
	}

	public int getId() {
		return id;
	}
}

class Monitor {
	private int id;
	private NivelMonitor nivel;
	private List<String> horarios;

	public int getId() {
		return id;
	}

	public NivelMonitor getNivel() {
		return nivel;
	}

	public List<String> getHorarios() {
		return horarios;
	}
}

// ==========================================================
//   CLASE PRINCIPAL: REPASO JDBC
// ==========================================================
public class RepasoBaseDatos {

	private static final String URL = "jdbc:sqlite:gym.db";

	// -------------------------------------------------------------------------
	// ESCENARIO A: LECTURA BÁSICA (SELECT -> LISTA)
	// -------------------------------------------------------------------------
	public List<Persona> cargarPersonas() {
		List<Persona> lista = new ArrayList<>();
		String sql = "SELECT * FROM PERSONA";

		try (Connection con = DriverManager.getConnection(URL); PreparedStatement pst = con.prepareStatement(sql)) {

			ResultSet rs = pst.executeQuery(); // Botón azul: LEER

			while (rs.next()) {
				String nombre = rs.getString("NOMBRE");

				// TRUCO ENUM: De String BBDD a Java Enum
				String textoAbono = rs.getString("TIPO_ABONO");
				TipoAbono abono = TipoAbono.valueOf(textoAbono);

				lista.add(new Persona(nombre, abono));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	// -------------------------------------------------------------------------
	// ESCENARIO B: ESCRITURA SIMPLE (INSERT CON FECHAS)
	// -------------------------------------------------------------------------
	public void insertarPersona(Persona p) {
		String sql = "INSERT INTO PERSONA (NOMBRE, EMAIL, TIPO_ABONO, FECHA_ALTA) VALUES (?, ?, ?, ?)";

		try (Connection con = DriverManager.getConnection(URL); PreparedStatement pst = con.prepareStatement(sql)) {

			pst.setString(1, p.getNombre());
			pst.setString(2, p.getEmail());
			// TRUCO ENUM: Pasar a String
			pst.setString(3, p.getTipoAbono().toString());
			// TRUCO FECHA: Guardar como Texto
			pst.setString(4, LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

			pst.executeUpdate(); // Botón rojo: CAMBIAR
			System.out.println("Persona insertada correctamente.");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------
	// ESCENARIO C: AGRUPAR EN MAPA (SELECT -> MAP)
	// Patrón: "Si no existe la clave, crea la lista vacía".
	// -------------------------------------------------------------------------
	public Map<TipoAbono, List<Persona>> cargarPersonasPorAbono() {
		Map<TipoAbono, List<Persona>> mapa = new HashMap<>();
		String sql = "SELECT * FROM PERSONA";

		try (Connection con = DriverManager.getConnection(URL); PreparedStatement pst = con.prepareStatement(sql)) {

			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				String nombre = rs.getString("NOMBRE");
				TipoAbono abono = TipoAbono.valueOf(rs.getString("TIPO_ABONO"));
				Persona p = new Persona(nombre, abono);

				// LÓGICA DE MAPA
				if (!mapa.containsKey(abono)) {
					mapa.put(abono, new ArrayList<>());
				}
				mapa.get(abono).add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapa;
	}

	// -------------------------------------------------------------------------
	// EL "MEGAZORD": MAPAS + JDBC (CRUD COMPLETO)
	// Escenario: Recorrer un mapa de cambios y hacer Delete/Update/Insert según
	// lógica.
	// -------------------------------------------------------------------------
	public void procesarCambiosMasivos(Map<Integer, TipoAbono> mapaCambios) {
		String sqlInsert = "INSERT INTO AUDITORIA (ID_USUARIO, ACCION, FECHA) VALUES (?, ?, ?)";
		String sqlUpdate = "UPDATE PERSONA SET TIPO_ABONO = ? WHERE ID = ?";
		String sqlDelete = "DELETE FROM PERSONA WHERE ID = ?";

		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement pstLog = con.prepareStatement(sqlInsert);
				PreparedStatement pstUpdate = con.prepareStatement(sqlUpdate);
				PreparedStatement pstDelete = con.prepareStatement(sqlDelete)) {

			// RECORREMOS EL MAPA (EntrySet)
			for (Map.Entry<Integer, TipoAbono> entrada : mapaCambios.entrySet()) {
				Integer idUsuario = entrada.getKey();
				TipoAbono nuevoAbono = entrada.getValue();

				// LÓGICA DE NEGOCIO
				if (nuevoAbono == TipoAbono.BAJA) {
					// CASO A: SI ES 'BAJA', BORRAMOS
					pstDelete.setInt(1, idUsuario);
					int borrados = pstDelete.executeUpdate();

					if (borrados > 0) {
						registrarLog(pstLog, idUsuario, "USUARIO ELIMINADO");
					}
				} else {
					// CASO B: SI ES OTRO ABONO, ACTUALIZAMOS
					pstUpdate.setString(1, nuevoAbono.toString());
					pstUpdate.setInt(2, idUsuario);
					int actualizados = pstUpdate.executeUpdate();

					if (actualizados > 0) {
						registrarLog(pstLog, idUsuario, "CAMBIO A " + nuevoAbono.toString());
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Método auxiliar reutilizable
	private void registrarLog(PreparedStatement pst, int id, String accion) throws SQLException {
		pst.setInt(1, id);
		pst.setString(2, accion);
		pst.setString(3, LocalDate.now().toString());
		pst.executeUpdate();
	}

	// -------------------------------------------------------------------------
	// ACTUALIZACIÓN MASIVA (BORRÓN Y CUENTA NUEVA)
	// Escenario: Recibir lista nueva de actividades -> Borrar viejas -> Insertar
	// nuevas.
	// -------------------------------------------------------------------------
	public void actualizarActividadesMasivas(Map<Integer, List<Integer>> mapaAsignaciones) {
		String sqlDelete = "DELETE FROM PERSONA_ACTIVIDAD WHERE ID_PERSONA = ?";
		String sqlInsert = "INSERT INTO PERSONA_ACTIVIDAD (ID_PERSONA, ID_ACTIVIDAD) VALUES (?, ?)";

		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement pstDelete = con.prepareStatement(sqlDelete);
				PreparedStatement pstInsert = con.prepareStatement(sqlInsert)) {

			for (Map.Entry<Integer, List<Integer>> entrada : mapaAsignaciones.entrySet()) {
				Integer idUsuario = entrada.getKey();
				List<Integer> nuevasActividades = entrada.getValue();

				// A. LIMPIEZA (DELETE)
				pstDelete.setInt(1, idUsuario);
				pstDelete.executeUpdate();

				// B. ASIGNACIÓN (INSERT)
				for (Integer idActividad : nuevasActividades) {
					pstInsert.setInt(1, idUsuario);
					pstInsert.setInt(2, idActividad);
					pstInsert.executeUpdate();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------------------------------------
	// EL PATRÓN "ATÓMICO" (Update en Cascada)
	// Clave: Usar el valor de retorno de executeUpdate() en un IF.
	// -------------------------------------------------------------------------
	public boolean actualizarMonitor(Monitor monitor) {
		boolean exito = false;
		String sqlUpdate = "UPDATE MONITORES SET NIVEL = ? WHERE ID = ?";
		String sqlDelete = "DELETE FROM CLASES WHERE ID_MONITOR = ?";
		String sqlInsert = "INSERT INTO CLASES (ID_MONITOR, HORARIO) VALUES (?, ?)";

		try (Connection con = DriverManager.getConnection(URL);
				PreparedStatement pstUpdate = con.prepareStatement(sqlUpdate);
				PreparedStatement pstDelete = con.prepareStatement(sqlDelete);
				PreparedStatement pstInsert = con.prepareStatement(sqlInsert)) {

			// PASO 1: ACTUALIZAR MONITOR
			pstUpdate.setString(1, monitor.getNivel().toString());
			pstUpdate.setInt(2, monitor.getId());

			// ¡EL IF DE SEGURIDAD!
			if (pstUpdate.executeUpdate() == 1) {

				// PASO 2: BORRAR CLASES VIEJAS (>= 0 porque puede no tener clases)
				pstDelete.setInt(1, monitor.getId());
				if (pstDelete.executeUpdate() >= 0) {

					// PASO 3: INSERTAR CLASES NUEVAS
					for (String horario : monitor.getHorarios()) {
						pstInsert.setInt(1, monitor.getId());
						pstInsert.setString(2, horario);
						pstInsert.executeUpdate();
					}
					exito = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exito;
	}
}