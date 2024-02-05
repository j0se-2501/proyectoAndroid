package com.example.proyectotocho;

public class Producto {

    private int id;
    private String nombre;
    private String descripcion;
    private String precio;
    private int stock;
    private String imagenUrl;

    // Constructor
    public Producto(int id, String nombre, String descripcion, String precio, int stock, String imagenUrl) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagenUrl = imagenUrl;
    }

    // Getters

    public int getId() {return id;}
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }
}

