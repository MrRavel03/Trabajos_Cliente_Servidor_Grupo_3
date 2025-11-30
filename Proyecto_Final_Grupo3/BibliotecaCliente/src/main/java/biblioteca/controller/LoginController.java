package biblioteca.controller;

import biblioteca.cliente.ClienteTCP;
import biblioteca.controller.admin.AdminDashboardController;
import biblioteca.controller.estudiantes.MenuEstudianteController;
import biblioteca.model.Usuario;
import biblioteca.view.*;
import biblioteca.view.admin.AdminDashboardView;
import biblioteca.view.estudiantes.MenuPrincipalEstudianteView;

public class LoginController {

    private final LoginView vista;

    public LoginController(LoginView vista) {
        this.vista = vista;

        this.vista.setIngresarListener(e -> validarUsuario());
        this.vista.setRegristrarseListener(e -> abrirRegistro());
    }

    public void iniciar(){

        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    public void validarUsuario(){

        String email = vista.getEmail();
        String password = vista.getPassword();

        if (email.isEmpty() || password.isEmpty()) {
            vista.mostrarError("Por favor, complete todos los campos.");
            return;
        }

        // Utilizamos ClienteTCP el cual es nuestra puerta
        Usuario usuario = ClienteTCP.getInstance().validarLogin(email, password);

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

            MenuPrincipalEstudianteView menuPrincipalVista = new MenuPrincipalEstudianteView();
            new MenuEstudianteController(menuPrincipalVista, usuario);

        } else {

            vista.mostrarError("Error: Su rol no tiene permisos asignados.");
            vista.setVisible(true);
        }
    }

    private void abrirRegistro(){
        vista.dispose();

        RegistroUsuarioView vistaRegistro = new RegistroUsuarioView();
        new RegistroController(vistaRegistro);
    }

}
