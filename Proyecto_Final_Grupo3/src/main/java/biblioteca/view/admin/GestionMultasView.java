package biblioteca.view.admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class GestionMultasView extends JFrame {

    private JTable tablaMultas;
    private DefaultTableModel modeloTabla;
    private JButton btnRegistrarPago;
    private JButton btnCerrar;

    public GestionMultasView() {
        setTitle("Gestión de Multas y Pagos");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarComponentes();
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- TÍTULO ---
        JLabel titulo = new JLabel("Control de Multas", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(33, 33, 33));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // --- TABLA ---
        String[] columnas = {"ID", "Usuario", "Libro", "Monto", "Fecha", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tablaMultas = new JTable(modeloTabla);
        tablaMultas.setRowHeight(30);
        tablaMultas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaMultas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Estilo
        tablaMultas.setGridColor(new Color(230, 230, 230));
        tablaMultas.setShowGrid(true);
        tablaMultas.setBackground(Color.WHITE);
        tablaMultas.setSelectionBackground(new Color(231, 76, 60)); 
        tablaMultas.setSelectionForeground(Color.WHITE);
        tablaMultas.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaMultas.getTableHeader().setBackground(new Color(245, 247, 250));

        JScrollPane scroll = new JScrollPane(tablaMultas);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

        panelPrincipal.add(scroll, BorderLayout.CENTER);

        // --- BOTONES ---
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelSur.setBackground(Color.WHITE);

        btnRegistrarPago = new JButton("Registrar Pago (Pagada)");
        estilizarBoton(btnRegistrarPago, new Color(46, 204, 113));

        btnCerrar = new JButton("Cerrar");
        estilizarBoton(btnCerrar, new Color(149, 165, 166));

        panelSur.add(btnRegistrarPago);
        panelSur.add(btnCerrar);

        panelPrincipal.add(panelSur, BorderLayout.SOUTH);
        setContentPane(panelPrincipal);
    }

    private void estilizarBoton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(280, 45));
    }

    // Getters & Listeners
    public JTable getTablaMultas() { return tablaMultas; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }

    public void setRegistrarPagoListener(ActionListener l) { btnRegistrarPago.addActionListener(l); }
    public void setCerrarListener(ActionListener l) { btnCerrar.addActionListener(l); }
}