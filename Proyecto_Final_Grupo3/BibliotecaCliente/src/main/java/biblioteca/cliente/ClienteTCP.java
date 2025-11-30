package biblioteca.cliente;

import biblioteca.model.*;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClienteTCP {

    private static ClienteTCP instancia;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private final String HOST = "localhost";
    private final int PUERTO = 9999;

    private ClienteTCP(){

        try{

            socket = new Socket(HOST, PUERTO);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println(">> Conectado al servidor " + HOST + ":" + PUERTO);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: No se puede conectar al servidor.\n" + e.getMessage());
            System.exit(1); // Cerramos la app si no hay servidor
        }
    }

    public static ClienteTCP getInstance() {
        if (instancia == null) {
            instancia = new ClienteTCP();
        }
        return instancia;
    }

    public void cerrarConexion() {
        try {
            if (out != null) out.writeObject("SALIR");
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Usuarios

    public Usuario valildarLogin(String email, String password){

        try {
            out.writeObject("LOGIN");

            Usuario u = new Usuario();
            u.setEmail(email);
            u.setPassword(password);

            out.writeObject(u);
            out.flush();

            return (Usuario) in.readObject();
        } catch (Exception e){
            System.err.println("Error al validar login en el ClienteTCP: " + e.getMessage() );
            return null;
        }
    }

    public boolean registrarUsuario(Usuario u) {
        try {
            out.writeObject("REGISTRAR_USUARIO");
            out.writeObject(u);
            out.flush();
            return (boolean) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en registrarUsuario del ClienteTCP: " + e.getMessage());
            return false;
        }
    }

    public List<Usuario> listarUsuarios() {
        try {
            out.writeObject("LISTAR_USUARIOS");
            out.flush();
            return (List<Usuario>) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en listarUsuarios del ClienteTCP: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean eliminarUsuario(int id) {
        try {
            out.writeObject("ELIMINAR_USUARIO");
            out.writeObject(id);
            out.flush();
            return (boolean) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en eliminarUsuario del ClienteTCP: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarUsuario(Usuario u) {
        try {
            out.writeObject("ACTUALIZAR_USUARIO");
            out.writeObject(u);
            out.flush();
            return (boolean) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en actualizarUsuario del ClienteTCP: " + e.getMessage());
            return false;
        }
    }

    // Libros

    public List<Libro> listarLibros() {
        try {
            out.writeObject("LISTAR_LIBROS");
            out.flush();
            return (List<Libro>) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en listarLibros del ClienteTCP: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Libro> buscarLibros(String busqueda) {
        try {
            out.writeObject("BUSCAR_LIBROS");
            out.writeObject(busqueda);
            out.flush();
            return (List<Libro>) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en buscarLibros del ClienteTCP: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean registrarLibro(Libro l) {
        try {
            out.writeObject("REGISTRAR_LIBRO");
            out.writeObject(l);
            out.flush();
            return (boolean) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en registrarLibro del ClienteTCP: " + e.getMessage());
            return false;
        }
    }

    public List<String> listarCategoriasDisponibles() {
        try {
            out.writeObject("CATEGORIAS_LIBROS");
            out.flush();
            return (List<String>) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en listarCategoriasDisponibles del ClienteTCP: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<String> obtenerReporteMasPrestados() {
        try {
            out.writeObject("REPORTE_MAS_PRESTADOS");
            out.flush();
            return (List<String>) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en obtenerReporteMasPrestados del ClienteTCP: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Prestamos

    public List<Prestamo> listarPrestamosActivos() {
        try {
            out.writeObject("LISTAR_PRESTAMOS_ACTIVOS");
            out.flush();
            return (List<Prestamo>) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en listarPrestamosActivos del ClienteTCP: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Prestamo> listarHistorialPorUsuario(int idUsuario) {
        try {
            out.writeObject("HISTORIAL_PRESTAMOS");
            out.writeObject(idUsuario);
            out.flush();
            return (List<Prestamo>) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en listarHistorialPorUsuario del ClienteTCP: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean registrarPrestamo(int idUsuario, int idLibro) {
        try {
            out.writeObject("REGISTRAR_PRESTAMO");
            out.writeObject(idUsuario);
            out.writeObject(idLibro);
            out.flush();
            return (boolean) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en registrarPrestamo del ClienteTCP: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarDevolucion(int idPrestamo, int idLibro) {
        try {
            out.writeObject("REGISTRAR_DEVOLUCION");
            out.writeObject(idPrestamo);
            out.writeObject(idLibro);
            out.flush();
            return (boolean) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en registrarDevolucion del ClienteTCP: " + e.getMessage());
            return false;
        }
    }

    // Reservas

    public List<Reserva> listarReservasActivas() {
        try {
            out.writeObject("LISTAR_RESERVAS_ACTIVAS");
            out.flush();
            return (List<Reserva>) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en listarReservasActivas del ClienteTCP: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Reserva> listarReservasPorUsuario(int idUsuario) {
        try {
            out.writeObject("LISTAR_RESERVAS_USUARIO");
            out.writeObject(idUsuario);
            out.flush();
            return (List<Reserva>) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en listarReservasPorUsuario del ClienteTCP: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean registrarReserva(int idUsuario, int idLibro) {
        try {
            out.writeObject("REGISTRAR_RESERVA");
            out.writeObject(idUsuario);
            out.writeObject(idLibro);
            out.flush();
            return (boolean) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en registrarReserva del ClienteTCP: " + e.getMessage());
            return false;
        }
    }

    public boolean cancelarReserva(int idReserva) {
        try {
            out.writeObject("CANCELAR_RESERVA");
            out.writeObject(idReserva);
            out.flush();
            return (boolean) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en cancelarReserva del ClienteTCP: " + e.getMessage());
            return false;
        }
    }

    // Multas

    public List<Multa> listarMultasPendientes(int idUsuario) {
        try {
            out.writeObject("LISTAR_MULTAS_PENDIENTES");
            out.writeObject(idUsuario);
            out.flush();
            return (List<Multa>) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en listarMultasPendientes del ClienteTCP: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Multa> listarTodasLasMultas() {
        try {
            out.writeObject("LISTAR_TODAS_MULTAS");
            out.flush();
            return (List<Multa>) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en listarTodasLasMultas del ClienteTCP: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean registrarPagoMulta(int idMulta) {
        try {
            out.writeObject("PAGAR_MULTA");
            out.writeObject(idMulta);
            out.flush();
            return (boolean) in.readObject();
        } catch (Exception e) {
            System.err.println("Error en registrarPagoMulta del ClienteTCP: " + e.getMessage());
            return false;
        }
    }
}
