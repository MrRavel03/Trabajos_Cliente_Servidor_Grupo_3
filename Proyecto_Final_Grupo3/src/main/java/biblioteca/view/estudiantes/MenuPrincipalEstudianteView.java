package biblioteca.view.estudiantes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPrincipalEstudianteView extends JFrame {

    private JLabel nombreUsuario;
    private JLabel perfil;
    private JButton botonNotificaciones;
    private JButton botonCatalogo;
    private JButton botonReservas;
    private JButton botonPrestamos;
    private JButton botonMultas;
    private JButton botonCerrarSesion;

    public MenuPrincipalEstudianteView() {

        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Menú Principal");
        setSize(1000, 650);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(240, 242, 245));
    }

    private void inicializarComponentes() {
        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));
        header.setPreferredSize(new Dimension(1000, 60));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Biblioteca Digital");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(new Color(52, 152, 219));

        //Panel al lado derecho del Header
        JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 5));
        panelUsuario.setBackground(Color.WHITE);

        //Boton de Notificaciones
        ImageIcon iconNotif = new ImageIcon("src/main/resources/campana-notificaciones.png");
        if (iconNotif.getIconWidth() > 0) {
            Image imgNotif = iconNotif.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            botonNotificaciones = new JButton(new ImageIcon(imgNotif));
        } else {
            botonNotificaciones = new JButton("Notif");
        }


        botonNotificaciones.setBorderPainted(false);
        botonNotificaciones.setContentAreaFilled(false);
        botonNotificaciones.setFocusPainted(false);
        botonNotificaciones.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //Nombre del Usuario
        nombreUsuario = new JLabel("Estudiante");
        nombreUsuario.setForeground(new Color(80, 80, 80));
        nombreUsuario.setFont(new Font("Segoe UI", Font.BOLD, 16));

        //Foto de perfil
        perfil = new JLabel();
        perfil.setPreferredSize(new Dimension(35, 35));
        perfil.setOpaque(true);
        perfil.setBackground(new Color(220, 220, 220));
        perfil.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        panelUsuario.add(botonNotificaciones);
        panelUsuario.add(nombreUsuario);
        panelUsuario.add(perfil);

        header.add(titulo, BorderLayout.WEST);
        header.add(panelUsuario, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        //Panel para menu lateral
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(Color.WHITE);
        panelMenu.setPreferredSize(new Dimension(250, 0));
        panelMenu.setLayout(new GridLayout(6, 1, 0, 10));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

        //Crear botones del menu
        botonCatalogo = crearBotonMenu("Catálogo de Libros");
        botonReservas = crearBotonMenu("Mis Reservas");
        botonPrestamos = crearBotonMenu("Mis Préstamos");
        botonMultas = crearBotonMenu("Mis Multas");

        botonCerrarSesion = crearBotonMenu("Cerrar Sesión");
        botonCerrarSesion.setBackground(new Color(255, 235, 235));
        botonCerrarSesion.setForeground(new Color(231, 76, 60));

        panelMenu.add(botonCatalogo);
        panelMenu.add(botonReservas);
        panelMenu.add(botonPrestamos);
        panelMenu.add(botonMultas);
        panelMenu.add(Box.createVerticalGlue());
        panelMenu.add(botonCerrarSesion);

        add(panelMenu, BorderLayout.WEST);

        //Imagen central
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(new Color(240, 242, 245));
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel imagenCentral = new JLabel();
        imagenCentral.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            ImageIcon icon = new ImageIcon("src/main/resources/estanteria.jpg");
            Image img = icon.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH);
            imagenCentral.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imagenCentral.setText("Bienvenido a la Biblioteca");
            imagenCentral.setFont(new Font("Segoe UI", Font.BOLD, 20));
            imagenCentral.setForeground(Color.GRAY);
        }

        panelCentral.add(imagenCentral, BorderLayout.CENTER);
        add(imagenCentral, BorderLayout.CENTER);
    }

    //Metodo para crear botones
    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        boton.setForeground(new Color(80, 80, 80));
        boton.setBackground(new Color(245, 245, 245));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    // Setters
    public void setNombreUsuario(String nombre) {
        nombreUsuario.setText(nombre);
    }

    // listeners
    public void setIrCatalogoListener(ActionListener l) {
        botonCatalogo.addActionListener(l);
    }

    public void setIrReservasListener(ActionListener l) {
        botonReservas.addActionListener(l);
    }

    public void setIrPrestamosListener(ActionListener l) {
        botonPrestamos.addActionListener(l);
    }

    public void setIrMultasListener(ActionListener l) {
        botonMultas.addActionListener(l);
    }

    public void setCerrarSesionListener(ActionListener l) {
        botonCerrarSesion.addActionListener(l);
    }

    public void setNotificacionesListener(ActionListener l) {
        botonNotificaciones.addActionListener(l);
    }
}
