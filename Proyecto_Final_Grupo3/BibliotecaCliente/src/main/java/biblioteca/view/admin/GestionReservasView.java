package biblioteca.view.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class GestionReservasView extends JFrame {

    private JTable tablaReservas;
    private DefaultTableModel modeloTabla;
    private JButton btnEntregarLibro;
    private JButton btnCancelarReserva;
    private JButton btnRegresar;

    public GestionReservasView() {
        setTitle("Gestión de Reservas - Biblioteca");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //titulo
        JLabel titulo = new JLabel("Reservas Activas (Pendientes de Retiro)", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(33, 33, 33));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        //tabla
        String[] columnas = {"ID Reserva", "Usuario", "ID Libro", "Título Libro", "Fecha Reserva", "ID Usuario"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tablaReservas = new JTable(modeloTabla);
        tablaReservas.setRowHeight(30);
        tablaReservas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //estilo de la tabla
        tablaReservas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaReservas.getTableHeader().setBackground(new Color(240, 240, 240));
        tablaReservas.setSelectionBackground(new Color(255, 245, 230));
        tablaReservas.setSelectionForeground(Color.BLACK);

        JScrollPane scroll = new JScrollPane(tablaReservas);
        scroll.getViewport().setBackground(Color.WHITE);

        panelPrincipal.add(scroll, BorderLayout.CENTER);

        //botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBotones.setBackground(Color.WHITE);

        btnEntregarLibro = crearBoton("Entregar Libro (Crear Préstamo)", new Color(46, 204, 113));
        btnCancelarReserva = crearBoton("Cancelar Reserva", new Color(231, 76, 60));
        btnRegresar = crearBoton("Regresar", new Color(149, 165, 166));

        panelBotones.add(btnEntregarLibro);
        panelBotones.add(btnCancelarReserva);
        panelBotones.add(btnRegresar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 40));
        return btn;
    }

    // GETS para los componentes
    public JTable getTablaReservas() { return tablaReservas; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }

    public void setEntregarListener(ActionListener l) { btnEntregarLibro.addActionListener(l); }
    public void setCancelarListener(ActionListener l) { btnCancelarReserva.addActionListener(l); }
    public void setRegresarListener(ActionListener l) { btnRegresar.addActionListener(l); }
}