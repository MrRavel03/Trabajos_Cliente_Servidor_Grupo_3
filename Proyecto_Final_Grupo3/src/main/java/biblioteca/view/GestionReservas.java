package biblioteca.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionReservas extends JFrame {
    private JTable tablaReservas;
    private JButton btnAceptar, btnRechazar, btnDevolver, btnRegresar;

    public GestionReservas() {
        setTitle("Gestion de Reservas - Biblioteca");
        setSize(900, 500);
        setLocationRelativeTo(null);

        inicializarComponentes();
        cargarDatosPrueba();
    }

    private void inicializarComponentes() {
        // Panel principal con border layout
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Titulo de la seccion
        JLabel titulo = new JLabel("GESTION DE RESERVAS PENDIENTES", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Configuracion de tabla con datos de prueba
        String[] columnas = {"ID Reserva", "Usuario", "Libro", "Fecha Reserva", "Estado"};
        Object[][] datosPrueba = {
                {"R-001", "Ana Garcia", "Cien Anos de Soledad", "2024-01-15", "Pendiente"},
                {"R-002", "Carlos Lopez", "Don Quijote", "2024-01-16", "Pendiente"},
                {"R-003", "Maria Rodriguez", "1984", "2024-01-14", "Pendiente"},
                {"R-004", "Juan Martinez", "Orgullo y Prejuicio", "2024-01-17", "Pendiente"}
        };

        tablaReservas = new JTable(datosPrueba, columnas);
        JScrollPane scroll = new JScrollPane(tablaReservas);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        // Panel inferior con botones de accion
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAceptar = new JButton("Aceptar Reserva");
        btnRechazar = new JButton("Rechazar Reserva");
        btnDevolver = new JButton("Marcar como Devuelto");
        btnRegresar = new JButton("Regresar al Dashboard");

        // Action listeners para demostracion
        btnAceptar.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Reserva aceptada exitosamente (Demo)"));

        btnRechazar.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Reserva rechazada (Demo)"));

        btnDevolver.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Libro marcado como devuelto (Demo)"));

        btnRegresar.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            this.dispose();
        });

        panelBotones.add(btnAceptar);
        panelBotones.add(btnRechazar);
        panelBotones.add(btnDevolver);
        panelBotones.add(btnRegresar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    private void cargarDatosPrueba() {
        // Metodo para cargar datos de demostracion
        // En version final se conectaria con la base de datos
    }
}