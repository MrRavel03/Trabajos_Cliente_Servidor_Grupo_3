package biblioteca.dao;

import biblioteca.config.ConexionDB;
import biblioteca.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Funciones publicas de lectura

    public Usuario validarLogin(String email, String password) {

        Usuario u = null;

        String sql = "SELECT U.*, " +
                "E.DESCRIPCION AS ESTATUS " +
                "FROM USUARIO U " +
                "INNER JOIN ESTADO E ON U.ID_ESTADO = E.ID " +
                "WHERE U.EMAIL = ? AND U.PASSWORD = ? AND E.DESCRIPCION = 'ACTIVO'";

        try (Connection con = ConexionDB.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

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

    // Funciones publicas de escritura

    public boolean registrarUsuario(Usuario u) {

        if (u == null) {
            return false;
        }

        Connection conTemp = ConexionDB.conectar();

        if (conTemp == null) {
            return false;
        }

        try (Connection con = conTemp) {

            if (validarEmailExiste(con, u.getEmail())) {
                System.out.println(">> Error: Correo electronico: " + u.getEmail() + " ya esta registrado. <<");
                return false; 
            }

            insertarUsuario(con, u);

            System.out.println(">> Se ha registrado correctamente al usuario: " + u.getNombre());
            return true;

        } catch (Exception e) {
            System.err.println(">> Error al registrar el usuario: " + u.getNombre() + ": " + e.getMessage());
            return false;
        }

    }

    // Funciones privadas de validacion

    private boolean validarEmailExiste(Connection con, String email) throws SQLException {

        String sql = "SELECT ID FROM USUARIO WHERE EMAIL = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                return rs.next();
            }
        }
    }

    // Funciones privadas de escritura

    private void insertarUsuario(Connection con, Usuario u) throws SQLException {

        String sql = "INSERT INTO USUARIO (NOMBRE, EMAIL, PASSWORD, ROL, ID_ESTADO) " +
                "VALUES (?, ?, ?, ?, 1)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getRol());

            ps.executeUpdate();
        }
    }
}