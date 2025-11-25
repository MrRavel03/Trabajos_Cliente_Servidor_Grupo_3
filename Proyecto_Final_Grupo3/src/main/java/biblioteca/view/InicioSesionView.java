package biblioteca.view;

import javax.swing.*;
import java.awt.*;

public class InicioSesionView extends JFrame{

    private JPanel ventana;
    private JLabel titulo;
    private JLabel usuario;
    private JLabel contrasena;
    private JTextField usuarioIngresado;
    private JPasswordField contrasenaIngresada;
    private JButton botonIngresar;
    private JLabel mensajeError;

    public InicioSesionView(){

        setTitle("Inicio de Sesion - Biblioteca");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(720,480);
        setLocationRelativeTo(null);
        setVisible(true);



        ventana = new JPanel();
        ventana.setLayout(null); // Layout manual
        ventana.setBackground(new Color(43, 45, 48));
        add(ventana);

        //Texto del Titulo
        titulo = new JLabel("¡Bienvenidos a la Biblioteca XYZ!");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setBounds(120, 20, 729, 40);
        ventana.add(titulo);

        //Texto usuario
        usuario = new JLabel("Usuario:");
        usuario.setForeground(Color.WHITE);
        usuario.setBounds(50, 100, 120, 25);
        ventana.add(usuario);

        //Usuario Digitado
        usuarioIngresado = new JTextField();
        usuarioIngresado.setBounds(180, 100, 280, 28);
        ventana.add(usuarioIngresado);

        //Texto Contrasena
        contrasena = new JLabel("Contraseña:");
        contrasena.setForeground(Color.WHITE);
        contrasena.setBounds(50, 160, 120, 25);
        ventana.add(contrasena);

        //Contrasena Ingresada por Usuario
        contrasenaIngresada = new JPasswordField();
        contrasenaIngresada.setBounds(180, 160, 280, 28);
        ventana.add(contrasenaIngresada);

        // Botón ingresar
        botonIngresar = new JButton("Ingresar");
        botonIngresar.setBounds(180, 240, 260, 35);
        botonIngresar.setFocusPainted(false);
        botonIngresar.setBackground(new Color(52, 152, 219));
        botonIngresar.setForeground(Color.WHITE);
        botonIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ventana.add(botonIngresar);

        //Mensaje en caso de error
        mensajeError = new JLabel("");
        mensajeError.setForeground(Color.RED);
        mensajeError.setBounds(40, 220, 300, 25);
        ventana.add(mensajeError);
    }

    public void mostrarErrorInicioSesion(String mensaje){
        mensajeError.setText(mensaje);
    }

    public JLabel getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(JLabel mensajeError) {
        this.mensajeError = mensajeError;
    }

    public JButton getBotonIngresar() {
        return botonIngresar;
    }

    public void setBotonIngresar(JButton botonIngresar) {
        this.botonIngresar = botonIngresar;
    }

    public JPasswordField getContrasenaIngresada() {
        return contrasenaIngresada;
    }

    public void setContrasenaIngresada(JPasswordField contrasenaIngresada) {
        this.contrasenaIngresada = contrasenaIngresada;
    }

    public JTextField getUsuarioIngresado() {
        return usuarioIngresado;
    }

    public void setUsuarioIngresado(JTextField usuarioIngresado) {
        this.usuarioIngresado = usuarioIngresado;
    }

    public JLabel getContrasena() {
        return contrasena;
    }

    public void setContrasena(JLabel contrasena) {
        this.contrasena = contrasena;
    }

    public JLabel getUsuario() {
        return usuario;
    }

    public void setUsuario(JLabel usuario) {
        this.usuario = usuario;
    }

    public JLabel getTitulo() {
        return titulo;
    }

    public void setTitulo(JLabel titulo) {
        this.titulo = titulo;
    }

    public JPanel getVentana() {
        return ventana;
    }

    public void setVentana(JPanel ventana) {
        this.ventana = ventana;
    }
}
