package biblioteca.view.estudiantes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

public class PrestamosEstudianteView extends JFrame {

    private JTable tablaPrestamos;
    private JButton botonVolver;
    private DefaultTableModel modeloTabla;

    public PrestamosEstudianteView() {

        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Mis Préstamos");
        setSize(900, 600);
        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
    }

    private void inicializarComponentes() {

        //Panel principal
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(Color.WHITE);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        //Boton de Volver y Título
        botonVolver = new JButton("← Volver");
        botonVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botonVolver.setForeground(new Color(100, 100, 100));
        botonVolver.setContentAreaFilled(false);
        botonVolver.setBorderPainted(false);
        botonVolver.setFocusPainted(false);
        botonVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //Titulo
        JLabel titulo = new JLabel("Mis Préstamos");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(33, 33, 33));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelHeader.add(botonVolver, BorderLayout.WEST);
        panelHeader.add(titulo, BorderLayout.CENTER);
        JLabel dummy = new JLabel("                       ");
        panelHeader.add(dummy, BorderLayout.EAST);

        add(panelHeader, BorderLayout.NORTH);


        String[] columnas = {"ID", "Libro", "Fecha Salida", "Fecha Devolución", "Estado"};

        // Modelo no editable
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPrestamos = new JTable(modeloTabla);
        tablaPrestamos.setRowHeight(30);
        tablaPrestamos.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        //Tabla de Préstamos
        tablaPrestamos.setGridColor(new Color(230, 230, 230));
        tablaPrestamos.setShowGrid(true);
        tablaPrestamos.setBackground(Color.WHITE);
        tablaPrestamos.setForeground(Color.BLACK);
        tablaPrestamos.setSelectionBackground(new Color(240, 242, 245));
        tablaPrestamos.setSelectionForeground(Color.BLACK);

        tablaPrestamos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaPrestamos.getTableHeader().setBackground(new Color(245, 247, 250));
        tablaPrestamos.getTableHeader().setForeground(new Color(33, 33, 33));

        JScrollPane scroll = new JScrollPane(tablaPrestamos);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        add(scroll, BorderLayout.CENTER);
    }

    public JTable getTablaPrestamos() {
        return tablaPrestamos;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public void setVolverListener(ActionListener l) {
        botonVolver.addActionListener(l);
    }
}
