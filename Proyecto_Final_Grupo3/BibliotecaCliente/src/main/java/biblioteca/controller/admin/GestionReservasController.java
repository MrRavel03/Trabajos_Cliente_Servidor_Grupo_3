package biblioteca.controller.admin;

import biblioteca.cliente.ClienteTCP;
import biblioteca.model.Reserva;
import biblioteca.view.admin.GestionReservasView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class GestionReservasController {

    private final GestionReservasView vista;
    public GestionReservasController(GestionReservasView vista) {
        this.vista = vista;

        cargarTabla();

        this.vista.setEntregarListener(e -> entregarLibro());
        this.vista.setCancelarListener(e -> cancelarReserva());
        this.vista.setRegresarListener(e -> vista.dispose());

        this.vista.setVisible(true);
    }

    private void cargarTabla() {
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        List<Reserva> lista = ClienteTCP.getInstance().listarReservasActivas();

        for (Reserva r : lista) {
            modelo.addRow(new Object[]{
                    r.getId(),
                    r.getNombreUsuario(),
                    r.getIdLibro(),
                    r.getTituloLibro(),
                    r.getFechaReserva(),
                    r.getIdUsuario()
            });
        }
    }

    private void entregarLibro() {
        int fila = vista.getTablaReservas().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una reserva para entregar el libro.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idReserva = (int) vista.getModeloTabla().getValueAt(fila, 0);
        int idLibro = (int) vista.getModeloTabla().getValueAt(fila, 2);
        int idUsuario = (int) vista.getModeloTabla().getValueAt(fila, 5);

        boolean reservaLiberada = ClienteTCP.getInstance().cancelarReserva(idReserva);

        if (reservaLiberada) {

            if (ClienteTCP.getInstance().registrarPrestamo(idUsuario, idLibro)) {

                JOptionPane.showMessageDialog(vista, "Préstamo registrado exitosamente.");
                cargarTabla();
            } else {

                JOptionPane.showMessageDialog(vista, "Error crítico: Se liberó la reserva pero falló el préstamo.\nRevise el estado del libro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {

            JOptionPane.showMessageDialog(vista, "Error al procesar la reserva.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void cancelarReserva() {

        int fila = vista.getTablaReservas().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione una reserva.");
            return;
        }

        int idReserva = (int) vista.getModeloTabla().getValueAt(fila, 0);

        if (ClienteTCP.getInstance().cancelarReserva(idReserva)) {
            JOptionPane.showMessageDialog(vista, "Reserva cancelada.");
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al cancelar reserva.");
        }
    }
}
