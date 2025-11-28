package biblioteca.controller;

import biblioteca.dao.UsuarioDAO;
import biblioteca.view.AdminDashboardView;
import biblioteca.view.GestionLibrosView;
import biblioteca.view.GestionPrestamosView;
import biblioteca.view.LoginView;

public class AdminDashboardController {

    private final AdminDashboardView vista;

    public AdminDashboardController(AdminDashboardView vista){

        this.vista = vista;

        this.vista.setPrestamosListener(e -> abrirGestionPrestamos());
        this.vista.setCerrarSesionListener(e -> cerrarSesion());
        this.vista.setLibrosListener(e -> abrirGestionLibros());

        // TODO
        this.vista.setReservasListener(e -> System.out.println("Abriendo Reservas..."));
        this.vista.setUsuariosListener(e -> System.out.println("Abriendo Usuarios..."));

        this.vista.setVisible(true);
    }

    private void abrirGestionPrestamos(){

        GestionPrestamosView vistaPrestamos = new GestionPrestamosView();
        new GestionPrestamosController(vistaPrestamos);
    }

    private void cerrarSesion(){

        vista.dispose();
        new LoginController(new LoginView(), new UsuarioDAO()).iniciar();
    }

    private void abrirGestionLibros(){

        GestionLibrosView vistaLibros = new GestionLibrosView();
        new GestionLibrosController(vistaLibros);
    }
}
