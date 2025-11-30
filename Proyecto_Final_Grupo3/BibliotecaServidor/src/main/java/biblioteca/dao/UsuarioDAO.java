package biblioteca.dao;

import biblioteca.config.ConexionDB;
import biblioteca.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Funciones publicas de lectura

    public List<Usuario> listarUsuarios() {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT U.ID, U.NOMBRE, U.EMAIL, U.ROL, E.DESCRIPCION AS ESTADO " +
                "FROM USUARIO U " +
                "INNER JOIN ESTADO E ON U.ID_ESTADO = E.ID " +
                "WHERE E.DESCRIPCION = 'ACTIVO'";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("ID"));
                u.setNombre(rs.getString("NOMBRE"));
                u.setEmail(rs.getString("EMAIL"));
                u.setRol(rs.getString("ROL"));
                u.setEstado(rs.getString("ESTADO"));
                lista.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

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

            System.err.println("Error en validar Login");
        }

        return u;
    }



    // Funciones publicas de escritura

    public boolean actualizarUsuario(Usuario u) {
        String sql;
        boolean actualizarPass = false;

        if (u.getPassword() != null && !u.getPassword().isEmpty()) {
            // Si trae contraseña nueva, la actualizamos
            sql = "UPDATE USUARIO SET NOMBRE=?, EMAIL=?, ROL=?, PASSWORD=? WHERE ID=?";
            actualizarPass = true;
        } else {
            // Si no trae, dejamos la que tiene (no tocamos ese campo)
            sql = "UPDATE USUARIO SET NOMBRE=?, EMAIL=?, ROL=? WHERE ID=?";
        }

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {


            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getRol());

            if (actualizarPass) {

                ps.setString(4, u.getPassword());
                ps.setInt(5, u.getId());
            } else {

                ps.setInt(4, u.getId());
            }

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarUsuario(int idUsuario) {
        String sql = "UPDATE USUARIO SET ID_ESTADO = 2 WHERE ID = ?"; // 2 = INACTIVO

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

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