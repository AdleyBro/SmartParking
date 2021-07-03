package com.uahcu.parkinginteligente;

import java.util.ArrayList;

public class UserInfo {
    private static String nombreUsuario;
    private static String nombre;
    private static String email;
    private static String telefono;

    public static void setDataFromArray(ArrayList<String> response) {
        nombreUsuario = response.get(6);
        nombre = response.get(1);
        email = response.get(2);
        telefono = response.get(3);
    }

    public static void setData(String nombreU, String nombre, String email, String telefono) {
        nombreUsuario = nombreU;
        UserInfo.nombre = nombre;
        UserInfo.email = email;
        UserInfo.telefono = telefono;
    }

    public static String getNombreUsuario() {
        return nombreUsuario;
    }

    public static String getNombre() {
        return nombre;
    }

    public static String getEmail() {
        return email;
    }

    public static String getTelefono() {
        return telefono;
    }

}
