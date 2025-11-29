package biblioteca.view.estudiantes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

public class ReservasEstudianteView extends JFrame {

    private JTable tablaReservas;
    private JButton botonCancelarReserva;
    private JButton botonVolver;
    private DefaultTableModel modeloTabla;

    public ReservasEstudianteView() {
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Mis Reservas Activas");
        setSize(900, 600);
        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
    }

    private void inicializarComponentes() {

        //Header
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(Color.WHITE);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        //Boton Volver
        botonVolver = new JButton("← Volver");
        botonVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botonVolver.setForeground(new Color(100, 100, 100));
        botonVolver.setContentAreaFilled(false);
        botonVolver.setBorderPainted(false);
        botonVolver.setFocusPainted(false);
        botonVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Título
        JLabel titulo = new JLabel("Mis Reservas Pendientes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(33, 33, 33));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelHeader.add(botonVolver, BorderLayout.WEST);
        panelHeader.add(titulo, BorderLayout.CENTER);
        panelHeader.add(new JLabel("                           "), BorderLayout.EAST);

        add(panelHeader, BorderLayout.NORTH);

        // --- TABLA ---
        String[] columnas = {"ID", "Libro", "Fecha Reserva", "Estado"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaReservas = new JTable(modeloTabla);
        tablaReservas.setRowHeight(35);
        tablaReservas.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Estilos
        tablaReservas.setGridColor(new Color(230, 230, 230));
        tablaReservas.setShowGrid(true);
        tablaReservas.setBackground(Color.WHITE);
        tablaReservas.setForeground(Color.BLACK);
        tablaReservas.setSelectionBackground(new Color(255, 245, 230));
        tablaReservas.setSelectionForeground(new Color(230, 126, 34));

        JScrollPane scroll = new JScrollPane(tablaReservas);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(scroll, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelInferior.setBackground(Color.WHITE);

        botonCancelarReserva = new JButton("Cancelar Reserva Seleccionada");
        botonCancelarReserva.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botonCancelarReserva.setBackground(new Color(231, 76, 60));
        botonCancelarReserva.setForeground(Color.WHITE);
        botonCancelarReserva.setFocusPainted(false);
        botonCancelarReserva.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonCancelarReserva.setPreferredSize(new Dimension(280, 45));

        panelInferior.add(botonCancelarReserva);
        add(panelInferior, BorderLayout.SOUTH);
    }

    // getters
    public JTable getTablaReservas() {
        return tablaReservas;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    // Listeners
    public void setCancelarListener(ActionListener l) {
        botonCancelarReserva.addActionListener(l);
    }

    public void setVolverListener(ActionListener l) {
        botonVolver.addActionListener(l);
    }
}
