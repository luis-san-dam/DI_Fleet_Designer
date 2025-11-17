package com.javafx.modelos;

public class Nave {
    
    private int id_nave;
    private String nombre;
    private String tipo;
    private String propulsion;
    private String sistema_defensivo;
    private String armadura;
    private String escudo;
    private String armamento;
    private String imagen;
    
    public Nave(int id_nave, String nombre, String tipo, String propulsion, String sistema_defensivo, String armadura,
            String escudo, String armamento, String imagen) {
        this.id_nave = id_nave;
        this.nombre = nombre;
        this.tipo = tipo;
        this.propulsion = propulsion;
        this.sistema_defensivo = sistema_defensivo;
        this.armadura = armadura;
        this.escudo = escudo;
        this.armamento = armamento;
        this.imagen = imagen;
    }

    public int getId_nave() {
        return id_nave;
    }

    public void setId_nave(int id_nave) {
        this.id_nave = id_nave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPropulsion() {
        return propulsion;
    }

    public void setPropulsion(String propulsion) {
        this.propulsion = propulsion;
    }

    public String getSistema_defensivo() {
        return sistema_defensivo;
    }

    public void setSistema_defensivo(String sistema_defensivo) {
        this.sistema_defensivo = sistema_defensivo;
    }

    public String getArmadura() {
        return armadura;
    }

    public void setArmadura(String armadura) {
        this.armadura = armadura;
    }

    public String getEscudo() {
        return escudo;
    }

    public void setEscudo(String escudo) {
        this.escudo = escudo;
    }

    public String getArmamento() {
        return armamento;
    }

    public void setArmamento(String armamento) {
        this.armamento = armamento;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    
}
