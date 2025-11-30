package biblioteca.controller.admin;

import biblioteca.dao.MultaDAO;
import biblioteca.model.Multa;
import biblioteca.view.admin.GestionMultasView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class GestionMultasController {

    private final GestionMultasView vista;
    private final MultaDAO multaDAO;

    public GestionMultasController(GestionMultasView vista){

        this.vista = vista;
        this.multaDAO = new MultaDAO();

        cargarTabla();

        this.vista.setRegistrarPagoListener(e -> registrarPago());
        this.vista.setCerrarListener(e -> vista.dispose());

        this.vista.setVisible(true);
    }

    private void cargarTabla() {
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        List<Multa> lista = multaDAO.listarTodasLasMultas();

        for (Multa m : lista) {
            modelo.addRow(new Object[]{
                    m.getId(),
                    m.getNombreUsuario(),
                    m.getTituloLibro(),
                    "₡" + m.getMonto(),
                    m.getFechaGeneracion(),
                    m.getEstado()
            });
        }
    }

    private void registrarPago() {
        int fila = vista.getTablaMultas().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una multa para registrar el pago.");
            return;
        }

        String estadoActual = (String) vista.getModeloTabla().getValueAt(fila, 5);
        if ("PAGADA".equalsIgnoreCase(estadoActual)) {
            JOptionPane.showMessageDialog(vista, "Esta multa ya fue pagada anteriormente.");
            return;
        }

        int idMulta = (int) vista.getModeloTabla().getValueAt(fila, 0);

        if (multaDAO.registrarPagoMulta(idMulta)) {
            JOptionPane.showMessageDialog(vista, "Pago registrado. La multa ha sido cancelada.");
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar el pago.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
}
