package biblioteca.controller.admin;

import biblioteca.dao.LibroDAO;
import biblioteca.model.Libro;
import biblioteca.view.admin.GestionLibrosView;

import javax.swing.*;
import java.util.List;

public class GestionLibrosController {

    private final GestionLibrosView vista;
    private final LibroDAO LibroDAO;

    public GestionLibrosController (GestionLibrosView vista){

        this.vista = vista;
        this.LibroDAO = new LibroDAO();

        this.vista.setAgregarListener( e -> registrarLibro());
        this.vista.setEditarListener(System.out::println);
        this.vista.setEliminarListener(System.out::println);
        this.vista.setLimpiarListener(e -> vista.limpiarFormulario());

        cargarTabla();

        this.vista.setVisible(true);
    }

    private void cargarTabla(){

        vista.getModeloTabla().setRowCount(0);

        List<Libro> libros = LibroDAO.listarLibros();

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

        if (LibroDAO.registrarLibro(nl)) {

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
}
