package biblioteca.view;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class reservasEstudianteView extends JFrame {

    private JTable tablaReservas;
    private JButton botonCancelarReserva;
    private JButton botonVolver;

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
        Color colorFondo = new Color(33,33,33);
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        //Boton Volver
        botonVolver = new JButton("←");
        botonVolver.setFont(new Font("Segoe UI", Font.BOLD, 20));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setBackground(colorFondo);
        botonVolver.setFocusPainted(false);
        botonVolver.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        //Titulo
        JLabel titulo = new JLabel("Mis Reservas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);

        //Panel que contiene flecha y título centrado
        JPanel panelHeader = new JPanel(new GridBagLayout());
        panelHeader.setBackground(colorFondo);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;

        //Flecha izquierda
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelHeader.add(botonVolver, gbc);

        //Título centrado
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panelHeader.add(titulo, gbc);
        add(panelHeader, BorderLayout.NORTH);

        //Tabla con mis Reservas
        Color colorTabla = new Color(60,60,60);
        String[] columnas = {"ID", "Libro", "Fecha Reserva", "Estado"};
        Object[][] datos = {};

        tablaReservas = new JTable(datos, columnas);
        tablaReservas.setRowHeight(40);
        tablaReservas.setBackground(colorTabla);
        tablaReservas.setForeground(Color.WHITE);
        tablaReservas.setSelectionBackground(new Color(75, 110, 175)); // selección azul suave
        tablaReservas.setSelectionForeground(Color.WHITE);

        JTableHeader header = tablaReservas.getTableHeader();
        header.setBackground(new Color(80, 80, 80));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JScrollPane scroll = new JScrollPane(tablaReservas);
        scroll.setPreferredSize(new Dimension(500, 300));
        scroll.getViewport().setBackground(colorTabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90), 1));

        //Boton para cancelar reservacion
        botonCancelarReserva = new JButton("Cancelar Reserva Seleccionada");
        botonCancelarReserva.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonCancelarReserva.setBackground(new Color(236, 91, 75));
        botonCancelarReserva.setForeground(Color.WHITE);
        botonCancelarReserva.setFocusPainted(false);

        add(panelPrincipal, BorderLayout.CENTER);
        panelPrincipal.add(Box.createVerticalStrut(15));
        panelPrincipal.add(scroll);
        panelPrincipal.add(Box.createVerticalStrut(20));
        panelPrincipal.add(botonCancelarReserva);
    }
    public JTable getTablaReservas() {return tablaReservas;}
    public JButton getBotonCancelarReserva() {return botonCancelarReserva;}
}
