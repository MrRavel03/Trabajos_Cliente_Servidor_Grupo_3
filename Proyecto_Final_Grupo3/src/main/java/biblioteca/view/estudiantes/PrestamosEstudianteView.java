package biblioteca.view.estudiantes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class PrestamosEstudianteView extends JFrame {
    private JTable tablaPrestamos;
    private JButton botonDevolverLibro;
    private JButton botonVolver;

    public PrestamosEstudianteView() {
        configurarVentana();
        inicializarComponentes();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Mis Préstamos");
        setSize(800, 600);
        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {

        //Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(33, 33, 33));
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Boton de Volver y Título
        botonVolver = new JButton("←");
        botonVolver.setFont(new Font("Segoe UI", Font.BOLD, 20));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setBackground(new Color(33, 33, 33));
        botonVolver.setFocusPainted(false);
        botonVolver.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        //Titulo
        JLabel titulo = new JLabel("Mis Préstamos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);

        JPanel panelHeader = new JPanel(new GridBagLayout());
        panelHeader.setBackground(new Color(33, 33, 33));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;

        //Botón izquierdo
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelHeader.add(botonVolver, gbc);

        //Título centrado
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panelHeader.add(titulo, gbc);

        //Agregar la
        add(panelHeader, BorderLayout.NORTH);

        //Tabla de Préstamos
        String[] columnas = {"ID", "Libro", "Fecha Salida", "Fecha Devolución", "Estado"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tablaPrestamos = new JTable(modelo);
        tablaPrestamos.setRowHeight(35);
        tablaPrestamos.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tablaPrestamos.setForeground(Color.WHITE);
        tablaPrestamos.setBackground(new Color(60, 60, 60));

        JTableHeader header = tablaPrestamos.getTableHeader();
        header.setBackground(new Color(80, 80, 80));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JScrollPane scroll = new JScrollPane(tablaPrestamos);
        scroll.getViewport().setBackground(new Color(60, 60, 60));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1));

        panelPrincipal.add(scroll);
        panelPrincipal.add(Box.createVerticalStrut(20));

        //Boton devolver libro
        botonDevolverLibro = new JButton("Devolver Libro Seleccionado");
        botonDevolverLibro.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonDevolverLibro.setBackground(new Color(52, 152, 219));
        botonDevolverLibro.setForeground(Color.WHITE);
        botonDevolverLibro.setFocusPainted(false);
        botonDevolverLibro.setFont(new Font("Segoe UI", Font.BOLD, 15));

        panelPrincipal.add(botonDevolverLibro);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    public JTable getTablaPrestamos() { return tablaPrestamos; }
    public JButton getBotonDevolverLibro() { return botonDevolverLibro; }
    public JButton getBotonVolver() { return botonVolver; }
}
