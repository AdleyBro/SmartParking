package db;

public class Historial_plazas{

    private int id_parking;
    private int id_plaza;
    private String fecha_hora;
    private boolean es_reservable;
    private String fecha_hora_salida;


    public Historial_plazas(int id_parking, int id_plaza, String fecha_hora, boolean es_reservable, String fecha_hora_salida) {
        this.id_parking = id_parking;
        this.id_plaza = id_plaza;
        this.fecha_hora = fecha_hora;
        this.es_reservable = es_reservable;
        this.fecha_hora_salida = fecha_hora_salida;
    }

    public int getId_parking() {
        return this.id_parking;
    }

    public void setId_parking(int id_parking) {
        this.id_parking = id_parking;
    }

    public int getId_plaza() {
        return this.id_plaza;
    }

    public void setId_plaza(int id_plaza) {
        this.id_plaza = id_plaza;
    }

    public String getFecha_hora() {
        return this.fecha_hora;
    }

    public void setFecha_hora(String fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public boolean isEs_reservable() {
        return this.es_reservable;
    }

    public boolean getEs_reservable() {
        return this.es_reservable;
    }

    public void setEs_reservable(boolean es_reservable) {
        this.es_reservable = es_reservable;
    }

    public String getFecha_hora_salida() {
        return this.fecha_hora_salida;
    }

    public void setFecha_hora_salida(String fecha_hora_salida) {
        this.fecha_hora_salida = fecha_hora_salida;
    }

    public Historial_plazas id_parking(int id_parking) {
        setId_parking(id_parking);
        return this;
    }

    public Historial_plazas id_plaza(int id_plaza) {
        setId_plaza(id_plaza);
        return this;
    }

    public Historial_plazas fecha_hora(String fecha_hora) {
        setFecha_hora(fecha_hora);
        return this;
    }

    public Historial_plazas es_reservable(boolean es_reservable) {
        setEs_reservable(es_reservable);
        return this;
    }

    public Historial_plazas fecha_hora_salida(String fecha_hora_salida) {
        setFecha_hora_salida(fecha_hora_salida);
        return this;
    }

}