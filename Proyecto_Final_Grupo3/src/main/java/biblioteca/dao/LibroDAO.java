package biblioteca.dao;

import biblioteca.config.ConexionDB;
import biblioteca.model.Libro;
import biblioteca.model.Prestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    public List<Libro> listarLibros(){

        List<Libro> lista = new ArrayList<>();

        String sql = "SELECT L.ID, L.TITULO, L.AUTOR, L.CATEGORIA, L.DISPONIBLE" +
                "E.DESCRIPCION " +
                "FROM LIBRO L " +
                "INNER JOIN ESTADO E ON L.ID_ESTADO = E.ID " +
                "WHERE E.DESCRIPCION = 'ACTIVO'";

        try(Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

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
}
