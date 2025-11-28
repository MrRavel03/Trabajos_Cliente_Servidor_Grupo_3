package biblioteca.controller;

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

        //todo



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
        // TODO: En el futuro, conectar con ReservasController
        ReservasEstudianteView vistaReservas = new ReservasEstudianteView();
        vistaReservas.setVisible(true);
    }

    private void abrirPrestamos() {
        // TODO: En el futuro, conectar con PrestamosController
        PrestamosEstudianteView vistaPrestamos = new PrestamosEstudianteView();
        vistaPrestamos.setVisible(true);
    }

    private void abrirMultas() {
        // TODO: En el futuro, conectar con MultasController
        MultasEstudianteView vistaMultas = new MultasEstudianteView();
        vistaMultas.setVisible(true);
    }

    private void cerrarSesion(){

        vista.dispose();
        LoginView vistaLogin = new LoginView();
        UsuarioDAO modeloUsuario = new UsuarioDAO();
        LoginController loginCtrl = new LoginController(vistaLogin, modeloUsuario);
        loginCtrl.iniciar();
    }
}
