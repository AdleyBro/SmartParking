package db;

public class Parking {

    private int id_parking;
    private String ciudad;
    private String direccion;
    private Double latitud;
    private Double longitud;

    public Parking() {
        this.id_parking = 0;
        this.ciudad = "";
        this.direccion = "";
        this.latitud = 0.0;
        this.longitud = 0.0;
    }

    public Parking(int id_parking, String ciudad, String direccion, Double latitud, Double longitud) {
        this.id_parking = id_parking;
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;

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

    public double getLatitud() {
        return this.latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return this.longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

}