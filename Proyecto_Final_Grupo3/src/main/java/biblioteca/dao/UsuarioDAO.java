package biblioteca.dao;

import biblioteca.config.ConexionDB;
import biblioteca.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario validarLogin(String email, String password) {

        Usuario u = null;

        String sql = "SELECT U.*, " +
                "E.DESCRIPCION AS ESTATUS" +
                "FROM USUARIO U" +
                "INNER JOIN ESTADO E ON U.ID_ESTADO = E.ID" +
                "WHERE U.EMAIL = ? AND U.PASSWORD = ? AND E.DESCRIPCION = 'ACTIVO'";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)){

             ps.setString(1, email);
             ps.setString(2, password);

             try(ResultSet rs = ps.executeQuery()){

                 if(rs.next()){

                     u = new Usuario();

                     u.setId(rs.getInt("ID"));
                     u.setNombre(rs.getString("NOMBRE"));
                     u.setEmail(rs.getString("EMAIL"));
                     u.setRol(rs.getString("ROL"));
                     u.setEstado(rs.getString("ESTATUS"));
                 }
             }
        } catch (SQLException e) {

            System.err.println("Error en validarLogin");
        }

        return u;
    }
}