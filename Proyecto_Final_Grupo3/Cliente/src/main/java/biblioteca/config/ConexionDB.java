package biblioteca.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/BIBLIOTECA_DB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASS = "gabriel";

    public static Connection conectar(){

        Connection con = null;

        try{

            //Cargamos el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Establecemos la conexion
            con = DriverManager.getConnection(URL, USER, PASS);

            System.out.println(">> Conexión a BD exitosa <<");


        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontro el Driver de MySQL");
        } catch (SQLException e) {
            System.err.println("Error de conexion: " + e.getMessage());
        }

        return con;
    }
}
