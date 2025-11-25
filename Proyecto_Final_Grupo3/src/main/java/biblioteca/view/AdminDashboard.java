package biblioteca.vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {
    private JButton btnReservas, btnPrestamos, btnUsuarios, btnLibros, btnCategorias, btnMultas;

    public AdminDashboard() {
        setTitle("Panel Administrativo - Biblioteca");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Panel principal con grid para botones
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 2, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Creacion de botones para cada modulo
        btnReservas = new JButton("Gestion de Reservas");
        btnPrestamos = new JButton("Gestion de Prestamos");
        btnUsuarios = new JButton("Gestion de Usuarios");
        btnLibros = new JButton("Gestion de Libros");
        btnCategorias = new JButton("Gestion de Categorias");
        btnMultas = new JButton("Gestion de Multas");

        // Configurar tamanos y fuentes de botones
        Font botonFont = new Font("Arial", Font.BOLD, 14);
        btnReservas.setFont(botonFont);
        btnPrestamos.setFont(botonFont);
        btnUsuarios.setFont(botonFont);
        btnLibros.setFont(botonFont);
        btnCategorias.setFont(botonFont);
        btnMultas.setFont(botonFont);

        // Action listeners para navegacion entre vistas
        btnReservas.addActionListener(e -> {
            new biblioteca.vistas.GestionReservas().setVisible(true);
            this.dispose();
        });

        btnPrestamos.addActionListener(e -> {
            new biblioteca.vistas.GestionPrestamos().setVisible(true);
            this.dispose();
        });

        btnUsuarios.addActionListener(e -> {
            new biblioteca.vistas.GestionUsuarios().setVisible(true);
            this.dispose();
        });

        btnLibros.addActionListener(e -> {
            new biblioteca.vistas.GestionLibros().setVisible(true);
            this.dispose();
        });

        // Agregar botones al panel
        mainPanel.add(btnReservas);
        mainPanel.add(btnPrestamos);
        mainPanel.add(btnUsuarios);
        mainPanel.add(btnLibros);
        mainPanel.add(btnCategorias);
        mainPanel.add(btnMultas);

        add(mainPanel);
    }
}