package biblioteca.model;

import java.io.Serializable;
import java.util.Date;

public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int idUsuario;
    private String nombreUsuario;
    private int idLibro;
    private String tituloLibro;
    private Date fechaReserva;
    private String estado;

    public Reserva(){}

    public Reserva(int id, int idUsuario, String nombreUsuario, int idLibro, String tituloLibro, Date fechaReserva, String estado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.idLibro = idLibro;
        this.tituloLibro = tituloLibro;
        this.fechaReserva = fechaReserva;
        this.estado = estado;
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

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
