package org.example;

public class Medico {

    private String codigo;
    private String nombre;
    private Especialidad especialidad;
    private String telefono;
    private String email;

    public Medico(String codigo, String nombre, Especialidad especialidad, String telefono,String email ) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.codigo = codigo;
    }

    @Override
    public String toString(){
        return "Medico ("+ codigo + ", " +nombre + ", " + especialidad + ", " + telefono + ", " + email + ")";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }



}




