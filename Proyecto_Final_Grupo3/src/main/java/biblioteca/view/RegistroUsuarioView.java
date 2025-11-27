package biblioteca.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegistroUsuarioView extends JFrame {

    private JTextField txtNombre;
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnRegistrar;
    private JButton btnCancelar;

    public RegistroUsuarioView() {

        setTitle("Registro de Nuevo Usuario");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();
    }

    private void inicializarComponentes(){

        JPanel panelFondo = new JPanel(new GridBagLayout());
        panelFondo.setBackground(new Color (43,45,48));
        setContentPane(panelFondo);

        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBackground(new Color(60, 63, 65));
        panelForm.setBorder(new EmptyBorder(40, 60, 40, 60));

        // Crear Cuenta - titulo
        JLabel lblTitulo = new JLabel("Crear Cuenta");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24)); // fuente
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar el texto en el eje X

        // campo de nombre
        JLabel lblNombre = new JLabel("Nombre Completo");
        estilizarEtiqueta(lblNombre); // funciones privadas
        txtNombre = new JTextField(20);
        estilizarCampo(txtNombre); // funciones privadas

        // campo de correo
        JLabel lblEmail = new JLabel("Correo Electronico");
        estilizarEtiqueta(lblEmail); // funciones privadas
        txtEmail = new JTextField(20);
        estilizarCampo(txtEmail); // funciones privadas

        // Campo Contraseña
        JLabel lblPass = new JLabel("Contraseña");
        estilizarEtiqueta(lblPass);
        txtPassword = new JPasswordField(20);
        estilizarCampo(txtPassword);

        // botones
        btnRegistrar = new JButton("REGISTRARSE");

        btnRegistrar.setMaximumSize(new Dimension(300, 45));
        btnRegistrar.setBackground(new Color(46, 204, 113));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.getRootPane().setDefaultButton(btnRegistrar);

        btnCancelar = new JButton("Cancelar / Volver");
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setForeground(new Color(189, 195, 199));
        btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // adds
        panelForm.add(lblTitulo);
        panelForm.add(Box.createVerticalStrut(30));

        panelForm.add(lblNombre);
        panelForm.add(Box.createVerticalStrut(5));
        panelForm.add(txtNombre);
        panelForm.add(Box.createVerticalStrut(15));

        panelForm.add(lblEmail);
        panelForm.add(Box.createVerticalStrut(5));
        panelForm.add(txtEmail);
        panelForm.add(Box.createVerticalStrut(15));

        panelForm.add(lblPass);
        panelForm.add(Box.createVerticalStrut(5));
        panelForm.add(txtPassword);
        panelForm.add(Box.createVerticalStrut(30));

        panelForm.add(btnRegistrar);
        panelForm.add(Box.createVerticalStrut(10));
        panelForm.add(btnCancelar);

        panelFondo.add(panelForm);
    }

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

    // Getters

    public String getNombre() {
        return txtNombre.getText();
    }

    public String getEmail() {
        return txtEmail.getText();
    }
    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    // Listeners
    public void setRegistrarListener(ActionListener l) {
        btnRegistrar.addActionListener(l);
    }

    public void setCancelarListener(ActionListener l) {
        btnCancelar.addActionListener(l);
    }
}
