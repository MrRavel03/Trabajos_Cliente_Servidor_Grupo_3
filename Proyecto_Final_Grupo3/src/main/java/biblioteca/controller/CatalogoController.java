package biblioteca.controller;

import biblioteca.dao.LibroDAO;
import biblioteca.dao.ReservaDAO;
import biblioteca.model.Libro;
import biblioteca.view.estudiantes.CatalogoLibrosEstudiantesView;

import javax.swing.*;
import java.util.List;

public class CatalogoController {

    private final CatalogoLibrosEstudiantesView vista;
    private final LibroDAO libroDAO;
    private final ReservaDAO reservaDAO;
    private final int idUsuarioActual;

    public CatalogoController(CatalogoLibrosEstudiantesView vista, int idUsuario){

        this.vista = vista;
        this.idUsuarioActual = idUsuario;
        this.libroDAO = new LibroDAO();
        this.reservaDAO = new ReservaDAO();

        List<String> categoriasDisponibles = libroDAO.listarCategoriasDisponibles();
        vista.cargarCategoria(categoriasDisponibles);

        cargarLibros("");

        this.vista.setBusquedaListener(e -> filtrarLibros());

        this.vista.setBusquedaListener(e -> filtrarLibros());

        this.vista.setReservarListener(e -> reservarLibroSeleccionado());

        this.vista.setVisible(true);
    }

    private void filtrarLibros(){

        String texto = vista.getTextoBusqueda();
        String categoria = vista.getCategoriaSeleccionada();

        if (categoria != null && !categoria.equals("Todas")) {
            cargarLibros(categoria);
        } else {
            cargarLibros(texto);
        }
    }

    private void cargarLibros (String busqueda){

        vista.getModeloTabla().setRowCount(0);

        List<Libro> lista;

        if (busqueda == null || busqueda.trim().isEmpty()) {
            lista = libroDAO.listarLibros();
        } else {
            lista = libroDAO.buscarLibros(busqueda);
        }

        for (Libro l : lista) {
            vista.getModeloTabla().addRow(new Object[]{
                    l.getId(),
                    l.getTitulo(),
                    l.getAutor(),
                    l.getCategoria(),
                    l.getEstado()
            });
        }
    }

    private void reservarLibroSeleccionado(){

        int fila = vista.getTablaLibros().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(
                    vista,
                    "Por favor, seleccione un libro para reservar",
                    "Ningun libro selecionado",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int idLibro = (int) vista.getModeloTabla().getValueAt(fila, 0);
        String titulo = (String) vista.getModeloTabla().getValueAt(fila, 1);
        String estado = (String) vista.getModeloTabla().getValueAt(fila, 4);

        boolean resultado = reservaDAO.registrarReserva(idUsuarioActual, idLibro);

        if (resultado) {

            JOptionPane.showMessageDialog(vista,
                    "Reserva realizada \nEl libro '" + titulo + "' te espera.",
                    "Reserva correcta",
                    JOptionPane.INFORMATION_MESSAGE);

            filtrarLibros();
        } else {

            JOptionPane.showMessageDialog(vista,
                    "No se pudo reservar el libro.\n" +
                            "Puede que ya esté prestado, reservado por otro usuario\n",
                    "Error en Reserva",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
