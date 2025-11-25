package biblioteca.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestionUsuarios extends JFrame {
    private JTextField txtNombre, txtEmail, txtTelefono;
    private JComboBox<String> comboTipo;
    private JButton btnGuardar, btnEditar, btnEliminar, btnRegresar;
    private JTable tablaUsuarios;

    public GestionUsuarios() {
        setTitle("Gestion de Usuarios - Biblioteca");
        setSize(900, 500);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(4, 2, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));

        // Campos del formulario
        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);

        panelFormulario.add(new JLabel("Telefono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Tipo de Usuario:"));
        String[] tipos = {"Estudiante", "Profesor", "Bibliotecario", "Administrador"};
        comboTipo = new JComboBox<>(tipos);
        panelFormulario.add(comboTipo);

        // Botones de accion
        JPanel panelBotonesForm = new JPanel(new FlowLayout());
        btnGuardar = new JButton("Guardar Usuario");
        btnEditar = new JButton("Editar Usuario");
        btnEliminar = new JButton("Eliminar Usuario");

        panelBotonesForm.add(btnGuardar);
        panelBotonesForm.add(btnEditar);
        panelBotonesForm.add(btnEliminar);

        // Tabla de usuarios
        String[] columnas = {"ID", "Nombre", "Email", "Telefono", "Tipo", "Estado"};
        Object[][] datosPrueba = {
                {"U-001", "Ana Garcia", "ana@email.com", "1234-5678", "Estudiante", "Activo"},
                {"U-002", "Carlos Lopez", "carlos@email.com", "2345-6789", "Profesor", "Activo"},
                {"U-003", "Maria Rodriguez", "maria@email.com", "3456-7890", "Estudiante", "Inactivo"},
                {"U-004", "Juan Martinez", "juan@email.com", "4567-8901", "Bibliotecario", "Activo"}
        };

        tablaUsuarios = new JTable(datosPrueba, columnas);
        JScrollPane scrollTabla = new JScrollPane(tablaUsuarios);

        // Boton regresar
        btnRegresar = new JButton("Regresar al Dashboard");
        btnRegresar.addActionListener(e -> {
            new AdminDashboard().setVisible(true);
            this.dispose();
        });

        // Action listeners de demostracion
        btnGuardar.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Usuario guardado (Demo)"));

        btnEditar.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Usuario editado (Demo)"));

        btnEliminar.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Usuario eliminado (Demo)"));

        // Agregar componentes
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.NORTH);
        panelSuperior.add(panelBotonesForm, BorderLayout.SOUTH);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(btnRegresar, BorderLayout.SOUTH);

        add(panelPrincipal);
    }
}