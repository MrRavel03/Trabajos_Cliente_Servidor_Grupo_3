package biblioteca.main;

import biblioteca.config.ConexionDB;
import biblioteca.dao.LibroDAO;
import biblioteca.dao.PrestamoDAO;
import biblioteca.dao.ReservaDAO;
import biblioteca.dao.UsuarioDAO;
import biblioteca.model.Libro;
import biblioteca.model.Prestamo;
import biblioteca.model.Reserva;
import biblioteca.model.Usuario;

import java.sql.Connection;
import java.util.List;

public class Main {
    static void main() {
        System.out.println("=================================================");
        System.out.println("   PRUEBA INTEGRAL DEL SISTEMA BIBLIOTECARIO");
        System.out.println("=================================================\n");

        // ----------------------------------------------------------------
        // PASO 1: AUTENTICACIÓN
        // ----------------------------------------------------------------
        System.out.println("[PASO 1] Probando Inicio de Sesión...");
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioLogueado = usuarioDAO.validarLogin("gosorio@estudiante.com", "1234");

        if (usuarioLogueado != null) {
            System.out.println("✅ Login Exitoso. Usuario: " + usuarioLogueado.getNombre());
        } else {
            System.err.println("❌ Login Fallido. Revisa tu base de datos.");
            return; // Si no entra, no seguimos.
        }

        // ----------------------------------------------------------------
        // PASO 2: CATÁLOGO Y BÚSQUEDA
        // ----------------------------------------------------------------
        System.out.println("\n[PASO 2] Consultando libros disponibles...");
        LibroDAO libroDAO = new LibroDAO();
        List<Libro> libros = libroDAO.listarLibros();

        int idLibroPrueba = 2; // Usaremos "Cien años de soledad" para las pruebas
        String tituloLibroPrueba = "";

        for (Libro l : libros) {
            System.out.println("   -> ID: " + l.getId() + " | Título: " + l.getTitulo() + " | Disponible: " + l.isDisponible());
            if (l.getId() == idLibroPrueba) tituloLibroPrueba = l.getTitulo();
        }

        // ----------------------------------------------------------------
        // PASO 3: REALIZAR PRÉSTAMO
        // ----------------------------------------------------------------
        System.out.println("\n[PASO 3] Prestando el libro: '" + tituloLibroPrueba + "'...");
        PrestamoDAO prestamoDAO = new PrestamoDAO();
        int idUsuario = 2; // Gabriel Osorio

        boolean prestamoExitoso = prestamoDAO.registrarPrestamo(idUsuario, idLibroPrueba);

        if (prestamoExitoso) {
            System.out.println("✅ Préstamo registrado. El libro ahora debería estar NO DISPONIBLE.");
        } else {
            System.out.println("⚠️ El préstamo falló (¿Ya estaba prestado?).");
        }

        // ----------------------------------------------------------------
        // PASO 4: PRUEBA DE RESTRICCIÓN DE RESERVA (REQUERIMIENTO 12)
        // Intentamos reservar el libro que ACABAMOS de prestar. Debería fallar.
        // ----------------------------------------------------------------
        System.out.println("\n[PASO 4] Intentando RESERVAR el libro ocupado (Debe fallar)...");
        ReservaDAO reservaDAO = new ReservaDAO();
        boolean reservaFallidaEsperada = reservaDAO.registrarReserva(idUsuario, idLibroPrueba);

        if (!reservaFallidaEsperada) {
            System.out.println("✅ CORRECTO: El sistema bloqueó la reserva porque el libro no está disponible.");
        } else {
            System.err.println("❌ ERROR GRAVE: El sistema permitió reservar un libro prestado.");
        }

        // ----------------------------------------------------------------
        // PASO 5: DEVOLUCIÓN DEL LIBRO
        // ----------------------------------------------------------------
        System.out.println("\n[PASO 5] Devolviendo el libro...");

        // Primero buscamos el ID del préstamo activo
        List<Prestamo> activos = prestamoDAO.listarPrestamosActivos();
        int idPrestamoActivo = -1;

        for(Prestamo p : activos) {
            if(p.getIdLibro() == idLibroPrueba && p.getIdUsuario() == idUsuario) {
                idPrestamoActivo = p.getId();
                break;
            }
        }

        if (idPrestamoActivo != -1) {
            boolean devolucionExitosa = prestamoDAO.registrarDevolucion(idPrestamoActivo, idLibroPrueba);
            if (devolucionExitosa) {
                System.out.println("✅ Devolución registrada. El libro ahora está DISPONIBLE.");
            }
        } else {
            System.out.println("⚠️ No se encontró el préstamo activo para devolver.");
        }

        // ----------------------------------------------------------------
        // PASO 6: RESERVA EXITOSA
        // Ahora que el libro fue devuelto, la reserva SÍ debería funcionar.
        // ----------------------------------------------------------------
        System.out.println("\n[PASO 6] Intentando RESERVAR nuevamente (Debe funcionar)...");
        boolean reservaExitosa = reservaDAO.registrarReserva(idUsuario, idLibroPrueba);

        if (reservaExitosa) {
            System.out.println("✅ CORRECTO: Reserva realizada con éxito.");
        } else {
            System.err.println("❌ ERROR: Falló la reserva y el libro debería estar libre.");
        }

        // ----------------------------------------------------------------
        // RESUMEN FINAL
        // ----------------------------------------------------------------
        System.out.println("\n=================================================");
        System.out.println("   RESUMEN DE RESERVAS ACTIVAS");
        System.out.println("=================================================");
        List<Reserva> reservas = reservaDAO.listarReservasActivas();
        for(Reserva r : reservas) {
            System.out.println("Reserva ID: " + r.getId() + " | Libro: " + r.getTituloLibro() + " | Usuario: " + r.getNombreUsuario());
        }
    }
}
