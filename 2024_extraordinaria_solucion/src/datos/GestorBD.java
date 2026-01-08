package datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dominio.Asignatura;
import dominio.Competencia;
import dominio.Estudiante;
import dominio.Examen;

public class GestorBD {

	private Connection conn = null; 
	
	/**
	 * Crea una conexión con la base de datos.
	 * @throws BDException Se produce cuando existe un problema con la creación de la conexión a la BD.
	 */
	public void conectar(String dbPath) throws BDException {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
		} catch (ClassNotFoundException e) {
			throw new BDException("Error cargando el driver de la BD", e);
		} catch (SQLException e) {
			throw new BDException("Error conectando a la BD", e);
		}
	}
	
	/**
	 * Cierra una conexión con la BD.
	 * @throws BDException Se produce cuando existe un error a la hora de conectar con la BD.
	 */
	public void desconectar() throws BDException {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new BDException("Error cerrando la conexión con la BD", e);
		}
	}
	
	/**
	 * Borrar y crea de nuevo las tablas utilizadas en la aplicación. 
	 * Si la tablas existen son eliminas y creadas de nuevo.
	 */
	public void crearTablas() throws BDException {
		try (Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("DROP TABLE IF EXISTS Asignatura");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Asignatura (codigo INTEGER PRIMARY KEY, nombre TEXT)");
			stmt.executeUpdate("DROP TABLE IF EXISTS Estudiante");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Estudiante (nia INTEGER PRIMARY KEY, nombre TEXT, apellido TEXT)");
			stmt.executeUpdate("DROP TABLE IF EXISTS Matricula");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Matricula (nia INTEGER, codAsignatura INTEGER)");
			stmt.executeUpdate("DROP TABLE IF EXISTS Competencia");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Competencia (id INTEGER PRIMARY KEY AUTOINCREMENT, codAsignatura INTEGER, codigo TEXT, desc TEXT)");
			
			stmt.executeUpdate("DROP TABLE IF EXISTS Examen");
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Examen (fecha TEXT, nia INTEGER, codAsignatura INTEGER, nota FLOAT, resumen TEXT)");
		} catch (SQLException e) {
			throw new BDException("Error creando las tablas de la BD", e);
		}
	}
	
	/**
	 * Inserta estudiantes en la base de datos
	 * @throws BDException Se produce cuando existe un error al insertar datos
	 */
	public void insertarEstudiantes(List<Estudiante> estudiantes) throws BDException {
		String insertSQL = "INSERT INTO Estudiante (nia, nombre, apellido) VALUES (?, ?, ?)";
		try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
			for (Estudiante e : estudiantes) {
				preparedStatement.setInt(1, e.getNIA());
				preparedStatement.setString(2, e.getNombre());
				preparedStatement.setString(3, e.getApellido());
				preparedStatement.executeUpdate();
				
				for (Asignatura a : e.getAsignaturas()) {
					insertartMatriculación(e, a);
				}
			}
		} catch (SQLException e) {
			throw new BDException("Error insertando datos de estudiantes de prueba", e);
		}
	}
	
	/**
	 * Inserta en la base de datosuna matriculación de un estudiante en una asignatura
	 */
	public void insertartMatriculación(Estudiante estudiante, Asignatura asignatura) {
		String insertSQL = "INSERT INTO Matricula (nia, codAsignatura) VALUES (?, ?)";
		try (PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
			preparedStatement.setInt(1, estudiante.getNIA());
			preparedStatement.setInt(2, asignatura.getCodigo());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error insertando datos de estudiantes de prueba. " + e.getCause());
		}
	}
	
	/**
	 * Inserta asignaturas en la base de datos
	 * @throws BDException Se produce cuando existe un error al insertar datos
	 */
	public void insertarAsignaturas(List<Asignatura> asignaturas) throws BDException {
		String insertAsig = "INSERT INTO Asignatura (codigo, nombre) VALUES (?, ?)";
		String insertComp = "INSERT INTO Competencia (codAsignatura, codigo, desc) VALUES (?, ?, ?)";
		try (PreparedStatement preparedStmtAsig = conn.prepareStatement(insertAsig)) {
			for (Asignatura asignatura : asignaturas) {
				preparedStmtAsig.setInt(1, asignatura.getCodigo());
				preparedStmtAsig.setString(2, asignatura.getNombre());
				preparedStmtAsig.executeUpdate();
				
				try (PreparedStatement preparedStmtComp = conn.prepareStatement(insertComp)) { 
					for (Competencia competencia : asignatura.getCompetencias()) {
						preparedStmtComp.setInt(1, asignatura.getCodigo());
						preparedStmtComp.setString(2, competencia.getCodigo());
						preparedStmtComp.setString(3, competencia.getDesc());
						preparedStmtComp.executeUpdate();
					}
				}
			}
		} catch (SQLException e) {
			throw new BDException("Error insertando datos de estudiantes de prueba", e);
		}
	}
	
	/**
	 * Obtiene la lista de asignaturas desde la base de datos
	 * @throws BDException 
	 */
	public List<Asignatura> listadoAsignaturas() throws BDException {
		List<Asignatura> asignaturas = new ArrayList<>();
		String selectSQL = "SELECT * FROM Asignatura";
		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(selectSQL);
			while (rs.next()) {
				int codigo = rs.getInt("codigo");
				String nombre = rs.getString("nombre");
				asignaturas.add(new Asignatura(codigo, nombre));
			}
			
		return asignaturas;
		} catch (SQLException e) {
			throw new BDException("Error obteniendo datos de asignaturas", e);
		}
	}
	
	/**
	 * Obtiene los estudiantes que están matriculados en la asignatura indicada
	 * @throws BDException 
	 */
	public List<Estudiante> obtenerEstudiantesMatriculados(Asignatura asignatura) throws BDException {
		List<Estudiante> estudiantes = new ArrayList<>();
		String selectSQL = "SELECT * FROM Estudiante, Matricula WHERE Matricula.codAsignatura = ? AND Estudiante.nia = Matricula.nia";
		try (PreparedStatement preparedStatement = conn.prepareStatement(selectSQL)) {
			preparedStatement.setInt(1, asignatura.getCodigo());
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int nia = rs.getInt("nia");
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				estudiantes.add(new Estudiante(nia, nombre, apellido));
			}

			return estudiantes;
		} catch (SQLException e) {
			throw new BDException("Error obteniendo datos de estudiantes matriculados", e);
		}
	}
	
	/**
	 * Obtiene todos los estudiantes de la base de datos que cursan
	 * una determinada asignatura
	 * @throws BDException 
	 */
	public List<Estudiante> obtenerEstudiantes(int codigoAsignatura) throws BDException {
		List<Estudiante> estudiantes = new ArrayList<>();
		String selectSQL = "SELECT Estudiante.nia, nombre, apellido FROM Estudiante, Matricula WHERE Estudiante.nia = Matricula.nia AND codAsignatura = ? ORDER BY Estudiante.nia";
		try (PreparedStatement stmt = conn.prepareStatement(selectSQL)) {
			stmt.setInt(1, codigoAsignatura);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int nia = rs.getInt("nia");
				String nombre = rs.getString("nombre");
				String apellido = rs.getString("apellido");
				estudiantes.add(new Estudiante(nia, nombre, apellido));
			}
			return estudiantes;
		} catch (SQLException e) {
			throw new BDException("Error obteniendo datos de estudiantes", e);
		}
	}
	
	/**
	 * Inserta datos de prueba en la base de datos.
	 * @throws BDException
	 */
	public void insertarDatosPrueba() throws BDException {
		List<Asignatura> asignaturas = Arrays.asList(
			new Asignatura(145315, "Programación III"),
			new Asignatura(145374, "Geometría y Física para Entornos Interactivos"),
			new Asignatura(145325, "Proceso de Software y Calidad")
		);
		
		asignaturas.get(0).addCompetencia(new Competencia("CE-01", "Swing"));
		asignaturas.get(0).addCompetencia(new Competencia("CE-02", "Colecciones"));
		asignaturas.get(0).addCompetencia(new Competencia("CE-03", "Persistencia"));
		asignaturas.get(0).addCompetencia(new Competencia("CE-04", "JUnit"));
		asignaturas.get(0).addCompetencia(new Competencia("CE-05", "Recursividad"));
		
		asignaturas.get(0).addCompetencia(new Competencia("CE-01", "Motores de videojuegos"));
		asignaturas.get(0).addCompetencia(new Competencia("CE-02", "Fundamentos matemáticos"));
		asignaturas.get(0).addCompetencia(new Competencia("CE-03", "Fundamentos físicos"));
		asignaturas.get(0).addCompetencia(new Competencia("CE-04", "Motores de físicas"));
		
		asignaturas.get(0).addCompetencia(new Competencia("CE-01", "Desarrollo ágil"));
		asignaturas.get(0).addCompetencia(new Competencia("CE-02", "Herramientas SCM"));
		asignaturas.get(0).addCompetencia(new Competencia("CE-03", "Herramientas de gestión del proceso"));
		asignaturas.get(0).addCompetencia(new Competencia("CE-04", "Calidad del software"));
		
		insertarAsignaturas(asignaturas);
		
		List<Estudiante> estudiantes = Arrays.asList(
			new Estudiante(12001, "Theodore", "Jefferson", asignaturas.subList(0, 1)),
			new Estudiante(11902, "Kiera ", "Gomez", asignaturas.subList(0, 1)),
			new Estudiante(11803, "Tyrese", "Cervantes", asignaturas.subList(0, 1)),
			new Estudiante(11704, "Frida", "Hodge", asignaturas.subList(0, 1)),
			new Estudiante(11605, "Avery", "Brooks", asignaturas.subList(1, 3)),
			new Estudiante(11506, "Kendall", "Wilson", asignaturas.subList(1, 3)),
			new Estudiante(11407, "Frida", "Avila", asignaturas.subList(1, 2)),
			new Estudiante(11308, "Karsyn", "Gould", asignaturas.subList(0, 1)),
			new Estudiante(11209, "Chaim", "Leach", asignaturas.subList(0, 1)),
			new Estudiante(11110, "Kaydence", "Summers", asignaturas.subList(0, 1)),
			new Estudiante(11011, "Saige", "Holden", asignaturas.subList(0, 1)),
			new Estudiante(10912, "Tanner", "Nguyen", asignaturas.subList(1, 3)),
			new Estudiante(10813, "Raiden", "Higgins", asignaturas.subList(0, 1)),
			new Estudiante(10714, "Victoria", "Cruz", asignaturas.subList(1, 2)),
			new Estudiante(10615, "Mylie", "Dunn", asignaturas.subList(0, 1)),
			new Estudiante(10516, "Liam", "Osborne", asignaturas.subList(2, 3)),
			new Estudiante(10417, "India", "Mays", asignaturas.subList(0, 1)),
			new Estudiante(10318, "Trinity", "Gamble", asignaturas.subList(2, 3)),
			new Estudiante(10219, "Marely", "Newman", asignaturas.subList(0, 1)),
			new Estudiante(10120, "Linda", "Werner", asignaturas.subList(0, 1))
		);
		
		insertarEstudiantes(estudiantes);
	}
	
	// T1. Implementa el siguiente método de base de datos de acuerdo a la especificación del enunciado
	public void insertarExamenes(List<Examen> examenes) throws BDException {
		String insertSQL = "INSERT INTO Examen (fecha, nia, codAsignatura, nota, resumen) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmtInsert = conn.prepareStatement(insertSQL)) {
            for (Examen e : examenes) {
                stmtInsert.setString(1, DateTimeFormatter.ISO_DATE.format(e.getFecha()));
                stmtInsert.setInt(2, e.getNia());
                stmtInsert.setInt(3, e.getAsignatura());
                
                List<String> competencias = new ArrayList<String>();
                String selectSQL = "SELECT * FROM Competencia WHERE codAsignatura = ?";
                try (PreparedStatement stmtSelect = conn.prepareStatement(selectSQL)) {
					stmtSelect.setInt(1, e.getAsignatura());
					ResultSet rs = stmtSelect.executeQuery();
					while (rs.next()) {
						competencias.add(rs.getString("desc"));
					}
                }
                
                float nota = 0;
                StringBuilder sb = new StringBuilder(); 
                for (int i = 0; i < e.getResultados().size(); i++) {
                	nota += e.getResultados().get(i);
                	sb.append(String.format("%s: %.1f%n", competencias.get(i), e.getResultados().get(i)));
                }
                
                nota /= e.getResultados().size();
                stmtInsert.setDouble(4, nota);
                stmtInsert.setString(5, sb.toString());
                
                stmtInsert.executeUpdate();
            }
        } catch (SQLException e) {
            throw new BDException("Error insertando datos de examenes. ", e);
        }
	}
}
