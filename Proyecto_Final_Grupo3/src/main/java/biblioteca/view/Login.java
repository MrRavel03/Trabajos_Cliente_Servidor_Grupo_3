package biblioteca.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnCancelar;

    public Login() {
        setTitle("Sistema Biblioteca - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Panel principal con bordes
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titulo
        JLabel titulo = new JLabel("ACCESO AL SISTEMA", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Panel central con formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(3, 2, 10, 10));

        // Campos del formulario
        panelFormulario.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panelFormulario.add(txtUsuario);

        panelFormulario.add(new JLabel("Contrasena:"));
        txtPassword = new JPasswordField();
        panelFormulario.add(txtPassword);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnLogin = new JButton("Iniciar Sesion");
        btnCancelar = new JButton("Cancelar");

        // Action listeners
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String password = new String(txtPassword.getPassword());

                // Validacion basica de demo
                if (usuario.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(Login.this,
                            "Por favor complete todos los campos");
                } else {
                    // Simulacion de login exitoso
                    JOptionPane.showMessageDialog(Login.this,
                            "Login exitoso! Redirigiendo al dashboard...");
                    new AdminDashboard().setVisible(true);
                    dispose();
                }
            }
        });

        btnCancelar.addActionListener(e -> System.exit(0));

        panelBotones.add(btnLogin);
        panelBotones.add(btnCancelar);

        // Agregar componentes al panel principal
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    public static void main(String[] args) {
        // Punto de entrada alternativo para pruebas
        SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}