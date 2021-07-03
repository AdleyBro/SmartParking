package db;

public class HistorialSalida{

    private int id_parking;
    private String nombre_usuario;
    private String fecha_hora_salida;


    public HistorialSalida(String fecha_hora_salida,String nombre_usuario,int id_parking) {
        this.id_parking = id_parking;
        this.nombre_usuario = nombre_usuario;
        this.fecha_hora_salida = fecha_hora_salida;
    }

    public int getId_parking() {
        return this.id_parking;
    }

    public void setId_parking(int id_parking) {
        this.id_parking = id_parking;
    }

    public String getNombre_usuario() {
        return this.nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getFecha_hora_salida() {
        return this.fecha_hora_salida;
    }

    public void setFecha_hora_salida(String fecha_hora_salida) {
        this.fecha_hora_salida = fecha_hora_salida;
    }

    

}