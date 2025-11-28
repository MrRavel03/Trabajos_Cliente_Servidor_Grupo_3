package biblioteca.controller.admin;


import biblioteca.dao.MultaDAO;
import biblioteca.dao.PrestamoDAO;
import biblioteca.model.Prestamo;
import biblioteca.view.admin.GestionPrestamosView;

import javax.swing.*;
import java.util.List;

public class GestionPrestamosController {

    private final GestionPrestamosView vista;
    private final PrestamoDAO prestamoDAO;
    private final MultaDAO multaDAO;

    public GestionPrestamosController(GestionPrestamosView vista) {
        this.vista = vista;
        this.prestamoDAO = new PrestamoDAO();
        this.multaDAO = new MultaDAO();

        this.vista.setPrestarListener(e -> registrarPrestamo());
        this.vista.setDevolverListener(e -> registrarDevolucion());
        this.vista.setRegresarListener(e -> cerrarVista());

        cargarTabla(); // Cargar tabla al abrir

        this.vista.setVisible(true);
    }

    private void cargarTabla(){

        vista.getModeloTabla().setRowCount(0);

        List<Prestamo> lista = prestamoDAO.listarPrestamosActivos();

        for (Prestamo p : lista){

            vista.getModeloTabla().addRow(new Object[]{

                    p.getId(),
                    p.getTituloLibro(),
                    p.getIdLibro(),
                    p.getNombreUsuario(),
                    p.getFechaSalida(),
                    p.getEstado()
            });
        }
    }

    private void registrarPrestamo(){

        String idUsuarioStr = vista.getIdUsuario();
        String idLibroStr = vista.getIdLibro();

        if (idUsuarioStr.isEmpty() || idLibroStr.isEmpty()){

            JOptionPane.showMessageDialog(
                    vista,
                    "Por favor ingrese el ID del Usuario y del Libro",
                    "Datos faltantes",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try{

            int idUsuario = Integer.parseInt(idUsuarioStr);
            int idLibro = Integer.parseInt(idLibroStr);

            if (prestamoDAO.registrarPrestamo(idUsuario, idLibro)){

                JOptionPane.showMessageDialog(
                        vista,
                        "Prestamo registrado exitosamente");
                vista.limpiarCampos();
                cargarTabla();
            } else{
                JOptionPane.showMessageDialog(
                        vista,
                        "No se pudo realizar el préstamo.\nVerifique que el Usuario exista y el Libro esté disponible.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(
                    vista,
                    "Los IDs deben ser números enteros.",
                    "Error de formato",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void registrarDevolucion(){

        int idPrestamo = vista.getIdPrestamoSeleccionado();
        int idLibro = vista.getIdLibroSeleccionado();

        if (idPrestamo == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un préstamo de la tabla para devolver.", "Selección Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (prestamoDAO.registrarDevolucion(idPrestamo,idLibro)){

            boolean hayMulta = multaDAO.procesarPosibleMulta(idPrestamo);

            if (hayMulta){
                JOptionPane.showMessageDialog(
                        vista,
                        "Devolucion con retraso.\n" +
                                "Se ha generado una multa en el sistema.",
                        "Multa Generada",
                        JOptionPane.WARNING_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        vista,
                        "Libro devuelto a tiempo",
                        "Libro devuelto",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(
                    vista,
                    "Error al procesar la devolucion.",
                    "Error",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void cerrarVista(){
        vista.dispose(); // Cerramos solo esta ventana
    }
}
