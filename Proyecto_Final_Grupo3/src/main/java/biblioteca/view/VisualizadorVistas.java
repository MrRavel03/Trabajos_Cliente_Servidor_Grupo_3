package biblioteca.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisualizadorVistas extends JFrame {

    public VisualizadorVistas() {
        setTitle("VISUALIZADOR - Vistas Biblioteca");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        crearInterfaz();
    }

    private void crearInterfaz() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));

        JLabel titulo = new JLabel("VISUALIZAR VISTAS BIBLIOTECA", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titulo);

        // Botones para abrir cada vista
        JButton btnDashboard = new JButton("1. Admin Dashboard");
        JButton btnReservas = new JButton("2. Gestion Reservas");
        JButton btnLibros = new JButton("3. Gestion Libros");
        JButton btnUsuarios = new JButton("4. Gestion Usuarios");
        JButton btnPrestamos = new JButton("5. Gestion Prestamos");
        JButton btnLogin = new JButton("6. Login");

        // Action Listeners para abrir vistas
        btnDashboard.addActionListener(e -> new AdminDashboard().setVisible(true));
        btnReservas.addActionListener(e -> new GestionReservas().setVisible(true));
        btnLibros.addActionListener(e -> new GestionLibros().setVisible(true));
        btnUsuarios.addActionListener(e -> new GestionUsuarios().setVisible(true));
        btnPrestamos.addActionListener(e -> new GestionPrestamos().setVisible(true));
        btnLogin.addActionListener(e -> new Login().setVisible(true));

        panel.add(btnDashboard);
        panel.add(btnReservas);
        panel.add(btnLibros);
        panel.add(btnUsuarios);
        panel.add(btnPrestamos);
        panel.add(btnLogin);

        add(panel);
    }

    public static void main(String[] args) {
        // Punto de entrada para pruebas visuales
        SwingUtilities.invokeLater(() -> {
            new VisualizadorVistas().setVisible(true);
        });
    }
}