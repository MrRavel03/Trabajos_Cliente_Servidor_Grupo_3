package biblioteca.controller.admin;

import biblioteca.dao.UsuarioDAO;
import biblioteca.model.Usuario;
import biblioteca.view.admin.GestionUsuariosView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GestionUsuariosController {

    private final GestionUsuariosView vista;
    private final UsuarioDAO usuarioDAO;

    public GestionUsuariosController (GestionUsuariosView vista){

        this.vista = vista;
        this.usuarioDAO = new UsuarioDAO();

        this.vista.setGuardarListener(e -> guardarUsuario());
        this.vista.setEditarListener(e -> editarUsuario());
        this.vista.setEliminarListener(e -> eliminarUsuario());
        this.vista.setLimpiarListener(e -> vista.limpiarFormulario());

        // para poder editar la tabla directamente
        this.vista.getTablaUsuarios().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                llenarFormularioDesdeTabla();
            }
        });

        // --- 3. CARGA INICIAL ---
        cargarTabla();
        this.vista.setVisible(true);
    }

    private void cargarTabla(){
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        List<Usuario> usuarios = usuarioDAO.listarUsuarios();

        for (Usuario u : usuarios) {
            modelo.addRow(new Object[]{
                    u.getId(),
                    u.getNombre(),
                    u.getEmail(),
                    u.getRol(),
                    u.getEstado()
            });
        }
    }

    private void guardarUsuario() {

        String nombre = vista.getNombre();
        String email = vista.getEmail();
        String password = vista.getPassword();
        String rol = vista.getRolSeleccionado();

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor complete todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario u = new Usuario();
        u.setNombre(nombre);
        u.setEmail(email);
        u.setPassword(password);
        u.setRol(rol);

        if (usuarioDAO.registrarUsuario(u)) {
            JOptionPane.showMessageDialog(vista, "Usuario registrado.");
            vista.limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar.\nVerifique que el correo no esté duplicado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarUsuario() {

        int fila = vista.getTablaUsuarios().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un usuario para eliminar.", "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idUsuario = (int) vista.getModeloTabla().getValueAt(fila, 0);

        if (usuarioDAO.eliminarUsuario(idUsuario)) {
            JOptionPane.showMessageDialog(vista, "Usuario eliminado exitosamente.");
            vista.limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al eliminar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    private void editarUsuario() {

        int fila = vista.getTablaUsuarios().getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Seleccione un usuario de la tabla para editar.", "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idUsuario = (int) vista.getModeloTabla().getValueAt(fila, 0);
        String nombre = vista.getNombre();
        String email = vista.getEmail();
        String rol = vista.getRolSeleccionado();

        if (nombre.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El nombre y correo no pueden estar vacíos.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Usuario u = new Usuario();
        u.setId(idUsuario);
        u.setNombre(nombre);
        u.setEmail(email);
        u.setRol(rol);

        if (usuarioDAO.actualizarUsuario(u)) {
            JOptionPane.showMessageDialog(vista, "Usuario actualizado.");
            vista.limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(vista, "No se pudo actualizar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void llenarFormularioDesdeTabla() {
        int fila = vista.getTablaUsuarios().getSelectedRow();
        if (fila != -1) {

            String nombre = (String) vista.getModeloTabla().getValueAt(fila, 1);
            String email = (String) vista.getModeloTabla().getValueAt(fila, 2);
            String rol = (String) vista.getModeloTabla().getValueAt(fila, 3);

            vista.setNombre(nombre);
            vista.setEmail(email);
            vista.setRol(rol);

        }
    }
}
