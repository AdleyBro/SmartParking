package db;

public class HistorialEntrada{

    private int id_parking;
    private String nombre_usuario;
    private String fecha_hora_entrada;


    public HistorialEntrada(String fecha_hora_entrada,String nombre_usuario,int id_parking) {
        this.id_parking = id_parking;
        this.nombre_usuario = nombre_usuario;
        this.fecha_hora_entrada = fecha_hora_entrada;
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

    public String getFecha_hora_entrada() {
        return this.fecha_hora_entrada;
    }

    public void setFecha_hora_entrada(String fecha_hora_entrada) {
        this.fecha_hora_entrada = fecha_hora_entrada;
    }

    

}