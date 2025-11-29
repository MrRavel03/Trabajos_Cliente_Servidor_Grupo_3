package biblioteca.dao;

import biblioteca.config.ConexionDB;
import biblioteca.model.Prestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    // Funciones publicas: Operaciones de escritura

    public boolean registrarPrestamo(int idUsuario, int idLibro) {

        // conectamos con la dase de datos
        Connection conTemp = ConexionDB.conectar();
        if (conTemp == null) {
            return false;
        }

        try (Connection con = conTemp) {

            con.setAutoCommit(false);

            try {

                if (!verificarUsuarioActivo(con, idUsuario)) {
                    System.out.println(">> Error: El usuario " + idUsuario + " no valido. <<");
                    return false;
                }

                if (!verificarDisponibilidad(con, idLibro)) {
                    System.out.println(">> Error: El libro no está disponible <<");
                    return false;
                }

                insertarPrestamo(con, idUsuario, idLibro);

                bloquearLibro(con, idLibro);

                // Guardamos
                con.commit();
                System.out.println(">> Préstamo registrado del libro: " + idLibro + " <<");

                return true;

            } catch (SQLException e) {

                System.err.println("Error en transaccion de préstamo:  " + e.getMessage());
                con.rollback();
                return false;
            }
        } catch (SQLException e) {

            System.err.println("Error de conexion:  " + e.getMessage());
            return false;
        }
    }

    public boolean registrarDevolucion(int idPrestamo, int idLibro) {

        Connection contemp = ConexionDB.conectar();
        if (contemp == null) {
            return false;
        }

        try (Connection con = contemp) {

            con.setAutoCommit(false);

            try {

                if (!verificarPrestamoActivo(con, idPrestamo, idLibro)) {
                    System.out.println(">> Error: El prestamo no está activo o el libro no coincide<<");
                    return false;
                }

                cerrarPrestamo(con, idPrestamo);

                liberarLibro(con, idLibro);

                con.commit();
                System.out.println(
                        ">> Devolucion realizada del prestamo: " + idPrestamo + ", del libro: " + idLibro + " <<");
                return true;
            } catch (SQLException e) {
                System.err.println("Error en transaccion de devolucion: " + e.getMessage());
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error: rollback falló: " + ex.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error conexión: " + e.getMessage());
        }
        return false;
    }

    // Funciones publicas: Operaciones de lectura

    public List<Prestamo> listarPrestamosActivos() {

        List<Prestamo> lista = new ArrayList<>();

        String sql = "SELECT P.ID, P.FECHA_SALIDA, P.FECHA_DEVOLUCION, " +
                "U.ID AS UID, U.NOMBRE AS UNOMBRE, " +
                "L.ID AS LID, L.TITULO AS LTITULO, " +
                "E.DESCRIPCION AS ESTATUS " +
                "FROM PRESTAMO P " +
                "INNER JOIN USUARIO U ON P.ID_USUARIO = U.ID " +
                "INNER JOIN LIBRO L ON P.ID_LIBRO = L.ID " +
                "INNER JOIN ESTADO E ON P.ID_ESTADO = E.ID " +
                "WHERE E.DESCRIPCION = 'ACTIVO'";

        try (Connection con = ConexionDB.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Prestamo p = new Prestamo();

                p.setId(rs.getInt("ID"));
                p.setFechaSalida(rs.getDate("FECHA_SALIDA"));
                p.setFechaDevolucion(rs.getDate("FECHA_DEVOLUCION"));

                p.setIdUsuario(rs.getInt("UID"));
                p.setNombreUsuario(rs.getString("UNOMBRE"));

                p.setIdLibro(rs.getInt("LID"));
                p.setTituloLibro(rs.getString("LTITULO"));

                p.setEstado(rs.getString("ESTATUS"));

                lista.add(p);

            }
        } catch (SQLException e) {
            System.err.println("Error al listar prestamos: " + e.getMessage());
        }
        return lista;
    }

    public List<Prestamo> listarHistorialPorUsuario(int idUsuario) {

        List<Prestamo> lista = new ArrayList<>();

        String sql = "SELECT P.ID, P.FECHA_SALIDA, P.FECHA_DEVOLUCION, " +
                "U.ID AS UID, U.NOMBRE AS UNOMBRE, " +
                "L.ID AS LID, L.TITULO AS LTITULO, " +
                "E.DESCRIPCION AS ESTATUS " +
                "FROM PRESTAMO P " +
                "INNER JOIN USUARIO U ON P.ID_USUARIO = U.ID " +
                "INNER JOIN LIBRO L ON P.ID_LIBRO = L.ID " +
                "INNER JOIN ESTADO E ON P.ID_ESTADO = E.ID " +
                "WHERE U.ID = ? ORDER BY P.FECHA_SALIDA DESC";

        try (Connection con = ConexionDB.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Prestamo p = new Prestamo();

                    p.setId(rs.getInt("ID"));
                    p.setFechaSalida(rs.getDate("FECHA_SALIDA"));
                    p.setFechaDevolucion(rs.getDate("FECHA_DEVOLUCION"));

                    p.setIdUsuario(rs.getInt("UID"));
                    p.setNombreUsuario(rs.getString("UNOMBRE"));

                    p.setIdLibro(rs.getInt("LID"));
                    p.setTituloLibro(rs.getString("LTITULO"));

                    p.setEstado(rs.getString("ESTATUS"));

                    lista.add(p);

                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar prestamos: " + e.getMessage());
        }
        return lista;
    }

    // Funciones privadas: Validaciones

    private boolean verificarDisponibilidad(Connection con, int idLibro) throws SQLException {

        String sql = "SELECT DISPONIBLE FROM LIBRO WHERE ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idLibro);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("DISPONIBLE");
                }
            }
        }
        return false;
    }

    private boolean verificarPrestamoActivo(Connection con, int idPrestamo, int idLibro) throws SQLException {

        String sql = "SELECT ID_LIBRO FROM PRESTAMO WHERE ID = ? AND ID_ESTADO = 1";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPrestamo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    int idLibroRegistrado = rs.getInt("ID_LIBRO");

                    return idLibroRegistrado == idLibro;
                }
            }
        }
        return false;
    }

    private boolean verificarUsuarioActivo(Connection con, int idUsuario) throws SQLException {

        String sql = "SELECT U.ID FROM USUARIO U " +
                "INNER JOIN ESTADO E ON U.ID_ESTADO = E.ID " +
                "WHERE U.ID = ? AND E.DESCRIPCION = 'ACTIVO'";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()) {

                return rs.next();
            }
        }
    }

    // Funciones privadas: Operaciones de escritura

    private void insertarPrestamo(Connection con, int idUsuario, int idLibro) throws SQLException {

        String sql = "INSERT INTO PRESTAMO (ID_USUARIO, ID_LIBRO, FECHA_SALIDA, ID_ESTADO) VALUES (?, ?, CURRENT_DATE, 1)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, idLibro);
            ps.executeUpdate();
        }
    }

    private void bloquearLibro(Connection con, int idLibro) throws SQLException {

        String sql = "UPDATE LIBRO SET DISPONIBLE = FALSE WHERE ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idLibro);
            ps.executeUpdate();
        }
    }

    private void cerrarPrestamo(Connection con, int idPrestamo) throws SQLException {

        String sql = "UPDATE PRESTAMO SET ID_ESTADO = 2, FECHA_DEVOLUCION = CURRENT_DATE WHERE ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPrestamo);
            ps.executeUpdate();
        }
    }

    private void liberarLibro(Connection con, int idLibro) throws SQLException {

        String sql = "UPDATE LIBRO SET DISPONIBLE = TRUE WHERE ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idLibro);
            ps.executeUpdate();
        }
    }
}