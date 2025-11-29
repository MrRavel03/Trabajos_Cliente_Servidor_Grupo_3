package biblioteca.view.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class GestionUsuariosView extends JFrame {

    private JTextField txtNombre, txtEmail, txtTelefono;
    private JPasswordField txtPassword;
    private JComboBox<String> comboTipo;
    private JButton btnGuardar, btnEditar, btnEliminar, btnLimpiar;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    public GestionUsuariosView() {
        setTitle("Gestión de Usuarios - Biblioteca");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 15, 15));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));

        // Fila 1
        panelFormulario.add(new JLabel("Nombre Completo:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        // Fila 2
        panelFormulario.add(new JLabel("Correo Electrónico:"));
        txtEmail = new JTextField();
        panelFormulario.add(txtEmail);

        // Fila 3
        panelFormulario.add(new JLabel("Contraseña:"));
        txtPassword = new JPasswordField();
        panelFormulario.add(txtPassword);

        // Fila 4
        panelFormulario.add(new JLabel("Rol de Usuario:"));
        String[] tipos = {"ESTUDIANTE", "BIBLIOTECARIO"};
        comboTipo = new JComboBox<>(tipos);
        comboTipo.setBackground(Color.WHITE);
        panelFormulario.add(comboTipo);

        // Botones de accion
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(Color.WHITE);

        btnGuardar = crearBoton("Guardar", new Color(46, 204, 113));
        btnEditar = crearBoton("Editar", new Color(52, 152, 219));
        btnEliminar = crearBoton("Eliminar", new Color(231, 76, 60));
        btnLimpiar = crearBoton("Limpiar", Color.GRAY);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Agrupamos Formulario y Botones en el Norte
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.setBackground(Color.WHITE);
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);

        // --- TABLA ---
        String[] columnas = {"ID", "Nombre", "Email", "Rol", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setRowHeight(25);
        tablaUsuarios.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablaUsuarios.getTableHeader().setBackground(new Color(240, 240, 240));

        JScrollPane scrollTabla = new JScrollPane(tablaUsuarios);
        scrollTabla.getViewport().setBackground(Color.WHITE);

        panelPrincipal.add(panelNorte, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        setContentPane(panelPrincipal);
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    //gets
    public String getNombre() { return txtNombre.getText().trim(); }
    public String getEmail() { return txtEmail.getText().trim(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public String getRolSeleccionado() { return (String) comboTipo.getSelectedItem(); }

    public JTable getTablaUsuarios() { return tablaUsuarios; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }

    public void limpiarFormulario() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        comboTipo.setSelectedIndex(0);
    }

    //sets

    public void setNombre(String n) { txtNombre.setText(n); }
    public void setEmail(String e) { txtEmail.setText(e); }
    public void setRol(String r) { comboTipo.setSelectedItem(r); }

    // listeners
    public void setGuardarListener(ActionListener l) { btnGuardar.addActionListener(l); }
    public void setEditarListener(ActionListener l) { btnEditar.addActionListener(l); }
    public void setEliminarListener(ActionListener l) { btnEliminar.addActionListener(l); }
    public void setLimpiarListener(ActionListener l) { btnLimpiar.addActionListener(l); }

}