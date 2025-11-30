package biblioteca.model;

import java.io.Serializable;
import java.util.Date;

public class Prestamo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int idUsuario;
    private String nombreUsuario;
    private int idLibro;
    private String tituloLibro;
    private Date fechaSalida;
    private Date fechaDevolucion;
    private String estado;

    public Prestamo(){
    }

    public Prestamo(int id, int idUsuario, String nombreUsuario, int idLibro, String tituloLibro, Date fechaSalida, Date fechaDevolucion, String estado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.idLibro = idLibro;
        this.tituloLibro = tituloLibro;
        this.fechaSalida = fechaSalida;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
    }

    @Override
    public String toString(){
        return "Prestamo del libro: " + tituloLibro + " a " + nombreUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
