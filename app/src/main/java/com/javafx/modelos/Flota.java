package com.javafx.modelos;

public class Flota {
    
    private int id_flota;
    private Integer id_nave;
    private int id_usuario;
    private String nombre;
    private String faccion;
    private int cantidad;
    
    public Flota(int id_flota, int id_nave, int id_usuario, String nombre, String faccion, int cantidad) {
        this.id_flota = id_flota;
        this.id_nave = id_nave;
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.faccion = faccion;
        this.cantidad = cantidad;
    }

    public Flota(int id_flota, int id_usuario, String nombre, String faccion, int cantidad) {
        this.id_flota = id_flota;
        this.id_nave = null;
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.faccion = faccion;
        this.cantidad = cantidad;
    }

    public int getId_flota() {
        return id_flota;
    }

    public void setId_flota(int id_flota) {
        this.id_flota = id_flota;
    }

    public int getId_nave() {
        return id_nave;
    }

    public void setId_nave(int id_nave) {
        this.id_nave = id_nave;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFaccion() {
        return faccion;
    }

    public void setFaccion(String faccion) {
        this.faccion = faccion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    

}
