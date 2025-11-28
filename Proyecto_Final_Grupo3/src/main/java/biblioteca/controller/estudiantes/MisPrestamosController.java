package biblioteca.controller.estudiantes;

import biblioteca.dao.PrestamoDAO;
import biblioteca.model.Prestamo;
import biblioteca.view.estudiantes.PrestamosEstudianteView;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MisPrestamosController {

    private final PrestamosEstudianteView vista;
    private final PrestamoDAO prestamoDAO;
    private final int idUsuario;

    public MisPrestamosController(PrestamosEstudianteView vista, int idUsuario){

        this.vista = vista;
        this.idUsuario = idUsuario;
        this.prestamoDAO = new PrestamoDAO();

        cargarTabla();

        this.vista.setVolverListener(e -> vista.dispose());

        this.vista.setVisible(true);
    }

    private void cargarTabla(){

        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        List<Prestamo> lista = prestamoDAO.listarHistorialPorUsuario(idUsuario);

        for (Prestamo p : lista){

            Object fechaDev = (p.getFechaDevolucion() == null) ? "Pendiente" : p.getFechaDevolucion();

            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getTituloLibro(),
                    p.getFechaSalida(),
                    fechaDev,
                    p.getEstado()
            });
        }

    }
}
