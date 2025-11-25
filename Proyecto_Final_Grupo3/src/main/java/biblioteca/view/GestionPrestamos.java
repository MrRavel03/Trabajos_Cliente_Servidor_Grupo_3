package biblioteca.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionPrestamos extends JFrame {
    private JTable tablaPrestamos;
    private JButton btnNuevoPrestamo, btnRegistrarDevolucion, btnVerVencidos, btnRegresar;

    public GestionPrestamos() {
        setTitle("Gestion de Prestamos - Biblioteca");
        setSize(900, 500);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Titulo
        JLabel titulo = new JLabel("GESTION DE PRESTAMOS ACTIVOS", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Tabla de prestamos
        String[] columnas = {"ID Prestamo", "Usuario", "Libro", "Fecha Prestamo", "Fecha Devolucion", "Estado"};
        Object[][] datosPrueba = {
                {"P-001", "Ana Garcia", "Cien Anos de Soledad", "2024-01-10", "2024-01-24", "Activo"},
                {"P-002", "Carlos Lopez", "Don Quijote", "2024-01-12", "2024-01-26", "Activo"},
                {"P-003", "Maria Rodriguez", "1984", "2024-01-08", "2024-01-22", "Vencido"},
                {"P-004", "Juan Martinez", "Orgullo y Prejuicio", "2024-01-15", "2024-01-29", "Activo"}
        };

        tablaPrestamos = new JTable(datosPrueba, columnas);
        JScrollPane scrollTabla = new JScrollPane(tablaPrestamos);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnNuevoPrestamo = new JButton("Nuevo Prestamo");
        btnRegistrarDevolucion = new JButton("Registrar Devolucion");
        btnVerVencidos = new JButton("Ver Prestamos Vencidos");
        btnRegresar = new JButton("Regresar al Dashboard");

        // Action listeners de demostracion
        btnNuevoPrestamo.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Nuevo prestamo (Demo)"));

        btnRegistrarDevolucion.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Devolucion registrada (Demo)"));

        btnVerVencidos.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Mostrando prestamos vencidos (Demo)"));

        btnRegresar.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            this.dispose();
        });

        panelBotones.add(btnNuevoPrestamo);
        panelBotones.add(btnRegistrarDevolucion);
        panelBotones.add(btnVerVencidos);
        panelBotones.add(btnRegresar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }
}