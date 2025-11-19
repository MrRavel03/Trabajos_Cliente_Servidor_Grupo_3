package biblioteca.model;

import org.w3c.dom.ls.LSOutput;

public class Libro {

    private int id;
    private String titulo;
    private String autor;
    private String categoria;
    private boolean disponible;
    private String estado;

    // Constructor vacio
    public Libro(){
    }

    // Constructor completo
    public Libro(int id, String titulo, String autor, String categoria, Boolean disponible, String estado){
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.disponible = disponible;
        this.estado = estado;
    }

    @Override
    public String toString(){
        return titulo + " - " + autor;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
