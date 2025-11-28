package biblioteca.view.admin;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminDashboardView extends JFrame {

    private JButton btnReservas;
    private JButton btnPrestamos;
    private JButton btnUsuarios;
    private JButton btnLibros;
    private JButton btnMultas;
    private JButton btnCerrarSesion;

    public AdminDashboardView() {

        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana(){
        setTitle("Panel Administrativo - Biblioteca");
        setSize(900, 600);
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        // Panel principal con grid para botones
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(new Color(245, 247, 250));

        // --- Encabezado Azul ---
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(52, 152, 219));
        panelHeader.setPreferredSize(new Dimension(800, 80));
        panelHeader.setLayout(new GridBagLayout());

        // Encabezado/ titulo
        JLabel lblTitulo = new JLabel("Panel de Control Bibliotecario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        panelHeader.add(lblTitulo);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(2, 3, 20, 20)); // 2 filas, 3 columnas
        panelBotones.setBackground(new Color(245, 247, 250));
        panelBotones.setBorder(new EmptyBorder(20, 40, 40, 40));

        // Creacion de botones para cada modulo
        btnPrestamos = crearBoton("Gestión Préstamos"); // Verde
        btnReservas = crearBoton("Gestión Reservas");   // Amarillo
        btnLibros = crearBoton("Gestión Libros");       // Azul
        btnUsuarios = crearBoton("Gestión Usuarios");   // Morado
        btnMultas = crearBoton("Ver Multas");           // Naranja
        btnCerrarSesion = crearBoton("Cerrar Sesión");

        // Agregar botones al panel
        panelBotones.add(btnPrestamos);
        panelBotones.add(btnReservas);
        panelBotones.add(btnLibros);
        panelBotones.add(btnUsuarios);
        panelBotones.add(btnMultas);
        panelBotones.add(btnCerrarSesion);

        panelPrincipal.add(panelHeader, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);
        setContentPane(panelPrincipal);
    }

    private JButton crearBoton(String texto){

        JButton btn = new JButton(texto);

        btn.setFont(new Font("Segeo UI", Font.BOLD, 18));
        btn.setBackground(new Color(205, 205, 205));
        btn.setForeground(new Color(85, 85, 85));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // listeners
    public void setPrestamosListener(ActionListener l) {
        btnPrestamos.addActionListener(l);
    }

    public void setReservasListener(ActionListener l) {
        btnReservas.addActionListener(l);
    }

    public void setLibrosListener(ActionListener l) {
        btnLibros.addActionListener(l);
    }

    public void setUsuariosListener(ActionListener l) {
        btnUsuarios.addActionListener(l);
    }

    public void setMultasListener(ActionListener l) {
        btnMultas.addActionListener(l);
    }

    public void setCerrarSesionListener(ActionListener l) {
        btnCerrarSesion.addActionListener(l);
    }

}