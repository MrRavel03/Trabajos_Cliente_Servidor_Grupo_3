package biblioteca.view;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipalEstudianteView extends JFrame {

    private JButton botonNotificaciones;
    private JButton botonCatalogo;
    private JButton botonReservas;
    private JButton botonPrestamos;
    private JButton botonMultas;

    public MenuPrincipalEstudianteView() {
        configurarVentana();
        inicializarComponentes();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Menú Principal");
        setSize(1000, 650);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(33, 33, 33));
    }

    private void inicializarComponentes() {
        //Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(33, 33, 33));
        header.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titulo = new JLabel("Biblioteca XYZ");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);

        //Panel al lado derecho del Header
        JPanel panelUsuario = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 5));
        panelUsuario.setBackground(new Color(33, 33, 33));

        //Boton de Notificaciones
        ImageIcon iconNotif = new ImageIcon("src/main/resources/campana-notificaciones.png");
        Image imgNotif = iconNotif.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        botonNotificaciones = new JButton(new ImageIcon(imgNotif));

        botonNotificaciones.setBackground(new Color(33,33,33));
        botonNotificaciones.setBorder(null);
        botonNotificaciones.setFocusPainted(false);
        botonNotificaciones.setContentAreaFilled(false);

        //Nombre del Usuario
        JLabel nombreUsuario = new JLabel("usuario1");
        nombreUsuario.setForeground(Color.WHITE);
        nombreUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        //Foto de perfil
        JLabel perfil = new JLabel();
        perfil.setPreferredSize(new Dimension(35, 35));
        perfil.setOpaque(true);
        perfil.setBackground(Color.LIGHT_GRAY);
        perfil.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        panelUsuario.add(botonNotificaciones);
        panelUsuario.add(nombreUsuario);
        panelUsuario.add(perfil);

        header.add(titulo, BorderLayout.WEST);
        header.add(panelUsuario, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);


        //Imagen principal
        JLabel imagenCentral = new JLabel();
        imagenCentral.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            ImageIcon icon = new ImageIcon("src/main/resources/estanteria.jpg");
            Image img = icon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
            imagenCentral.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imagenCentral.setText("No se encontró la imagen central.");
            imagenCentral.setForeground(Color.WHITE);
            imagenCentral.setFont(new Font("Segoe UI", Font.BOLD, 26));
        }
        add(imagenCentral, BorderLayout.CENTER);


        //Panel para menu lateral
        JPanel panelMenu = new JPanel();
        panelMenu.setBackground(new Color(55, 55, 55));
        panelMenu.setPreferredSize(new Dimension(230, 0));
        panelMenu.setLayout(new GridLayout(4, 1, 0, 20));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(60, 20, 60, 20));


        //Crear botones del menu
        botonCatalogo = crearBotonMenu("Catálogo de Libros");
        botonReservas = crearBotonMenu("Mis Reservas");
        botonPrestamos = crearBotonMenu("Mis Préstamos");
        botonMultas = crearBotonMenu("Mis Multas");

        panelMenu.add(botonCatalogo);
        panelMenu.add(botonReservas);
        panelMenu.add(botonPrestamos);
        panelMenu.add(botonMultas);

        add(panelMenu, BorderLayout.EAST);
    }

    //Metodo para crear botones
    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(55, 55, 55));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        boton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        return boton;
    }

    //Getters
    public JButton getBotonNotificaciones() { return botonNotificaciones; }
    public JButton getBotonCatalogo() { return botonCatalogo; }
    public JButton getBotonReservas() { return botonReservas; }
    public JButton getBotonPrestamos() { return botonPrestamos; }
    public JButton getBotonMultas() { return botonMultas; }
}
