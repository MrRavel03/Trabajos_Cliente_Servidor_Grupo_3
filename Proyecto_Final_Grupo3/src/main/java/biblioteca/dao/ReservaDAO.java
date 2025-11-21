package biblioteca.dao;

import biblioteca.config.ConexionDB;
import biblioteca.model.Reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public boolean registrarReserva(int idUsuario, int idLibro) {

        Connection contemp = ConexionDB.conectar();
        if (contemp == null) {
            return false;
        }

        try (Connection con = contemp) {

            con.setAutoCommit(false);

            try {

                if (!verificarDisponibilidad(con, idLibro)) {
                    System.out.println(">> Error: El libro " + idLibro + " no esta disponible. <<");
                    return false;
                }

                insertarReserva(con, idUsuario, idLibro);

                bloquearLibro(con, idLibro);

                con.commit();
                System.out.println(">> Reserva del libro: " + idLibro + " completada <<");
                return true;
            } catch (SQLException e) {
                System.err.println("Error en el proceso de reserva: " + e.getMessage());
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error: rollback falló: " + ex.getMessage());
                }
                return false;
            }
        } catch (SQLException e) {
            System.err.println(">> Error de conexion: " + e.getMessage());
            return false;
        }
    }

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

    private void insertarReserva(Connection con, int idUsuario, int idLibro) throws SQLException {

        String sql = "INSERT INTO RESERVA (ID_USUARIO, ID_LIBRO, FECHA_RESERVA, ID_ESTADO) " +
                "VALUES (? , ?, CURRENT_DATE, 1)";

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

    public List<Reserva> listarReservasActivas() {

        List<Reserva> lista = new ArrayList<>();

        String sql = "SELECT R.ID, R.FECHA_RESERVA, " +
                "U.NOMBRE, " +
                "L.TITULO, " +
                "E.DESCRIPCION " +
                "FROM RESERVA R " +
                "INNER JOIN USUARIO U ON R.ID_USUARIO = U.ID " +
                "INNER JOIN LIBRO L ON R.ID_LIBRO = L.ID " +
                "INNER JOIN ESTADO E ON R.ID_ESTADO = E.ID " +
                "WHERE E.DESCRIPCION = 'ACTIVO'";

        try (Connection con = ConexionDB.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Reserva r = new Reserva();

                r.setId(rs.getInt("ID"));
                r.setFechaReserva(rs.getDate("FECHA_RESERVA"));
                r.setNombreUsuario(rs.getString("NOMBRE"));
                r.setTituloLibro(rs.getString("TITULO"));
                r.setEstado(rs.getString("DESCRIPCION"));

                lista.add(r);
            }
        } catch (SQLException e) {
            System.err.println(">> Error en listado de reservas activas: " + e.getMessage());
        }
        return lista;
    }

}
