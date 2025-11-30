package biblioteca.dao;

import biblioteca.config.ConexionDB;
import biblioteca.model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    // Funciones publicas de lectura

    public List<String> listarCategoriasDisponibles(){

        List<String> categorias = new ArrayList<>();

        String sql = "SELECT DISTINCT L.CATEGORIA " +
                "FROM LIBRO L " +
                "INNER JOIN ESTADO E ON L.ID_ESTADO = E.ID " +
                "WHERE E.DESCRIPCION = 'ACTIVO' " +
                "ORDER BY L.CATEGORIA ASC";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while (rs.next()){

                String categoria = rs.getString("CATEGORIA");

                if (categoria != null && !categoria.isEmpty()){

                    categorias.add(categoria);
                }
            }

        } catch (SQLException e){
            System.err.println("Error al obtener categorias: " + e.getMessage());
        }

        return categorias;
    }

    public List<String> obtenerReporteMasPrestados() {
        List<String> reporte = new ArrayList<>();

        String sql = "SELECT L.TITULO, COUNT(P.ID) AS TOTAL_PRESTAMOS " +
                "FROM PRESTAMO P " +
                "INNER JOIN LIBRO L ON P.ID_LIBRO = L.ID " +
                "GROUP BY L.TITULO " +
                "ORDER BY TOTAL_PRESTAMOS DESC";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                String titulo = rs.getString("TITULO");
                int cantidad = rs.getInt("TOTAL_PRESTAMOS");
                reporte.add(titulo + " (" + cantidad + " préstamos)");
            }
        } catch (SQLException e) {

            System.err.println("Error generando reporte: " + e.getMessage());
        }
        return reporte;
    }

    public List<Libro> listarLibros() {

        List<Libro> lista = new ArrayList<>();

        String sql = "SELECT L.ID, L.TITULO, L.AUTOR, L.CATEGORIA, L.DISPONIBLE, " +
                "E.DESCRIPCION " +
                "FROM LIBRO L " +
                "INNER JOIN ESTADO E ON L.ID_ESTADO = E.ID " +
                "WHERE E.DESCRIPCION = 'ACTIVO'";

        try (Connection con = ConexionDB.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Libro l = new Libro();

                l.setId(rs.getInt("ID"));
                l.setTitulo(rs.getString("TITULO"));
                l.setAutor(rs.getString("AUTOR"));
                l.setCategoria(rs.getString("CATEGORIA"));
                l.setDisponible(rs.getBoolean("DISPONIBLE"));
                l.setEstado(rs.getString("DESCRIPCION"));

                lista.add(l);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar libros: " + e.getMessage());
        }
        return lista;
    }

    public List<Libro> buscarLibros(String busqueda) {

        List<Libro> lista = new ArrayList<>();

        String sql = "SELECT L.ID, L.TITULO, L.AUTOR, L.CATEGORIA, L.DISPONIBLE, E.DESCRIPCION AS ESTATUS " +
                "FROM LIBRO L " +
                "INNER JOIN ESTADO E ON L.ID_ESTADO = E.ID " +
                "WHERE E.DESCRIPCION = 'ACTIVO' " +
                "AND (L.TITULO LIKE ? OR L.AUTOR LIKE ? OR L.CATEGORIA LIKE ?)";

        try (Connection con = ConexionDB.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            String parametro = "%" + busqueda + "%"; // para busquedas parciales

            ps.setString(1, parametro);
            ps.setString(2, parametro);
            ps.setString(3, parametro);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Libro l = new Libro();

                    l.setId(rs.getInt("ID"));
                    l.setTitulo(rs.getString("TITULO"));
                    l.setAutor(rs.getString("AUTOR"));
                    l.setCategoria(rs.getString("CATEGORIA"));
                    l.setDisponible(rs.getBoolean("DISPONIBLE"));
                    l.setEstado(rs.getString("ESTATUS"));
                    lista.add(l);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar libros: " + e.getMessage());
        }
        return lista;
    }

    // Funciones publicas de escritura

    public boolean registrarLibro(Libro l) {

        if (l == null){return false;}

        String sql = "INSERT INTO LIBRO (TITULO, AUTOR, CATEGORIA, DISPONIBLE, ID_ESTADO) VALUES " +
                "(?, ?, ?, TRUE, 1)";

        try (Connection con = ConexionDB.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, l.getTitulo());
            ps.setString(2, l.getAutor());
            ps.setString(3, l.getCategoria());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println(">> Se ha registrado correctamente el libro: " + l.getTitulo());
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Error al registrar libro: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarLibro(int idLibro) {
        Connection conTemp = ConexionDB.conectar();
        if (conTemp == null) return false;

        try (Connection con = conTemp) {
            con.setAutoCommit(false);

            try {

                String sqlCancelarReservas = "UPDATE RESERVA SET ID_ESTADO = 2 " +
                        "WHERE ID_LIBRO = ? AND ID_ESTADO = 1";

                try (PreparedStatement psRes = con.prepareStatement(sqlCancelarReservas)) {
                    psRes.setInt(1, idLibro);
                    psRes.executeUpdate();
                }

                // 2. Inactivar el Libro
                String sqlDeleteLibro = "UPDATE LIBRO SET ID_ESTADO = 2, DISPONIBLE = FALSE WHERE ID = ?";
                try (PreparedStatement psLib = con.prepareStatement(sqlDeleteLibro)) {
                    psLib.setInt(1, idLibro);
                    psLib.executeUpdate();
                }

                con.commit();
                return true;

            } catch (SQLException e) {
                con.rollback();
                System.err.println("Error eliminando libro: " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error conexión: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarLibro(Libro l) {
        String sql = "UPDATE LIBRO SET TITULO=?, AUTOR=?, CATEGORIA=? WHERE ID=?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, l.getTitulo());
            ps.setString(2, l.getAutor());
            ps.setString(3, l.getCategoria());
            ps.setInt(4, l.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar libro: " + e.getMessage());
            return false;
        }
    }
}
