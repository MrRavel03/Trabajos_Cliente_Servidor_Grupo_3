package biblioteca.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.List;

public class CatalogoLibrosEstudiantesView extends JFrame{
    private JTextField txtBuscador;
    private JComboBox<String> comboCategorias;
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private JButton btnReservar;

    public CatalogoLibrosEstudiantesView() {
        configurarVentana();
        inicializarComponentes();
    }

    public void configurarVentana(){

        setTitle("Catalogo de Libros - Biblioteca");
        setSize(900,600);
        setMinimumSize(new Dimension(600, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(Color.WHITE);
    }
    public void inicializarComponentes(){
        //Barra de Arriba
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelSuperior.setBackground(new Color(240, 242, 245));
        panelSuperior.setPreferredSize(new Dimension(800, 60));
        panelSuperior.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        // Etiqueta
        JLabel lblBuscar = new JLabel("Buscar:");

        lblBuscar.setForeground(new Color(33, 33, 33));
        lblBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));


        //Buscador
        txtBuscador = new JTextField(20);


        //Categorias
        comboCategorias = new JComboBox<>();
        comboCategorias.setBackground(Color.WHITE);

        panelSuperior.add(lblBuscar);
        panelSuperior.add(txtBuscador);
        panelSuperior.add(comboCategorias);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla de libros
        String[] columnas = {"ID", "Título", "Autor", "Categoría", "Estado"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setRowHeight(30);
        tablaLibros.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Colores de la tabla
        tablaLibros.setGridColor(new Color(230, 230, 230));
        tablaLibros.setShowGrid(true);
        tablaLibros.setBackground(Color.WHITE);
        tablaLibros.setForeground(Color.BLACK);
        tablaLibros.setSelectionBackground(new Color(52, 152, 219));
        tablaLibros.setSelectionForeground(Color.WHITE);

        // Encabezado de la tabla
        tablaLibros.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tablaLibros.getTableHeader().setBackground(new Color(240, 242, 245));
        tablaLibros.getTableHeader().setForeground(new Color(33, 33, 33));

        JScrollPane scrollTablaLibros = new JScrollPane(tablaLibros);
        scrollTablaLibros.getViewport().setBackground(Color.WHITE);
        scrollTablaLibros.setBorder(BorderFactory.createEmptyBorder());

        add(scrollTablaLibros, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 15));
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));

        btnReservar = new JButton("Reservar Libro Seleccionado");
        btnReservar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnReservar.setBackground(new Color(46, 204, 113));
        btnReservar.setForeground(Color.WHITE);
        btnReservar.setFocusPainted(false);
        btnReservar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReservar.setPreferredSize(new Dimension(250, 40));

        panelInferior.add(btnReservar);
        add(panelInferior, BorderLayout.SOUTH);
    }

    // Getters de datos

    public String getTextoBusqueda() {
        return txtBuscador.getText().trim();
    }

    public String getCategoriaSeleccionada() {
        return (String) comboCategorias.getSelectedItem();
    }

    public JTable getTablaLibros() {
        return tablaLibros;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    // Listeners

    public void setBusquedaListener(ActionListener l) {
        txtBuscador.addActionListener(l);
    }

    public void setFiltroCategoriaListener(ActionListener l) {
        comboCategorias.addActionListener(l);
    }

    public void setReservarListener(ActionListener l) {
        btnReservar.addActionListener(l);
    }


    // Metodo para llenar el combo desde el controller

    public void cargarCategoria (List<String> categorias){

        comboCategorias.removeAllItems();
        comboCategorias.addItem("Todas");

        for (String cat : categorias){
            comboCategorias.addItem(cat);
        }
    }
}
