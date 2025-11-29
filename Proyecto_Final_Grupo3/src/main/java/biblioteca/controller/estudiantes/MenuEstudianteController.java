package biblioteca.controller.estudiantes;

import biblioteca.controller.LoginController;
import biblioteca.dao.UsuarioDAO;
import biblioteca.model.Usuario;
import biblioteca.view.LoginView;
import biblioteca.view.estudiantes.*;

import javax.swing.*;

public class MenuEstudianteController {

    private final MenuPrincipalEstudianteView vista;
    private final Usuario usuario;

    public MenuEstudianteController(MenuPrincipalEstudianteView vista, Usuario usuario){

        this.vista = vista;
        this.usuario = usuario;

        this.vista.setNombreUsuario(usuario.getNombre());

        // Botones de navegacion

        this.vista.setIrCatalogoListener(e -> abrirCatalogo());

        this.vista.setIrPrestamosListener(e -> abrirPrestamos());

        this.vista.setIrReservasListener(e -> abrirReservas());

        this.vista.setIrMultasListener(e -> abrirMultas());

        this.vista.setCerrarSesionListener(e -> cerrarSesion());

        // Notificacion

        this.vista.setNotificacionesListener(e ->
                        JOptionPane.showMessageDialog(
                                vista,
                                "No tienes nuevas notificaciones",
                                "Notificaciones",
                                JOptionPane.INFORMATION_MESSAGE
                        )
                );

        this.vista.setVisible(true);
    }

    public void abrirCatalogo(){

        CatalogoLibrosEstudiantesView vistaCatalogo = new CatalogoLibrosEstudiantesView();
        new CatalogoController(vistaCatalogo, usuario.getId());
    }

    private void abrirReservas() {

        ReservasEstudianteView vistaReservas = new ReservasEstudianteView();
        new ReservasController(vistaReservas, usuario.getId());
    }

    private void abrirPrestamos() {

        PrestamosEstudianteView vistaPrestamos = new PrestamosEstudianteView();
        new MisPrestamosController(vistaPrestamos, usuario.getId());
    }

    private void abrirMultas() {

        MultasEstudianteView vistaMultas = new MultasEstudianteView();
        new MisMultasController(vistaMultas, usuario.getId());
    }

    private void cerrarSesion(){

        vista.dispose();
        LoginView vistaLogin = new LoginView();
        UsuarioDAO modeloUsuario = new UsuarioDAO();
        LoginController loginCtrl = new LoginController(vistaLogin, modeloUsuario);
        loginCtrl.iniciar();
    }
}
