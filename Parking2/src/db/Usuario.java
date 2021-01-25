package db;

import java.sql.Timestamp;

public class Usuario{

    
    public String nombre_usuario;
    public String password;
    public Timestamp ultimo_acceso_parking; 

    public Usuario(String nombre_usuario, String password, Timestamp ultimo_acceso_parking) {
        this.nombre_usuario = nombre_usuario;
        this.password = password;
        this.ultimo_acceso_parking = ultimo_acceso_parking;
    }

    public String getNombre_usuario() {
        return this.nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getUltimo_acceso_parking() {
        return this.ultimo_acceso_parking;
    }

    public void setUltimo_acceso_parking(Timestamp ultimo_acceso_parking) {
        this.ultimo_acceso_parking = ultimo_acceso_parking;
    }


}