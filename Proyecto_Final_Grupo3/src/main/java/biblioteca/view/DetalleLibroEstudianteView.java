package biblioteca.view;

import javax.swing.*;
import java.awt.*;

public class DetalleLibroEstudianteView extends JFrame {

    private JLabel portadaLibro;
    private JLabel tituloLibro;
    private JTextArea sinopsisArea;
    private JLabel anioPublicacion;
    private JLabel categoriasLibro;
    private JLabel autoresLibro;
    private JLabel estadoLibro;
    private JButton botonReservar;

    public DetalleLibroEstudianteView() {

        setTitle("Detalle del Libro");
        setSize(720, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        //Panel Izquierdo
        JPanel panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setPreferredSize(new Dimension(200, 480));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(20,20,20,0));

        //Seccion para portada del Libro
        portadaLibro = new JLabel();
        portadaLibro.setHorizontalAlignment(JLabel.CENTER);
        portadaLibro.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelIzquierdo.add(portadaLibro, BorderLayout.CENTER);
        add(panelIzquierdo, BorderLayout.WEST);

        //Panel Derecho
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        //Titulo
        tituloLibro = new JLabel();
        tituloLibro.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panelDerecho.add(tituloLibro);

        //Sinopsis
        sinopsisArea = new JTextArea();
        sinopsisArea.setEditable(false);
        sinopsisArea.setLineWrap(true); //par no hacer scroll horizontal
        sinopsisArea.setWrapStyleWord(true); //evita que se corten las palabras
        sinopsisArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrolldeSinopsis = new JScrollPane(sinopsisArea);
        scrolldeSinopsis.setPreferredSize(new Dimension(400, 100));
        panelDerecho.add(scrolldeSinopsis);

        //Panel Derecho bajo sinopsis de informacion del libro
        JPanel panelInfo = new JPanel(new GridLayout(4, 1, 0, 5));
        anioPublicacion = new JLabel();
        categoriasLibro = new JLabel();
        autoresLibro = new JLabel();
        estadoLibro = new JLabel();
        panelInfo.add(anioPublicacion);
        panelInfo.add(categoriasLibro);
        panelInfo.add(autoresLibro);
        panelInfo.add(estadoLibro);
        panelDerecho.add(panelInfo);

        //Boton de reserva
        botonReservar = new JButton("Reservar Libro");
        botonReservar.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelDerecho.add(botonReservar);

        add(panelDerecho, BorderLayout.CENTER);

        setVisible(true);
    }

    public void setPortadaLibro(ImageIcon imagen) {
        portadaLibro.setIcon(imagen);
    }

    public void setTituloLibro(String titulo) {
        tituloLibro.setText(titulo);
    }

    public void setSinopsis(String sinopsis) {
        sinopsisArea.setText(sinopsis);
    }

    public void setAnioPublicacion(String anio) {
        anioPublicacion.setText("Año: " + anio);
    }

    public void setCategorias(String categorias) {
        categoriasLibro.setText("Categorías: " + categorias);
    }

    public void setAutores(String autores) {
        autoresLibro.setText("Autor(es): " + autores);
    }

    public void setEstado(String estado) {
        estadoLibro.setText("Estado: " + estado);
    }

    public JButton getBotonReservar() {
        return botonReservar;
    }
}
