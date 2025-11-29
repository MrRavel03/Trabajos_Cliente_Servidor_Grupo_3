package biblioteca.controller.estudiantes;

import biblioteca.dao.ReservaDAO;
import biblioteca.model.Reserva;
import biblioteca.view.estudiantes.ReservasEstudianteView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ReservasController {

    private final ReservasEstudianteView vista;
    private final ReservaDAO reservaDAO;
    private final int idUsuario;

    public ReservasController(ReservasEstudianteView vista, int idUsuario){

        this.vista = vista;
        this.idUsuario = idUsuario;
        this.reservaDAO = new ReservaDAO();

        cargarReservas();

        this.vista.setVolverListener(e -> vista.dispose());
        this.vista.setCancelarListener(e -> calcelarReservaSeleccionada());

        this.vista.setVisible(true);
    }

    private void cargarReservas(){

        DefaultTableModel modelo = vista.getModeloTabla();

        modelo.setRowCount(0);

        List<Reserva> lista = reservaDAO.listarReservasPorUsuario(idUsuario);

        for (Reserva r : lista) {
            modelo.addRow(new Object[]{
                    r.getId(),
                    r.getTituloLibro(),
                    r.getFechaReserva(),
                    r.getEstado()
            });
        }
    }

    private void calcelarReservaSeleccionada(){

        int fila =  vista.getTablaReservas().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista,
                    "Selecciona una reserva para cancelar.",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idReserva = (int) vista.getModeloTabla().getValueAt(fila, 0);

        boolean resultado = reservaDAO.cancelarReserva(idReserva);

        if (resultado) {
            JOptionPane.showMessageDialog(vista, "Reserva cancelada exitosamente.");
            cargarReservas();
        } else {
            JOptionPane.showMessageDialog(vista,
                    "No se pudo cancelar la reserva.\nInténtalo más tarde.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
