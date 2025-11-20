package biblioteca.main;

import biblioteca.config.ConexionDB;
import biblioteca.dao.PrestamoDAO;
import biblioteca.model.Libro;
import biblioteca.model.Prestamo;

import java.sql.Connection;

public class Main {
    static void main() {

        //Libro i = new Libro(1,"Libro viejo","Angie Torres","Hola",true,"Hola");

        //Connection con = ConexionDB.conectar();S

        PrestamoDAO pDAO= new PrestamoDAO();
        System.out.println(pDAO.listarPrestamosActivos());
    }
}
