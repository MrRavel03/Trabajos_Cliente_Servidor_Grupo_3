package biblioteca.view;

import javax.swing.*;
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
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // cierra solo la ventana
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- ZONA SUPERIOR: REGISTRAR NUEVO (Indispensable para Req. 5) ---
        JPanel panelFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFormulario.add(new JLabel("ID Usuario:"));
        txtIdUsuario = new JTextField(10);
        panelFormulario.add(txtIdUsuario);

        panelFormulario.add(new JLabel("ID Libro:"));
        txtIdLibro = new JTextField(10);
        panelFormulario.add(txtIdLibro);

        btnPrestar = new JButton("Nuevo Préstamo");
        panelFormulario.add(btnPrestar);

        String[] columnas = {"ID Préstamo", "Libro", "ID Libro", "Usuario", "Fecha Salida", "Estado"};
        // para que no se pueda editar
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tablaPrestamos = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tablaPrestamos);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnDevolver = new JButton("Registrar Devolución");
        btnRegresar = new JButton("Regresar");

        panelPrincipal.add(panelFormulario, BorderLayout.NORTH);

        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);

        panelBotones.add(btnDevolver);
        panelBotones.add(btnRegresar);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
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