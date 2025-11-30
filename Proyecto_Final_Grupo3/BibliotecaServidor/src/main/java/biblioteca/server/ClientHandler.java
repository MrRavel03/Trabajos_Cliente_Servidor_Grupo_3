package biblioteca.server;

import biblioteca.dao.LibroDAO;
import biblioteca.dao.UsuarioDAO;
import biblioteca.model.Libro;
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

    public ClientHandler(Socket socket){

        this.socket = socket;

        this.usuarioDAO = new UsuarioDAO();
        this.libroDAO = new LibroDAO();
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

            // Usuarios y accesos
            case "LOGIN":
                manejarLogin();
                break;
            case "REGISTRAR_USUARIO":
                manejarRegistroUsuario();
                break;
            case "LISTAR_USUARIOS":
                manejarListarUsuarios();
                break;

            // Libros
            case "LISTAR_LIBROS":
                manejarListarLibros();
                break;
            case "BUSCAR_LIBROS":
                manejarBuscarLibros();
                break;
            case "REGISTRAR_LIBRO":
                manejarRegistrarLibro();
                break;
            case "CATEGORIAS_LIBROS":
                manejarCategorias();
                break;

            // Control

            case "SALIR":
                cerrarConexion();
                break;

            default:
                System.out.println("Comando desconocido: " + comando);
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
