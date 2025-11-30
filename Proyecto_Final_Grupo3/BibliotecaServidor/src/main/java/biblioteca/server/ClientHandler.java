package biblioteca.server;

import biblioteca.dao.*;
import biblioteca.model.Libro;
import biblioteca.model.Prestamo;
import biblioteca.model.Usuario;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private final UsuarioDAO usuarioDAO;
    private final LibroDAO libroDAO;
    private final PrestamoDAO prestamoDAO;
    private final ReservaDAO reservaDAO;
    private final MultaDAO multaDAO;

    public ClientHandler(Socket socket){

        this.socket = socket;

        this.usuarioDAO = new UsuarioDAO();
        this.libroDAO = new LibroDAO();
        this.prestamoDAO = new PrestamoDAO();
        this.reservaDAO = new ReservaDAO();
        this.multaDAO = new MultaDAO();
    }

    @Override
    public void run(){

        try{

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (true){

                Object objetoRecibido = in.readObject();

                if (objetoRecibido instanceof String){
                    String peticion = (String) objetoRecibido;


                    procesarPeticion(peticion);
                }
            }
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Cliente desconectado: " + socket.getInetAddress());
        }finally {
            cerrarConexion();
        }
    }

    private void procesarPeticion(String comando) throws IOException, ClassNotFoundException{

        System.out.println(">> Procesando:  " + comando);

        switch (comando){

            // Usuario
            case "LOGIN":                   manejarLogin(); break;
            case "REGISTRAR_USUARIO":       manejarRegistroUsuario(); break;
            case "LISTAR_USUARIOS":         manejarListarUsuarios(); break;
            case "ELIMINAR_USUARIO":        manejarEliminarUsuario(); break;
            case "ACTUALIZAR_USUARIO":      manejarActualizarUsuario(); break;

            // Libros
            case "LISTAR_LIBROS":           manejarListarLibros(); break;
            case "BUSCAR_LIBROS":           manejarBuscarLibros(); break;
            case "REGISTRAR_LIBRO":         manejarRegistrarLibro(); break;
            case "CATEGORIAS_LIBROS":       manejarCategorias(); break;
            case "REPORTE_MAS_PRESTADOS":   manejarReporteLibros(); break;

            // Prestamos
            case "LISTAR_PRESTAMOS_ACTIVOS": manejarListarPrestamosActivos(); break;
            case "HISTORIAL_PRESTAMOS":      manejarHistorialUsuario(); break;
            case "REGISTRAR_PRESTAMO":       manejarRegistrarPrestamo(); break;
            case "REGISTRAR_DEVOLUCION":     manejarRegistrarDevolucion(); break;

            // Reservas
            case "LISTAR_RESERVAS_ACTIVAS":  manejarListarReservasActivas(); break;
            case "LISTAR_RESERVAS_USUARIO":  manejarListarReservasUsuario(); break;
            case "REGISTRAR_RESERVA":        manejarRegistrarReserva(); break;
            case "CANCELAR_RESERVA":         manejarCancelarReserva(); break;

            // Multas
            case "LISTAR_MULTAS_PENDIENTES": manejarListarMultasPendientes(); break;
            case "LISTAR_TODAS_MULTAS":      manejarListarTodasMultas(); break;
            case "PAGAR_MULTA":              manejarPagarMulta(); break;

            // Control
            case "SALIR":
                cerrarConexion();
                break;
            default:
                System.out.println("⚠️ Comando desconocido: " + comando);
                break;
        }


    }

    // Metodos privados de procesarPeticion

    // Usuarios
    private void manejarLogin() throws IOException, ClassNotFoundException {

        Usuario credenciales = (Usuario) in.readObject();

        Usuario resultado = usuarioDAO.validarLogin(credenciales.getEmail(), credenciales.getPassword());

        out.writeObject(resultado);
        out.flush();
    }

    private void manejarRegistroUsuario() throws IOException, ClassNotFoundException {

        Usuario nuevoUsuario = (Usuario) in.readObject();

        boolean resultado = usuarioDAO.registrarUsuario(nuevoUsuario);

        out.writeObject(resultado);
        out.flush();
    }

    private void manejarListarUsuarios() throws IOException {

        List<Usuario> usuarios = usuarioDAO.listarUsuarios();

        out.writeObject(usuarios);
        out.flush();

    }

    private void manejarEliminarUsuario() throws IOException, ClassNotFoundException{

        int id = (int) in.readObject();

        out.writeObject(usuarioDAO.eliminarUsuario(id));
        out.flush();

    }

    private void manejarActualizarUsuario() throws IOException, ClassNotFoundException {
        Usuario u = (Usuario) in.readObject();
        out.writeObject(usuarioDAO.actualizarUsuario(u));
        out.flush();
    }

    // Libros

    private void manejarListarLibros () throws IOException {

        List<Libro> libros = libroDAO.listarLibros();

        out.writeObject(libros);
        out.flush();
    }

    private void manejarBuscarLibros() throws IOException, ClassNotFoundException {

        String busqueda = (String) in.readObject();

        List<Libro> resultado = libroDAO.buscarLibros(busqueda);

        out.writeObject(resultado);
        out.flush();
    }

    private void manejarRegistrarLibro() throws IOException, ClassNotFoundException{

        Libro nl = (Libro) in.readObject();

        boolean resultado = libroDAO.registrarLibro(nl);

        out.writeObject(resultado);
        out.flush();

    }

    private void manejarCategorias() throws IOException{

        List<String> categorias = libroDAO.listarCategoriasDisponibles();

        out.writeObject(categorias);
        out.flush();

    }

    private void manejarReporteLibros() throws IOException{

        out.writeObject(libroDAO.obtenerReporteMasPrestados());
        out.flush();

    }

    // Prestamos

    private void manejarListarPrestamosActivos() throws IOException {

        List<Prestamo> resultado = prestamoDAO.listarPrestamosActivos();

        out.writeObject(resultado);
        out.flush();
    }

    private void manejarHistorialUsuario() throws IOException, ClassNotFoundException {
        int idUsuario = (int) in.readObject();
        out.writeObject(prestamoDAO.listarHistorialPorUsuario(idUsuario));
        out.flush();
    }

    private void manejarRegistrarPrestamo() throws IOException, ClassNotFoundException {
        // Leemos los parámetros en el orden que el cliente los envía
        int idUsuario = (int) in.readObject();
        int idLibro = (int) in.readObject();

        boolean resultado = prestamoDAO.registrarPrestamo(idUsuario, idLibro);
        out.writeObject(resultado);
        out.flush();
    }

    private void manejarRegistrarDevolucion() throws IOException, ClassNotFoundException {
        int idPrestamo = (int) in.readObject();
        int idLibro = (int) in.readObject();

        boolean resultado = prestamoDAO.registrarDevolucion(idPrestamo, idLibro);

        if (resultado) {
            multaDAO.procesarPosibleMulta(idPrestamo);
        }

        out.writeObject(resultado);
        out.flush();
    }

    // Reservas

    private void manejarListarReservasActivas() throws IOException {
        out.writeObject(reservaDAO.listarReservasActivas());
        out.flush();
    }

    private void manejarListarReservasUsuario() throws IOException, ClassNotFoundException {
        int idUsuario = (int) in.readObject();
        out.writeObject(reservaDAO.listarReservasPorUsuario(idUsuario));
        out.flush();
    }

    private void manejarRegistrarReserva() throws IOException, ClassNotFoundException {
        int idUsuario = (int) in.readObject();
        int idLibro = (int) in.readObject();
        out.writeObject(reservaDAO.registrarReserva(idUsuario, idLibro));
        out.flush();
    }

    private void manejarCancelarReserva() throws IOException, ClassNotFoundException {
        int idReserva = (int) in.readObject();
        out.writeObject(reservaDAO.cancelarReserva(idReserva));
        out.flush();
    }

    // Multas

    private void manejarListarMultasPendientes() throws IOException, ClassNotFoundException {
        int idUsuario = (int) in.readObject();
        out.writeObject(multaDAO.listarMultasPendientes(idUsuario));
        out.flush();
    }

    private void manejarListarTodasMultas() throws IOException {
        out.writeObject(multaDAO.listarTodasLasMultas());
        out.flush();
    }

    private void manejarPagarMulta() throws IOException, ClassNotFoundException {
        int idMulta = (int) in.readObject();
        out.writeObject(multaDAO.registrarPagoMulta(idMulta));
        out.flush();
    }

    //Utilidades

    private void cerrarConexion() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
