package biblioteca.dao;

import biblioteca.config.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrestamoDAO {

    public boolean regristrarPrestamo(int idUsuario, int idLibro){

        // conectamos con la dase de datos
        Connection con = ConexionDB.conectar();

        PreparedStatement psInsert = null;
        PreparedStatement psUpdate = null;

        boolean resultado = false;

        String sqlInsert = "INSERT INTO PRESTAMOS (ID_USUARIO, ID_LIBRO, " +
                "FECHA_SALIDA, ID_ESTADO) VALUES " +
                "(?, ?, CURRENT_DATE, 1)";

        String sqlUpdateLibro = "UPDATE LIBROS SET DISPONIBLE = FALSE WHERE " +
                "ID = ?";

        try{
            con.setAutoCommit(false);

            //Insertamos el prestamo
            psInsert = con.prepareStatement(sqlInsert);
            psInsert.setInt(1,idUsuario);
            psInsert.setInt(2, idLibro);
            psInsert.executeUpdate();

            //Marcamos 

        } catch (SQLException e) {
            System.err.println("Error en transaccion de préstamo:" + e.getMessage());
            try{
                if (con != null){
                    con.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Error: rollback falló: " + ex.getMessage());
            }
        }
    }
}
