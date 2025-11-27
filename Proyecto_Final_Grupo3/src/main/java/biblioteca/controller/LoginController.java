package biblioteca.controller;

import biblioteca.dao.UsuarioDAO;
import biblioteca.model.Usuario;
import biblioteca.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {

    private final LoginView vista;
    private final UsuarioDAO modelo;

    public LoginController(LoginView vista, UsuarioDAO modelo) {
        this.vista = vista;
        this.modelo = modelo;

        this.vista.setIngresarListener(this); // este mismo
        this.vista.setRegristrarseListener(e -> abrirRegistro()); // hay que inicializar el RegistroController
    }

    public void iniciar(){

        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){

        String email = vista.getEmail();
        String password = vista.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            vista.mostrarError("Por favor, complete todos los campos.");
            return;
        }

        Usuario usuario = modelo.validarLogin(email, password);


        if (usuario != null) {

            vista.dispose(); // Cerramos la ventana de login
            redireccionarPorRol(usuario);
        } else {

            vista.mostrarError("Usuario o contraseña incorrectos.");
        }
    }

    private void redireccionarPorRol(Usuario usuario){

        String rol = usuario.getRol().toUpperCase();

        System.out.println(">> Acceso concedido a: " + usuario.getNombre() + " [" + rol + "]");

        if (rol.equals("BIBLIOTECARIO") || rol.equals("ADMINISTRADOR")) {

            AdminDashboardView dashboardView = new AdminDashboardView();
            new AdminDashboardController(dashboardView);

        } else if (rol.equals("ESTUDIANTE")) {

            new CatalogoLibrosEstudiantesView().setVisible(true);

        } else {

            vista.mostrarError("Error: Su rol no tiene permisos asignados.");
            vista.setVisible(true);
        }
    }

    private void abrirRegistro(){

        RegistroUsuarioView vistaRegistro = new RegistroUsuarioView();
        new RegistroController(vistaRegistro, modelo);
    }

}
