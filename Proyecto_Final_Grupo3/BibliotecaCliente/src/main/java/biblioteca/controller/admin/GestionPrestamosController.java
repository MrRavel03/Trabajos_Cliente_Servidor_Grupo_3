package biblioteca.controller.admin;

import biblioteca.cliente.ClienteTCP;
import biblioteca.model.Prestamo;
import biblioteca.view.admin.GestionPrestamosView;

import javax.swing.*;
import java.util.List;

public class GestionPrestamosController {

    private final GestionPrestamosView vista;

    public GestionPrestamosController(GestionPrestamosView vista) {
        this.vista = vista;

        this.vista.setPrestarListener(e -> registrarPrestamo());
        this.vista.setDevolverListener(e -> registrarDevolucion());
        this.vista.setRegresarListener(e -> cerrarVista());

        cargarTabla(); // Cargar tabla al abrir

        this.vista.setVisible(true);
    }

    private void cargarTabla(){

        vista.getModeloTabla().setRowCount(0);

        List<Prestamo> lista = ClienteTCP.getInstance().listarPrestamosActivos();

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

            if (ClienteTCP.getInstance().registrarPrestamo(idUsuario, idLibro)){

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
            JOptionPane.showMessageDialog(vista, "Seleccione un préstamo de la tabla.", "Selección Requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int resultado = ClienteTCP.getInstance().registrarDevolucion(idPrestamo, idLibro);

        switch (resultado) {
            case 2: // CASO: Devolución con Multa
                JOptionPane.showMessageDialog(
                        vista,
                        "Devolución con retraso.\nSe ha generado una multa en el sistema.",
                        "Multa Generada",
                        JOptionPane.WARNING_MESSAGE
                );
                cargarTabla();
                break;

            case 1: // CASO: Devolución Limpia
                JOptionPane.showMessageDialog(
                        vista,
                        "Libro devuelto a tiempo.",
                        "Libro devuelto",
                        JOptionPane.INFORMATION_MESSAGE
                );
                cargarTabla();
                break;

            default: // CASO: Error (0)
                JOptionPane.showMessageDialog(
                        vista,
                        "Error al procesar la devolución.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                break;
        }
    }

    private void cerrarVista(){
        vista.dispose(); // Cerramos solo esta ventana
    }
}
