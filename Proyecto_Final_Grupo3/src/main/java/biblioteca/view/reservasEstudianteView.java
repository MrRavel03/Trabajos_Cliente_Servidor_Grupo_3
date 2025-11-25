package biblioteca.view;

import javax.swing.*;
import java.awt.*;

public class reservasEstudianteView extends JFrame {

    private JTable tablaReservas;
    private JButton botonCancelarReserva;

    public reservasEstudianteView() {
        configurarVentana();
        inicializarComponentes();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Mis Reservas");
        setSize(800,600);
        setMinimumSize(new Dimension(400, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        //Panel
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(64, 64, 64));
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Titulo
        JLabel titulo = new JLabel("Mis Reservas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(Color.WHITE);

        //Tabla con mis Reservas
        Color colorTabla = new Color(103,103,103);
        String[] columnas = {"ID", "Libro", "Fecha Reserva", "Estado"};
        Object[][] datos = {};

        tablaReservas = new JTable(datos, columnas);
        tablaReservas.setRowHeight(40);
        tablaReservas.setBackground(colorTabla);
        tablaReservas.setForeground(Color.WHITE);
        tablaReservas.setSelectionBackground(new Color(75, 110, 175)); // selección azul suave
        tablaReservas.setSelectionForeground(Color.WHITE);

        JScrollPane scrollTabla = new JScrollPane(tablaReservas);
        scrollTabla.setPreferredSize(new Dimension(500, 300));
        scrollTabla.getViewport().setBackground(colorTabla);

        //Boton para cancelar reservacion
        botonCancelarReserva = new JButton("Cancelar Reserva Seleccionada");
        botonCancelarReserva.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonCancelarReserva.setBackground(new Color(236, 91, 75));
        botonCancelarReserva.setForeground(Color.WHITE);
        botonCancelarReserva.setFocusPainted(false);

        add(panelPrincipal, BorderLayout.CENTER);
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createVerticalStrut(15));
        panelPrincipal.add(scrollTabla);
        panelPrincipal.add(Box.createVerticalStrut(20));
        panelPrincipal.add(botonCancelarReserva);
    }

    public JTable getTablaReservas() {return tablaReservas;}
    public JButton getBotonCancelarReserva() {return botonCancelarReserva;}
}
