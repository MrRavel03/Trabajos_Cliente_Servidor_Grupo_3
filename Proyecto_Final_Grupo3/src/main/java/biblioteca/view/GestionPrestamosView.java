package biblioteca.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class GestionPrestamosView extends JFrame {

    private JTable tablaPrestamos;

    private DefaultTableModel modeloTabla;

    private JTextField txtIdUsuario;
    private JTextField txtIdLibro;
    private JButton btnPrestar;
    private JButton btnDevolver;
    private JButton btnRegresar;

    public GestionPrestamosView() {

        inicializarComponentes();
        configurarVentana();
    }

    private void configurarVentana(){
        setTitle("Gestion de Prestamos - Biblioteca");
        setSize(950, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // cierra solo la ventana
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(240, 242, 245));
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel panelFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFormulario.add(new JLabel("ID Usuario:"));
        txtIdUsuario = new JTextField(10);
        panelFormulario.add(txtIdUsuario);
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        panelFormulario.add(new JLabel("ID Libro:"));
        txtIdLibro = new JTextField(10);
        panelFormulario.add(txtIdLibro);

        btnPrestar = new JButton("Nuevo Préstamo");
        estilizarBoton(btnPrestar, new Color(46, 204, 113));

        String[] columnas = {"ID Préstamo", "Libro", "ID Libro", "Usuario", "Fecha Salida", "Estado"};
        // para que no se pueda editar
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tablaPrestamos = new JTable(modeloTabla);
        tablaPrestamos.setRowHeight(25);

        tablaPrestamos.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaPrestamos.getTableHeader().setBackground(new Color(230, 230, 230));

        JScrollPane scrollTabla = new JScrollPane(tablaPrestamos);
        scrollTabla.getViewport().setBackground(Color.WHITE);

        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSur.setBackground(new Color(240, 242, 245));

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnDevolver = new JButton("Registrar Devolución");
        estilizarBoton(btnDevolver, new Color(231, 76, 60));

        btnRegresar = new JButton("Regresar");
        estilizarBoton(btnRegresar, Color.GRAY);

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);

        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        panelFormulario.add(btnPrestar);
        panelSur.add(btnDevolver);
        panelSur.add(btnRegresar);

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void estilizarBoton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    //Getters

    public String getIdUsuario(){
        return txtIdUsuario.getText();
    }

    public String getIdLibro(){
        return txtIdLibro.getText();
    }

    public void limpiarCampos(){
        txtIdLibro.setText("");
        txtIdUsuario.setText("");
    }

    public DefaultTableModel getModeloTabla() { return modeloTabla; }

    public int getIdPrestamoSeleccionado() {
        int fila = tablaPrestamos.getSelectedRow();
        return (fila != -1) ? (int) modeloTabla.getValueAt(fila, 0) : -1;
    }

    public int getIdLibroSeleccionado() {
        int fila = tablaPrestamos.getSelectedRow();
        return (fila != -1) ? (int) modeloTabla.getValueAt(fila, 2) : -1;
    }

    // --- LISTENERS ---
    public void setPrestarListener(ActionListener l) {
        btnPrestar.addActionListener(l);
    }

    public void setDevolverListener(ActionListener l) {
        btnDevolver.addActionListener(l);
    }

    public void setRegresarListener(ActionListener l) {
        btnRegresar.addActionListener(l);
    }
}