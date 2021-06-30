package com.uahcu.parkinginteligente;

public class Parking {
    private int id_parking;
    private String ciudad;
    private String direccion;
    private Double latitud;
    private Double longitud;

    public Parking(int id_parking, String ciudad, String direccion, Double latitud, Double longitud) {
        this.id_parking = id_parking;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getId_parking() {
        return id_parking;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }
}
