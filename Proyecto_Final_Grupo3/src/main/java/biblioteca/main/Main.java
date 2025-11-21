package biblioteca.main;

import biblioteca.dao.LibroDAO;
import biblioteca.dao.PrestamoDAO;
import biblioteca.dao.ReservaDAO;
import biblioteca.dao.UsuarioDAO;
import biblioteca.model.Libro;
import biblioteca.model.Prestamo;
import biblioteca.model.Usuario;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   PRUEBA DE ACEPTACIÓN - BIBLIOTECA DIGITAL MPV");
        System.out.println("=================================================\n");

        // --- INSTANCIAS DE DAOS ---
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        LibroDAO libroDAO = new LibroDAO();
        PrestamoDAO prestamoDAO = new PrestamoDAO();
        ReservaDAO reservaDAO = new ReservaDAO();

        // VARIABLES PARA LA PRUEBA
        // Usamos un email aleatorio para no chocar con el UNIQUE constraint si corres
        // esto 2 veces
        String emailTest = "nuevo" + System.currentTimeMillis() + "@estudiante.com";
        int idUsuarioNuevo = -1;
        int idLibroNuevo = -1;

        // =================================================================
        // REQ 1 y 2: REGISTRO DE USUARIO Y LIBRO
        // =================================================================
        System.out.println("[PASO 1] Registrando nuevos datos...");

        // A) Crear Usuario
        Usuario u = new Usuario();
        u.setNombre("Estudiante Prueba");
        u.setEmail(emailTest);
        u.setPassword("1234");
        u.setRol("ESTUDIANTE");

        if (usuarioDAO.registrarUsuario(u)) {
            System.out.println("✅ Usuario registrado: " + u.getNombre());
            // Truco: Recuperamos el usuario logueando para obtener su ID generado
            Usuario uRecuperado = usuarioDAO.validarLogin(emailTest, "1234");
            idUsuarioNuevo = uRecuperado.getId();
        } else {
            System.err.println("❌ Error al registrar usuario.");
            return;
        }

        // B) Crear Libro
        Libro l = new Libro();
        l.setTitulo("Estructuras de Datos Avanzadas");
        l.setAutor("Robert Lafore");
        l.setCategoria("Tecnologia");

        if (libroDAO.registrarLibro(l)) {
            System.out.println("✅ Libro registrado: " + l.getTitulo());
            // Buscamos el libro para obtener su ID
            List<Libro> busqueda = libroDAO.buscarLibros("Estructuras");
            if (!busqueda.isEmpty())
                idLibroNuevo = busqueda.get(0).getId();
        } else {
            System.err.println("❌ Error al registrar libro.");
            return;
        }

        // =================================================================
        // REQ 2: LOGIN
        // =================================================================
        System.out.println("\n[PASO 2] Probando Login con el nuevo usuario...");
        Usuario login = usuarioDAO.validarLogin(emailTest, "1234");
        if (login != null) {
            System.out.println("✅ Login Exitoso. Rol: " + login.getRol());
        } else {
            System.err.println("❌ Login fallido.");
        }

        // =================================================================
        // REQ 5: PRÉSTAMO (Prestar el libro nuevo al usuario nuevo)
        // =================================================================
        System.out.println("\n[PASO 3] Realizando Préstamo...");
        boolean prestamoOk = prestamoDAO.registrarPrestamo(idUsuarioNuevo, idLibroNuevo);
        if (prestamoOk) {
            System.out.println("✅ Préstamo registrado correctamente.");
        } else {
            System.err.println("❌ Falló el préstamo.");
        }

        // =================================================================
        // REQ 12: VALIDACIÓN (Intentar reservar lo que ya está prestado)
        // =================================================================
        System.out.println("\n[PASO 4] Intentando reservar libro ocupado (Debe fallar)...");
        boolean reservaOk = reservaDAO.registrarReserva(idUsuarioNuevo, idLibroNuevo);
        if (!reservaOk) {
            System.out.println("✅ CORRECTO: El sistema bloqueó la reserva inválida.");
        } else {
            System.err.println("❌ ERROR: El sistema permitió reservar un libro ocupado.");
        }

        // =================================================================
        // REQ 11: HISTORIAL DE PRÉSTAMOS
        // =================================================================
        System.out.println("\n[PASO 5] Consultando Historial del Usuario...");
        List<Prestamo> historial = prestamoDAO.listarHistorialPorUsuario(idUsuarioNuevo);
        boolean encontradoEnHistorial = false;
        int idPrestamoActivo = -1;

        for (Prestamo p : historial) {
            System.out.println("   -> Libro: " + p.getTituloLibro() + " | Fecha: " + p.getFechaSalida() + " | Estado: "
                    + p.getEstado());
            if (p.getIdLibro() == idLibroNuevo) {
                encontradoEnHistorial = true;
                idPrestamoActivo = p.getId();
            }
        }

        if (encontradoEnHistorial)
            System.out.println("✅ El préstamo aparece en el historial.");
        else
            System.err.println("❌ El préstamo NO aparece en el historial.");

        // =================================================================
        // DEVOLUCIÓN
        // =================================================================
        System.out.println("\n[PASO 6] Devolviendo el libro...");
        if (idPrestamoActivo != -1) {
            if (prestamoDAO.registrarDevolucion(idPrestamoActivo, idLibroNuevo)) {
                System.out.println("✅ Libro devuelto con éxito.");
            } else {
                System.err.println("❌ Falló la devolución.");
            }
        }

        // =================================================================
        // REQ 4: RESERVA EXITOSA (Ahora que está libre)
        // =================================================================
        System.out.println("\n[PASO 7] Intentando reservar nuevamente (Debe funcionar)...");
        if (reservaDAO.registrarReserva(idUsuarioNuevo, idLibroNuevo)) {
            System.out.println("✅ CORRECTO: Reserva realizada.");
        } else {
            System.err.println("❌ Error al reservar libro disponible.");
        }

        System.out.println("\n=================================================");
        System.out.println("   FIN DE PRUEBAS - BACKEND LISTO");
        System.out.println("=================================================");
    }
}