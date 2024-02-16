package com.example.proyectotocho;

public class Pedido {

    private int id;

    private int idUsuario;

    private String fecha;

    private String direccion;

    private float precio;

    public Pedido(int id, int idUsuario, String fecha, String direccion, float precio) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.direccion = direccion;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", fecha='" + fecha + '\'' +
                ", direccion='" + direccion + '\'' +
                ", precio=" + precio +
                '}';
    }
}
