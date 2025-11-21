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

    public boolean registrarPrestamo(int idUsuario, int idLibro) {

        String sqlInsert = "INSERT INTO PRESTAMO (ID_USUARIO, ID_LIBRO, " +
                "FECHA_SALIDA, ID_ESTADO) VALUES " +
                "(?, ?, CURRENT_DATE, 1)";

        String sqlUpdateLibro = "UPDATE LIBRO SET DISPONIBLE = FALSE WHERE " +
                "ID = ?";

        // conectamos con la dase de datos
        Connection conTemp = ConexionDB.conectar();
        if (conTemp == null) {
            return false;
        }

        try (Connection con = conTemp) {

            con.setAutoCommit(false);

            try (PreparedStatement psInsert = con.prepareStatement(sqlInsert);
                 PreparedStatement psUpdate = con.prepareStatement(sqlUpdateLibro)) {

                //Insertamos el prestamo
                psInsert.setInt(1, idUsuario);
                psInsert.setInt(2, idLibro);
                psInsert.executeUpdate();

                //Marcamos como no disponible el libro
                psUpdate.setInt(1, idLibro);
                psUpdate.executeUpdate();

                //Guardamos
                con.commit();
                System.out.println(">> Transacción exitosa: Préstamo registrado <<");

                return true;

            } catch (SQLException e) {
                System.err.println("Error en transaccion de préstamo:  " + e.getMessage());
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.err.println("Error: rollback falló: " + ex.getMessage());
                }
            }
        } catch (SQLException e) {

            System.err.println("Error de conexion:  " + e.getMessage());
        }

        return false;

    }

    public boolean registrarDevolucion(int idPrestamo, int idLibro) {

        String sqlClosePrestamo = "UPDATE PRESTAMO SET ID_ESTADO = 2, FECHA_DEVOLUION = CURRENT_DATE WHERE ID = ?";

        String sqlFreeLibro = "UPDATE LIBRO SET DISPONIBLE = TRUE WHERE ID = ?";

        Connection contemp = ConexionDB.conectar();
        if (contemp == null) {
            return false;
        }

        try (Connection con = contemp) {

            con.setAutoCommit(false);

            try (PreparedStatement psClose = con.prepareStatement(sqlClosePrestamo);
                 PreparedStatement psFree = con.prepareStatement(sqlFreeLibro)) {

                // Cerramos el prestamo
                psClose.setInt(1, idPrestamo);
                psClose.executeUpdate();

                // Ponemos el libro como disponible
                psFree.setInt(1, idLibro);
                psFree.executeUpdate();

                con.commit();
                System.out.println(">> Transacción exitosa: Préstamo cerrado y libro devuelto <<");
                return true;
            } catch (SQLException e) {
                System.err.println("Error en transaccion de devolucion:  " + e.getMessage());
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

        public List<Prestamo> listarPrestamosActivos(){

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

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

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
}
