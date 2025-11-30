package biblioteca.model;

import java.io.Serializable;
import java.sql.Date;

public class PrestamoInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idUsuario;
    private Date fechaSalida;
    private Date fechaDevolucion;

    public PrestamoInfo(int idUsuario, Date fechaSalida, Date fechaDevolucion) {
        this.idUsuario = idUsuario;
        this.fechaSalida = fechaSalida;
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

}
