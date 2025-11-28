package biblioteca.view.estudiantes;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class MultasEstudianteView extends JFrame {

    private JTable tablaMultas;
    private JButton botonVolver;

    public MultasEstudianteView() {
        configurarVentana();
        inicializarComponentes();
    }
    public void configurarVentana(){
        setTitle("Mis Multas");
        setSize(800,600);
        setMinimumSize(new Dimension(400, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {

        //Panel Principal
        Color colorFondo = new Color(33,33,33);
        JPanel panelPrincipal = new JPanel(new BorderLayout(0, 20));
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        //Botón para Volver a la página anterior
        botonVolver = new JButton("←");
        botonVolver.setFont(new Font("Segoe UI", Font.BOLD, 18));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setBackground(colorFondo);
        botonVolver.setFocusPainted(false);
        botonVolver.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        //Título
        JLabel titulo = new JLabel("Multas Pendientes");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);


        //Panel Cabecera
        JPanel panelCabecera = new JPanel(new GridBagLayout());
        panelCabecera.setBackground(colorFondo);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;

        //Flecha (columna 0)
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panelCabecera.add(botonVolver, gbc);


        //Título (columna 1)
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panelCabecera.add(titulo, gbc);


        //Definiendo la Tabla
        String[] columnas = {"ID", "Usuario", "Libro", "Monto", "Fecha", "Estado"};

        tablaMultas = new JTable(new String[0][columnas.length], columnas);

        tablaMultas.setRowHeight(28);
        tablaMultas.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tablaMultas.setForeground(Color.WHITE);
        tablaMultas.setBackground(new Color(60, 60, 60));
        tablaMultas.setGridColor(Color.DARK_GRAY);

        //Definiando el estilo de los headers de las columnas
        JTableHeader header = tablaMultas.getTableHeader();
        header.setBackground(new Color(80, 80, 80));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JScrollPane scroll = new JScrollPane(tablaMultas);
        scroll.getViewport().setBackground(new Color(60, 60, 60));
        scroll.setBorder(BorderFactory.createLineBorder(new Color(90, 90, 90), 1));


        //agregando el panel de cabecera al panel principal (boton de volver y título)
        panelPrincipal.add(panelCabecera, BorderLayout.NORTH);

        //agregando al panel principal la tabla con Scroll
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        add(panelPrincipal);
    }

    //Get de la tabla
    public JTable getTablaMultas() {return tablaMultas;}

    //Get del Botón de vuelta
    public JButton getBotonVolver() {return botonVolver;}
}
