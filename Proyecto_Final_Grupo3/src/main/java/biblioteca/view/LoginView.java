package biblioteca.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame{

    // solo los atributos necesarios y los que pueden cambiar o ser llamados
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnIngresar;
    private JLabel lblMensajeError;

    public LoginView() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana(){
        setTitle("Biblioteca - Acceso");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,600);
        setMinimumSize(new Dimension(400, 500));
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes(){

        // Panel del fondo, Gridbaglayout para centrar automaticamente los componentes
        JPanel panelFondo = new JPanel(new GridBagLayout());
        panelFondo.setBackground(new Color(43, 45, 48));
        setContentPane(panelFondo);

        // Hacemos una tarjeta para el panel del login
        JPanel panelLogin = new JPanel();
        // se apilan los elementos verticalmente uno de debajo del otro
        panelLogin.setLayout (new BoxLayout(panelLogin, BoxLayout.Y_AXIS));
        panelLogin.setBackground(new Color(60, 63, 65));
        panelLogin.setBorder(new EmptyBorder(50, 60, 50, 60)); // margen imaginario

        // Biblioteca Digital - titulo
        JLabel lblTitulo = new JLabel("Biblioteca Digital");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28)); // fuente
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el texto en el eje X

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Acceso Exclusivo");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(170, 170, 170));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // campo de usuario
        JLabel lblUser = new JLabel("Correo Institucional");
        estilizarEtiqueta(lblUser); // funciones privadas
        txtEmail = new JTextField(15);
        estilizarCampo(txtEmail); // funciones privadas

        // Campo Contraseña
        JLabel lblPass = new JLabel("Contraseña");
        estilizarEtiqueta(lblPass);
        txtPassword = new JPasswordField(15);
        estilizarCampo(txtPassword);

        // Mensaje de Error
        lblMensajeError = new JLabel(" ");
        lblMensajeError.setForeground(new Color(231, 76, 60));
        lblMensajeError.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMensajeError.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón Ingresar
        btnIngresar = new JButton("INGRESAR AL SISTEMA");
        estilizarBotonPrincipal(btnIngresar);

        // para que al dar enter funcione como el boton de ingresar
        this.getRootPane().setDefaultButton(btnIngresar);

        // --- Agregar al panel ---
        panelLogin.add(lblTitulo);
        panelLogin.add(Box.createVerticalStrut(10));
        panelLogin.add(lblSubtitulo);
        panelLogin.add(Box.createVerticalStrut(40));

        panelLogin.add(lblUser);
        panelLogin.add(Box.createVerticalStrut(5));
        panelLogin.add(txtEmail);
        panelLogin.add(Box.createVerticalStrut(20));

        panelLogin.add(lblPass);
        panelLogin.add(Box.createVerticalStrut(5));
        panelLogin.add(txtPassword);
        panelLogin.add(Box.createVerticalStrut(25));

        panelLogin.add(lblMensajeError);
        panelLogin.add(Box.createVerticalStrut(15));
        panelLogin.add(btnIngresar);

        panelFondo.add(panelLogin);
    }

    // Funciones de estilo

    private void estilizarEtiqueta(JLabel lbl){

        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font ("Segoe UI", Font.BOLD, 12));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void estilizarCampo(JComponent campo) {
        campo.setMaximumSize(new Dimension(320, 40));
        campo.setPreferredSize(new Dimension(320, 40));
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void estilizarBotonPrincipal(JButton btn) {
        btn.setMaximumSize(new Dimension(320, 45));
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Sets y gets para el controller

    public String getEmail() {
        return txtEmail.getText().trim();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public void mostrarError(String mensaje) {
        lblMensajeError.setText(mensaje);
    }

    public void setIngresarListener(ActionListener listener) {
        btnIngresar.addActionListener(listener);
    }
}
