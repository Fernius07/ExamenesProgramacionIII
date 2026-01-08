package db;

import domain.Dificultad;
import domain.Pista;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class GestorBD {
    private static final String CONNECTION_STRING = "jdbc:sqlite:resort.db";

    public GestorBD() {
        try {
            // Cargar el driver explícitamente (RECOMENDADO para evitar errores)
            Class.forName("org.sqlite.JDBC");
            
            // Cargar datos de prueba si no existen (para que te funcione el ejemplo)
            crearDatosDePrueba();
        } catch (ClassNotFoundException e) {
            System.err.println("ERROR: No se encuentra el driver de SQLite. Añade el .jar al Build Path.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Método utilitario para limpiar la BD antes de meter los datos masivos (opcional pero recomendado)
    public void borrarTodasLasPistas() {
        String sql = "DELETE FROM PISTAS";
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Base de datos limpiada.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // NUEVO MÉTODO: Recibe una lista y la inserta de golpe (Batch)
    public void insertarPistasMasivas(List<Pista> pistas) {
        String sql = "INSERT INTO PISTAS (ID, NOMBRE, DIFICULTAD, ABIERTA, LONGITUD) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            // IMPORTANTE: Desactivar autocommit para hacerlo transaccional (mucho más rápido)
            con.setAutoCommit(false); 

            try (PreparedStatement pst = con.prepareStatement(sql)) {
                
                for (Pista p : pistas) {
                    pst.setInt(1, p.getId());
                    pst.setString(2, p.getNombre());
                    pst.setString(3, p.getDificultad().toString());
                    pst.setInt(4, p.isAbierta() ? 1 : 0);
                    pst.setDouble(5, p.getLongitud());
                    
                    pst.addBatch(); // Añadir al lote
                }

                // Ejecutar todo el lote de golpe
                int[] resultados = pst.executeBatch();
                con.commit(); // Confirmar cambios
                
                System.out.println("Se han insertado " + resultados.length + " pistas correctamente.");

            } catch (SQLException e) {
                con.rollback(); // Si falla, deshacer todo
                throw e;
            }

        } catch (SQLException e) {
            System.err.println("Error en inserción masiva: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // TAREA JDBC: Cargar datos agrupados en un Mapa usando Enums
    public Map<Dificultad, List<Pista>> cargarPistasPorDificultad() {
        Map<Dificultad, List<Pista>> mapa = new HashMap<>();
        String sql = "SELECT * FROM PISTAS";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                String nombre = rs.getString("NOMBRE");
                // Convertir String de BD a Enum
                Dificultad dificultad = Dificultad.valueOf(rs.getString("DIFICULTAD"));
                boolean abierta = rs.getInt("ABIERTA") == 1;
                double longitud = rs.getDouble("LONGITUD");

                Pista pista = new Pista(id, nombre, dificultad, abierta, longitud);

                // --- CAMBIO SOLICITADO: Lógica clásica con if(!containsKey) ---
                if (!mapa.containsKey(dificultad)) {
                    mapa.put(dificultad, new ArrayList<>());
                }
                mapa.get(dificultad).add(pista);
                // ---------------------------------------------------------------
            }

        } catch (SQLException e) {
            System.err.println("Error cargando pistas: " + e.getMessage());
        }

        return mapa;
    }

    // TAREA JDBC: Transacción (Update + Insert/Delete)
    public boolean cerrarPistaConLog(int idPista) {
        boolean exito = false;
        String sqlUpdate = "UPDATE PISTAS SET ABIERTA = 0 WHERE ID = ?";
        String sqlLog = "INSERT INTO LOGS (MENSAJE, FECHA) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(CONNECTION_STRING)) {
            con.setAutoCommit(false); // 1. INICIO TRANSACCIÓN (Desactivar autocommit)

            try (PreparedStatement pstUpd = con.prepareStatement(sqlUpdate);
                 PreparedStatement pstLog = con.prepareStatement(sqlLog)) {

                // Paso 1: Actualizar la pista
                pstUpd.setInt(1, idPista);
                int afectados = pstUpd.executeUpdate();

                // Solo si se actualizó la pista, procedemos a crear el log
                if (afectados > 0) {
                    // Paso 2: Insertar en Log
                    pstLog.setString(1, "Se cerró la pista con ID " + idPista);
                    pstLog.setString(2, new Date().toString());
                    pstLog.executeUpdate();

                    con.commit(); // 2. CONFIRMAR CAMBIOS (Todo ha ido bien)
                    exito = true;
                    System.out.println("Transacción completada: Pista cerrada y log creado.");
                } else {
                    // Si no se encontró la pista, no hacemos nada (o rollback por seguridad)
                    con.rollback();
                }
            } catch (SQLException e) {
                con.rollback(); // 3. DESHACER SI FALLA CUALQUIER COSA
                System.err.println("Error en transacción. Se ha hecho Rollback.");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exito;
    }

    // MÉTODO AUXILIAR PARA CREAR LA BD SI NO EXISTE
    private void crearDatosDePrueba() throws SQLException {
        try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
             Statement stmt = con.createStatement()) {
            
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS PISTAS (ID INTEGER PRIMARY KEY, NOMBRE TEXT, DIFICULTAD TEXT, ABIERTA INTEGER, LONGITUD REAL)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS LOGS (ID INTEGER PRIMARY KEY AUTOINCREMENT, MENSAJE TEXT, FECHA TEXT)");
            
            // Datos iniciales solo si la tabla está vacía para no duplicar
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PISTAS");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.executeUpdate("INSERT INTO PISTAS VALUES (1, 'El Águila', 'NEGRA', 1, 3.5)");
                stmt.executeUpdate("INSERT INTO PISTAS VALUES (2, 'Paseo', 'VERDE', 1, 1.2)");
                stmt.executeUpdate("INSERT INTO PISTAS VALUES (3, 'La Olla', 'AZUL', 1, 2.0)");
                stmt.executeUpdate("INSERT INTO PISTAS VALUES (4, 'Muralla', 'ROJA', 1, 2.8)");
                stmt.executeUpdate("INSERT INTO PISTAS VALUES (5, 'Debutantes', 'VERDE', 1, 0.5)");
            }
        }
    }
}