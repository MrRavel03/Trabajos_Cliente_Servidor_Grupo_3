package biblioteca.view;

import javax.swing.*;
import java.awt.*;

public class CatalogoLibrosEstudiantesView extends JFrame{
    private JPanel panelSuperior;
    private JTextField buscador;
    private JComboBox seleccionadorCategorias;
    private JTable tablaLibros;
    private JScrollPane scrollTablaLibros;

    public CatalogoLibrosEstudiantesView() {

        setTitle("Catálogo de Libros");
        setSize(720, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        //Barra de Arriba
        panelSuperior = new JPanel();
        panelSuperior.setBounds(0,0,720,60);
        panelSuperior.setBackground(new Color(0, 0, 0, 160));
        panelSuperior.setLayout(null);
        add(panelSuperior);

        //Buscador
        buscador = new JTextField();
        buscador.setBounds(20, 15, 200, 30);
        panelSuperior.add(buscador);

        //Categorias
        seleccionadorCategorias = new JComboBox<>();
        seleccionadorCategorias.setBounds(240,15,180,30);
        seleccionadorCategorias.addItem("Terror");
        seleccionadorCategorias.addItem("Drama");
        seleccionadorCategorias.addItem("Fantasia");
        seleccionadorCategorias.addItem("Ciencia");
        seleccionadorCategorias.addItem("Historia");
        panelSuperior.add(seleccionadorCategorias);

        //Libros
        String[] columnas = {"Portada","Titulo","Autor","Categoria","Estado"};
        Object[][] libros = new Object[0][5];
        tablaLibros = new JTable(libros, columnas);
        tablaLibros.setRowHeight(50);

        scrollTablaLibros =new JScrollPane(tablaLibros);
        scrollTablaLibros.setBounds(20, 80, 660, 360);
        add(scrollTablaLibros);


        setVisible(true);
    }

    public JTextField getCampoBusqueda() { return buscador; }
    public JComboBox<String> getComboCategorias() { return seleccionadorCategorias; }
    public JTable getTablaLibros() { return tablaLibros; }

}
