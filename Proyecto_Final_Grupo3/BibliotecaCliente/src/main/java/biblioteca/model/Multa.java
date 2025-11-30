package biblioteca.model;

import java.io.Serializable;
import java.util.Date;

public class Multa implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private int idUsuario;
    private String nombreUsuario;
    private int idPrestamo;
    private String tituloLibro;
    private double monto;
    private Date fechaGeneracion;
    private String estado;

    public Multa(){}

    public Multa(int id, int idUsuario, String nombreUsuario, int idPrestamo, String tituloLibro, double monto, Date fechaGeneracion, String estado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.idPrestamo = idPrestamo;
        this.tituloLibro = tituloLibro;
        this.monto = monto;
        this.fechaGeneracion = fechaGeneracion;
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

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getTituloLibro() {
        return tituloLibro;
    }

    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(Date fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
