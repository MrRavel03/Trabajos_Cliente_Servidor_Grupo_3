package biblioteca.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionLibros extends JFrame {
    private JTextField txtTitulo, txtAutor, txtAnio, txtISBN;
    private JButton btnAgregar, btnEditar, btnEliminar, btnLimpiar, btnRegresar;
    private JTextArea areaResultados;
    private JTable tablaLibros;

    public GestionLibros() {
        setTitle("Gestion de Libros - Biblioteca");
        setSize(1000, 600);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Panel principal con border layout
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior para formulario de entrada
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(5, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Agregar/Editar Libro"));

        // Campos del formulario
        panelFormulario.add(new JLabel("Titulo:"));
        txtTitulo = new JTextField();
        panelFormulario.add(txtTitulo);

        panelFormulario.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        panelFormulario.add(txtAutor);

        panelFormulario.add(new JLabel("Anio:"));
        txtAnio = new JTextField();
        panelFormulario.add(txtAnio);

        panelFormulario.add(new JLabel("ISBN:"));
        txtISBN = new JTextField();
        panelFormulario.add(txtISBN);

        // Botones del formulario
        JPanel panelBotonesForm = new JPanel(new FlowLayout());
        btnAgregar = new JButton("Agregar Libro");
        btnEditar = new JButton("Editar Libro");
        btnEliminar = new JButton("Eliminar Libro");
        btnLimpiar = new JButton("Limpiar Campos");

        panelBotonesForm.add(btnAgregar);
        panelBotonesForm.add(btnEditar);
        panelBotonesForm.add(btnEliminar);
        panelBotonesForm.add(btnLimpiar);

        panelFormulario.add(new JLabel()); // Espacio vacio
        panelFormulario.add(panelBotonesForm);

        // Tabla de libros existentes
        String[] columnas = {"ID", "Titulo", "Autor", "Anio", "ISBN", "Estado"};
        Object[][] datosPrueba = {
                {"L-001", "Cien Anos de Soledad", "Gabriel Garcia Marquez", "1967", "978-1234567890", "Disponible"},
                {"L-002", "Don Quijote", "Miguel de Cervantes", "1605", "978-0987654321", "Prestado"},
                {"L-003", "1984", "George Orwell", "1949", "978-1122334455", "Disponible"},
                {"L-004", "Orgullo y Prejuicio", "Jane Austen", "1813", "978-5566778899", "Disponible"}
        };

        tablaLibros = new JTable(datosPrueba, columnas);
        JScrollPane scrollTabla = new JScrollPane(tablaLibros);

        // Boton regresar
        btnRegresar = new JButton("Regresar al Dashboard");
        btnRegresar.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            this.dispose();
        });

        // Action listeners de demostracion
        btnAgregar.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Libro agregado (Demo)"));

        btnEditar.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Libro editado (Demo)"));

        btnEliminar.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Libro eliminado (Demo)"));

        btnLimpiar.addActionListener(e -> {
            txtTitulo.setText("");
            txtAutor.setText("");
            txtAnio.setText("");
            txtISBN.setText("");
        });

        // Agregar componentes al panel principal
        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(btnRegresar, BorderLayout.SOUTH);

        add(panelPrincipal);
    }
}