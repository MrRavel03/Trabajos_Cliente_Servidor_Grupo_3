package biblioteca.controller.admin;

import biblioteca.controller.LoginController;
import biblioteca.dao.UsuarioDAO;
import biblioteca.view.admin.*;
import biblioteca.view.LoginView;

public class AdminDashboardController {

    private final AdminDashboardView vista;

    public AdminDashboardController(AdminDashboardView vista){

        this.vista = vista;

        this.vista.setPrestamosListener(e -> abrirGestionPrestamos());
        this.vista.setCerrarSesionListener(e -> cerrarSesion());
        this.vista.setLibrosListener(e -> abrirGestionLibros());
        this.vista.setUsuariosListener(e -> abrirGestionUsuarios());
        this.vista.setReservasListener(e -> abrirGestionReservas());

        this.vista.setVisible(true);
    }

    private void abrirGestionPrestamos(){

        GestionPrestamosView vistaPrestamos = new GestionPrestamosView();
        new GestionPrestamosController(vistaPrestamos);
    }

    private void abrirGestionReservas(){

        GestionReservasView vistaReservas = new GestionReservasView();
        new GestionReservasController(vistaReservas);
    }

    private void cerrarSesion(){

        vista.dispose();
        new LoginController(new LoginView(), new UsuarioDAO()).iniciar();
    }

    private void abrirGestionLibros(){

        GestionLibrosView vistaLibros = new GestionLibrosView();
        new GestionLibrosController(vistaLibros);
    }

    private void abrirGestionUsuarios() {
        GestionUsuariosView vistaUsuarios = new GestionUsuariosView();
        new GestionUsuariosController(vistaUsuarios);
    }
}
