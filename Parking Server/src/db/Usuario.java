package db;

import java.sql.Timestamp;

public class Usuario{

    public String nombre;
    public String nombre_usuario;
    public int telefono;
    public String email;
    public Timestamp ultimo_acceso_parking; 

    public Usuario(String nombre, String nombre_usuario, int telefono, String email, Timestamp ultimo_acceso_parking) {
        this.nombre = nombre;
        this.nombre_usuario = nombre_usuario;
        this.telefono = telefono;
        this.email = email;
        this.ultimo_acceso_parking = ultimo_acceso_parking;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre_usuario() {
        return this.nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public int getTelefono() {
        return this.telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Timestamp getUltimo_acceso_parking() {
        return this.ultimo_acceso_parking;
    }

    public void setUltimo_acceso_parking(Timestamp ultimo_acceso_parking) {
        this.ultimo_acceso_parking = ultimo_acceso_parking;
    }


}