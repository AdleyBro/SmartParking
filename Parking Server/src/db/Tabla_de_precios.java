package db;

public class Tabla_de_precios{

    private int id_parking_FK;
    private String hora;
    private float precio; 

    public Tabla_de_precios(int id_parking_FK, String hora, float precio) {
        this.id_parking_FK = id_parking_FK;
        this.hora = hora;
        this.precio = precio;
    }

    public int getId_parking_FK() {
        return this.id_parking_FK;
    }

    public void setId_parking_FK(int id_parking_FK) {
        this.id_parking_FK = id_parking_FK;
    }

    public String getHora() {
        return this.hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public float getPrecio() {
        return this.precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

}