package biblioteca.controller.admin;

import biblioteca.cliente.ClienteTCP;
import biblioteca.model.Libro;
import biblioteca.view.admin.GestionLibrosView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GestionLibrosController {

    private final GestionLibrosView vista;

    public GestionLibrosController (GestionLibrosView vista){

        this.vista = vista;

        this.vista.setAgregarListener( e -> registrarLibro());
        this.vista.setEditarListener(e -> editarLibro());
        this.vista.setEliminarListener(e -> eliminarLibro());
        this.vista.setLimpiarListener(e -> vista.limpiarFormulario());
        this.vista.setRegresarListener(e -> vista.dispose());

        this.vista.getTablaLibros().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                llenarFormularioDesdeTabla();
            }
        });

        cargarTabla();

        this.vista.setVisible(true);
    }

    private void cargarTabla(){

        vista.getModeloTabla().setRowCount(0);

        List<Libro> libros = ClienteTCP.getInstance().listarLibros();

        for(Libro l : libros){
            Object[] fila = {
                    l.getId(),
                    l.getTitulo(),
                    l.getAutor(),
                    l.getCategoria(),
                    l.isDisponible() ? "Si" : "No",
                    l.getEstado()
            };
            vista.getModeloTabla().addRow(fila);
        }
    }

    private void editarLibro() {
        int fila = vista.getTablaLibros().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un libro para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String titulo = vista.getTitulo();
        String autor = vista.getAutor();
        String categoria = vista.getCategoria();

        if (titulo.isEmpty() || autor.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El título y autor son obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idLibro = (int) vista.getModeloTabla().getValueAt(fila, 0);

        Libro libroEditado = new Libro();
        libroEditado.setId(idLibro);
        libroEditado.setTitulo(titulo);
        libroEditado.setAutor(autor);
        libroEditado.setCategoria(categoria);


        if (ClienteTCP.getInstance().actualizarLibro(libroEditado)) {
             JOptionPane.showMessageDialog(vista, "Libro actualizado.");
             vista.limpiarFormulario();
             cargarTabla();
        } else {
             JOptionPane.showMessageDialog(vista, "Error al actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        JOptionPane.showMessageDialog(vista, "Función editar pendiente de implementar en Servidor/DAO.");
    }

    private void registrarLibro(){

        String titulo = vista.getTitulo();
        String autor = vista.getAutor();
        String categoria = vista.getCategoria();

        if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                    "Por favor complete todos los campos (Título, Autor, Categoría).",
                    "Campos vacíos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Libro nl = new Libro();

        nl.setTitulo(titulo);
        nl.setAutor(autor);
        nl.setCategoria(categoria);

        if (ClienteTCP.getInstance().registrarLibro(nl)) {

            JOptionPane.showMessageDialog(vista, "Libro registrado exitosamente.");
            vista.limpiarFormulario();
            cargarTabla();

        } else {
            JOptionPane.showMessageDialog(
                    vista,
                    "Error al guardar el libro en la base de datos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void eliminarLibro() {
        int fila = vista.getTablaLibros().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un libro para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idLibro = (int) vista.getModeloTabla().getValueAt(fila, 0);
        String titulo = (String) vista.getModeloTabla().getValueAt(fila, 1);

        int confirm = JOptionPane.showConfirmDialog(vista,
                "¿Seguro que desea eliminar el libro: '" + titulo + "'?\nSe cancelarán las reservas asociadas.",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (ClienteTCP.getInstance().eliminarLibro(idLibro)) {
                 JOptionPane.showMessageDialog(vista, "Libro eliminado correctamente.");
                 cargarTabla();
            } else {
                 JOptionPane.showMessageDialog(vista, "Error al eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            JOptionPane.showMessageDialog(vista, "Función eliminar pendiente de implementar en Servidor/DAO.");
        }
    }

    private void llenarFormularioDesdeTabla() {
        int fila = vista.getTablaLibros().getSelectedRow();
        if (fila != -1) {
            DefaultTableModel m = vista.getModeloTabla();

             vista.setTitulo((String) m.getValueAt(fila, 1));
             vista.setAutor((String) m.getValueAt(fila, 2));
             vista.setCategoria((String) m.getValueAt(fila, 3));
        }
    }
}
