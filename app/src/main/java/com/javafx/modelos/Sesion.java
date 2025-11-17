package com.javafx.modelos;

public class Sesion {
    
    private static Usuario usuarioActual;

    public static void iniciarSesion(Usuario u) {
        usuarioActual = u;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

    public static Usuario getUsuario() {
        return usuarioActual;
    }

    public static boolean hayUsuarioLogueado() {
        return usuarioActual != null;
    }
}
