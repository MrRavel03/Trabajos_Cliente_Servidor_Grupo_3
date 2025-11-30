package biblioteca.controller;

import biblioteca.cliente.ClienteTCP;
import biblioteca.model.Usuario;
import biblioteca.view.LoginView;
import biblioteca.view.RegistroUsuarioView;

import javax.swing.*;

public class RegistroController {

    private RegistroUsuarioView vista;

    public RegistroController(RegistroUsuarioView vista){
        this.vista = vista;

        this.vista.setRegistrarListener(e -> registrarUsuario());
        this.vista.setCancelarListener(e -> vista.dispose());

        this.vista.setVisible(true);
    }

    private void registrarUsuario(){

        String nombre = vista.getNombre();
        String email = vista.getEmail();
        String pass = vista.getPassword();

        if (nombre.isEmpty() || email.isEmpty() || pass.isEmpty()){

            JOptionPane.showMessageDialog(
                    vista,
                    "Por favor, complete todos los campos",
                    "Campos Incompletos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Usuario u = new Usuario();

        u.setNombre(nombre);
        u.setEmail(email);
        u.setPassword(pass);

        u.setRol("ESTUDIANTE");
        u.setEstado("ACTIVO");

        boolean resultado = ClienteTCP.getInstance().registrarUsuario(u);

        if (resultado) {

            JOptionPane.showMessageDialog(
                    vista,
                    "Registro exitoso\nAhora puedes iniciar sesion con tu cuenta.",
                    "Bienvenido",
                    JOptionPane.INFORMATION_MESSAGE
            );
            vista.dispose();

            //abrimos de nuevo
            new LoginController(new LoginView()).iniciar();

        } else {

            JOptionPane.showMessageDialog(
                    vista,
                    "No se pudo registrar el usuario. Probablemente el correo '" + email + "' ya esté en uso.",
                    "Error de Registro",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }
}
