package biblioteca.dao;

import biblioteca.config.ConexionDB;
import biblioteca.config.Configuracion;
import biblioteca.model.Multa;
import biblioteca.model.PrestamoInfo;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MultaDAO {

    // Funciones publicas de escritura

    public boolean procesarPosibleMulta(int idPrestamo){

        Connection conTemp = ConexionDB.conectar();
        if (conTemp == null){
            return false;
        }

        try (Connection con = conTemp){

            con.setAutoCommit(false);

            try {

                PrestamoInfo info = obtenerInfoPrestamo(con, idPrestamo);

                if (info == null){
                    System.out.println(">> Error: Prestamo no encontrado. <<");
                }

                long diasRetraso = calcularDiasRetraso(info.getFechaSalida(),info.getFechaDevolucion());

                if (diasRetraso > 0 ){

                    double monto = diasRetraso * Configuracion.COSTO_MULTA_POR_DIA;

                    System.out.println(">> Alerta: Retraso de " + diasRetraso + " dias. Multa: "
                    + monto);

                    insertarMulta(con, info.getIdUsuario(), idPrestamo,monto);

                    con.commit();
                    return true;
                } else {
                    System.out.println(">> Devolucion a tiempo. Sin multas.");
                    return false;
                }
            } catch (SQLException e){
                System.out.println("Error al general multa: " + e.getMessage());
                con.rollback();
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error de conexion: " + e.getMessage());
            return false;
        }
    }


    // Funciones publicas de lectura

    public List<Multa> listarMultasPendientes(int idUsuario){

        List<Multa> lista = new ArrayList<>();

        String sql = "SELECT M.ID, M.MONTO, M.FECHA_GENERACION, L.TITULO, E.DESCRIPCION " +
                "FROM MULTA M " +
                "INNER JOIN PRESTAMO P ON M.ID_PRESTAMO = P.ID " +
                "INNER JOIN LIBRO L ON P.ID_LIBRO = L.ID " +
                "INNER JOIN ESTADO E ON M.ID_ESTADO = E.ID " +
                "WHERE M.ID_USUARIO = ? AND E.DESCRIPCION = 'PENDIENTE'";

        try (Connection con = ConexionDB.conectar();
            PreparedStatement ps = con.prepareStatement(sql);){

            ps.setInt(1, idUsuario);

            try (ResultSet rs = ps.executeQuery()){

                while (rs.next()){

                    Multa m = new Multa();

                    m.setId(rs.getInt("ID"));
                    m.setMonto(rs.getDouble("MONTO"));
                    m.setFechaGeneracion(rs.getDate("FECHA_GENERACION"));
                    m.setTituloLibro(rs.getString("TITULO"));
                    m.setEstado(rs.getString("DESCRIPCION"));
                    lista.add(m);
                }
            }
        } catch (SQLException e) {
            System.err.println(">> Error al listar multas pendientes: " + e.getMessage());
        }
        return lista;

    }

    // Funciones privada de escritura

    private void insertarMulta(Connection con, int idUsuario, int idPrestamo, double monto) throws SQLException{

        String sql = "INSERT INTO MULTA (ID_USUARIO, MONTO, FECHA_GENERACION, ID_ESTADO) " +
                "VALUES (?,?,?,CURRENT_DATE, 3)";

        try (PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, idUsuario);
            ps.setInt(2, idPrestamo);
            ps.setDouble(3, monto);

            ps.executeUpdate();
        }
    }

    // Funciones privadas de lectura

    private PrestamoInfo obtenerInfoPrestamo(Connection con, int idPrestamo) throws SQLException{

        String sql = "SELECT ID_USUARIO, FECHA_SALIDA, FECHA_DEVOLUCION FROM PRESTAMO WHERE ID = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, idPrestamo);

            try (ResultSet rs = ps.executeQuery()){

                if(rs.next()){

                    return new PrestamoInfo(
                            rs.getInt("ID_USUARIO"),
                            rs.getDate("FECHA_SALIDA"),
                            rs.getDate("FECHA_DEVOLUCION")
                    );
                }
            }
        }
        return null;
    }

    // Funciones privadas auxiliares

    private long calcularDiasRetraso(java.sql.Date salida, java.sql.Date devolucion){

        if (salida == null || devolucion == null){
            return 0;
        }

        LocalDate fechaInicio =  salida.toLocalDate();
        LocalDate fechaFin = devolucion.toLocalDate();

        long diasTotales = ChronoUnit.DAYS.between(fechaInicio, fechaFin);

        return Math.max(0, diasTotales - Configuracion.DIAS_PRESTAMO_GRATIS);

    }
}
