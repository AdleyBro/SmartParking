package db;
import java.lang.Object;

public class Parking{


    private int id_parking;
    private String ciudad;
    private String direccion;
    //private double latitud;
    //private double longitud;
    private Object coordenadas ; //ver el tipo para coordenadas

    public Parking() {
    }

    public Parking(int id_parking, String ciudad, String direccion, Object coordenadas) {
        this.id_parking = id_parking;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.coordenadas = coordenadas;
    }

    public int getId_parking() {
        return this.id_parking;
    }

    public void setId_parking(int id_parking) {
        this.id_parking = id_parking;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Object getCoordenadas() {
        return this.coordenadas;
    }

    public void setCoordenadas(Object coordenadas) {
        this.coordenadas = coordenadas;
    }

}