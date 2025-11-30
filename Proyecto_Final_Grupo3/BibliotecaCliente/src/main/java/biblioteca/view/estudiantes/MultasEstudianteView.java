package biblioteca.view.estudiantes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

public class MultasEstudianteView extends JFrame {

    private JTable tablaMultas;
    private DefaultTableModel modeloTabla;
    private JButton botonVolver;

    public MultasEstudianteView() {
        configurarVentana();
        inicializarComponentes();
    }

    public void configurarVentana(){
        setTitle("Mis Multas");
        setSize(900,600);
        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
    }

    private void inicializarComponentes() {

        //Panel Principal
        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(Color.WHITE);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));


        //Botón para Volver a la página anterior
        botonVolver = new JButton("← Volver");
        botonVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botonVolver.setForeground(new Color(100, 100, 100));
        botonVolver.setContentAreaFilled(false);
        botonVolver.setBorderPainted(false);
        botonVolver.setFocusPainted(false);
        botonVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));


        //Título
        JLabel titulo = new JLabel("Mis Multas");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(33, 33, 33));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelHeader.add(botonVolver, BorderLayout.WEST);
        panelHeader.add(titulo, BorderLayout.CENTER);
        panelHeader.add(new JLabel("                         "), BorderLayout.EAST);

        add(panelHeader, BorderLayout.NORTH);

        //Definiendo la Tabla
        String[] columnas = {"ID", "Libro", "Monto", "Fecha Generación", "Estado"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaMultas = new JTable(modeloTabla);
        tablaMultas.setRowHeight(30);
        tablaMultas.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        tablaMultas.setGridColor(new Color(230, 230, 230));
        tablaMultas.setShowGrid(true);
        tablaMultas.setBackground(Color.WHITE);
        tablaMultas.setForeground(Color.BLACK);
        tablaMultas.setSelectionBackground(new Color(255, 235, 235));
        tablaMultas.setSelectionForeground(new Color(192, 57, 43));

        // Encabezado
        tablaMultas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaMultas.getTableHeader().setBackground(new Color(245, 247, 250));
        tablaMultas.getTableHeader().setForeground(new Color(33, 33, 33));

        JScrollPane scroll = new JScrollPane(tablaMultas);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        add(scroll, BorderLayout.CENTER);
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public void setVolverListener(ActionListener l) {
        botonVolver.addActionListener(l);
    }
}
