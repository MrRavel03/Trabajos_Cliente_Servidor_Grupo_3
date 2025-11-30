package biblioteca.view.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class GestionLibrosView extends JFrame {

    private JTextField txtTitulo, txtAutor, txtCategoria;
    private JButton btnAgregar, btnEditar, btnEliminar, btnLimpiar, btnRegresar;
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    public GestionLibrosView() {
        setTitle("Gestion de Libros - Biblioteca");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Panel principal con border layout
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior para formulario de entrada
        JPanel panelFormulario = new JPanel(new GridLayout(3, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Libro"));

        // Campos del formulario
        panelFormulario.add(new JLabel("Titulo:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("Categoria:"));
        txtCategoria = new JTextField();
        panelFormulario.add(txtCategoria);

        // Botones del formulario
        JPanel panelBotonesForm = new JPanel(new FlowLayout());
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnRegresar = new JButton("Regresar");

        panelBotonesForm.add(btnAgregar);
        panelBotonesForm.add(btnEditar);
        panelBotonesForm.add(btnEliminar);
        panelBotonesForm.add(btnLimpiar);
        panelBotonesForm.add(btnRegresar);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotonesForm, BorderLayout.SOUTH);


        // Tabla de libros existentes
        String[] columnas = {"ID", "Titulo", "Autor", "Categoria", "Disponible", "Estado"};

        modeloTabla = new DefaultTableModel(columnas, 0){

            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        tablaLibros = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaLibros);

        // Agregar componentes al panel principal
        panelPrincipal.add(panelNorte, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    // Getters de datos

    public String getTitulo(){
        return txtTitulo.getText().trim();
    }

    public String getAutor() {
        return txtAutor.getText().trim();
    }

    public String getCategoria() {
        return txtCategoria.getText().trim();
    }

    // metodos para la tabla

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JTable getTablaLibros() {
        return tablaLibros;
    }

    // metodo de limpiar datos

    public void limpiarFormulario(){
        txtTitulo.setText("");
        txtAutor.setText("");
        txtCategoria.setText("");
    }

    // Listeners

    public void setAgregarListener(ActionListener l){
        btnAgregar.addActionListener(l);
    }

    public void setEditarListener(ActionListener l){
        btnEditar.addActionListener(l);
    }

    public void setEliminarListener(ActionListener l){
        btnEliminar.addActionListener(l);
    }

    public void setLimpiarListener(ActionListener l){
        btnLimpiar.addActionListener(l);
    }

    public void setRegresarListener(ActionListener l) { btnRegresar.addActionListener(l); }
    //sets

    public void setTitulo(String titulo) {
        txtTitulo.setText(titulo);
    }

    public void setAutor(String autor) {
        txtAutor.setText(autor);
    }

    public void setCategoria(String categoria) {
        txtCategoria.setText(categoria);
    }
}