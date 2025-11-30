package biblioteca.controller.estudiantes;

import biblioteca.dao.MultaDAO;
import biblioteca.model.Multa;
import biblioteca.view.estudiantes.MultasEstudianteView;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MisMultasController {

    private final MultasEstudianteView vista;
    private final MultaDAO multaDAO;
    private final int idUsuario;

    public MisMultasController(MultasEstudianteView vista, int idUsuario) {
        this.vista = vista;
        this.idUsuario = idUsuario;
        this.multaDAO = new MultaDAO();

        cargarMultas();

        this.vista.setVolverListener(e -> vista.dispose());

        this.vista.setVisible(true);
    }

    private void cargarMultas() {
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        List<Multa> lista = multaDAO.listarMultasPendientes(idUsuario);

        for (Multa m : lista) {
            modelo.addRow(new Object[]{
                    m.getId(),
                    m.getTituloLibro(),
                    "₡" + m.getMonto(),
                    m.getFechaGeneracion(),
                    m.getEstado()
            });
        }
    }
}
