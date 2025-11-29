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


    // Metodos privados de lectura

    private int obtenerLibroDeReserva(Connection con, int idReserva) throws SQLException {
        String sql = "SELECT ID_LIBRO FROM RESERVA WHERE ID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("ID_LIBRO");
            }
        }
        return -1;
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

    // Metodos privados de escritura

    private void cambiarEstadoReserva(Connection con, int idReserva, int idNuevoEstado) throws SQLException {
        String sql = "UPDATE RESERVA SET ID_ESTADO = ? WHERE ID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idNuevoEstado);
            ps.setInt(2, idReserva);
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


    // Metodos publicos de escritura

    public boolean cancelarReserva(int idReserva){

        Connection conTemp = ConexionDB.conectar();

        if (conTemp == null) {return false; }

        try(Connection con = conTemp){

            con.setAutoCommit(false);

            try {

                int idLibro = obtenerLibroDeReserva(con, idReserva);

                if (idLibro == -1) { return false;}

                cambiarEstadoReserva(con, idReserva, 2);

                liberarLibro(con, idLibro);

                con.commit();
                System.out.println(">> Reserva " + idReserva + " cancelada. Libro " + idLibro + " liberado");
                return true;
            } catch (SQLException e){
                con.rollback();
                System.err.println("Error al cancelar reserva: " + e.getMessage());
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error de conexion: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarReserva(int idUsuario, int idLibro) {

        Connection contemp = ConexionDB.conectar();
        if (contemp == null) {
            return false;
        }

        try (Connection con = contemp) {

            con.setAutoCommit(false);

            try {

                if (!verificarUsuarioActivo(con, idUsuario)) {
                    System.out.println(">> Error: El usuario " + idUsuario + " no valido. <<");
                    return false;
                }

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


    // Metodos publicos de lectura

    public List<Reserva> listarReservasActivas() {

        List<Reserva> lista = new ArrayList<>();

        // CAMBIO: Agregamos U.ID y L.ID a la consulta
        String sql = "SELECT R.ID, R.FECHA_RESERVA, " +
                "U.ID AS ID_USUARIO, U.NOMBRE, " +
                "L.ID AS ID_LIBRO, L.TITULO, " +
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

                r.setIdUsuario(rs.getInt("ID_USUARIO"));
                r.setNombreUsuario(rs.getString("NOMBRE"));

                r.setIdLibro(rs.getInt("ID_LIBRO"));
                r.setTituloLibro(rs.getString("TITULO"));

                r.setEstado(rs.getString("DESCRIPCION"));

                lista.add(r);
            }
        } catch (SQLException e) {
            System.err.println(">> Error en listado de reservas activas: " + e.getMessage());
        }
        return lista;
    }

    public List<Reserva> listarReservasPorUsuario(int idUsuario){

        List<Reserva> lista = new ArrayList<>();

        String sql = "SELECT R.ID, R.FECHA_RESERVA, " +
                "L.ID AS ID_LIBRO, L.TITULO, " +
                "E.DESCRIPCION AS ESTADO " +
                "FROM RESERVA R " +
                "INNER JOIN LIBRO L ON R.ID_LIBRO = L.ID " +
                "INNER JOIN ESTADO E ON R.ID_ESTADO = E.ID " +
                "WHERE R.ID_USUARIO = ? AND E.DESCRIPCION = 'ACTIVO'";

        try (Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1,idUsuario);

            try(ResultSet rs = ps.executeQuery()){

                while(rs.next()){

                    Reserva r = new Reserva();
                    r.setId(rs.getInt("ID"));
                    r.setFechaReserva(rs.getDate("FECHA_RESERVA"));
                    r.setIdLibro(rs.getInt("ID_LIBRO"));
                    r.setTituloLibro(rs.getString("TITULO"));
                    r.setEstado(rs.getString("ESTADO"));
                    lista.add(r);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar reservas por usuario: " + e.getMessage());
        }
        return lista;
    }

}
