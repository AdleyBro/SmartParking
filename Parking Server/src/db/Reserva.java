package db;

public class Reserva{

    private int id_usuario;
    private String fecha_hora_inicio;
    private String fecha_hora_fin;
    private float precio_pagado;
    private String id_plaza_FK;
    private String id_parking_FK;


    public Reserva(int id_usuario, String fecha_hora_inicio, String fecha_hora_fin, float precio_pagado, String id_plaza_FK, String id_parking_FK) {
        this.id_usuario = id_usuario;
        this.fecha_hora_inicio = fecha_hora_inicio;
        this.fecha_hora_fin = fecha_hora_fin;
        this.precio_pagado = precio_pagado;
        this.id_plaza_FK = id_plaza_FK;
        this.id_parking_FK = id_parking_FK;
    }

    public int getId_usuario() {
        return this.id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFecha_hora_inicio() {
        return this.fecha_hora_inicio;
    }

    public void setFecha_hora_inicio(String fecha_hora_inicio) {
        this.fecha_hora_inicio = fecha_hora_inicio;
    }

    public String getFecha_hora_fin() {
        return this.fecha_hora_fin;
    }

    public void setFecha_hora_fin(String fecha_hora_fin) {
        this.fecha_hora_fin = fecha_hora_fin;
    }

    public float getPrecio_pagado() {
        return this.precio_pagado;
    }

    public void setPrecio_pagado(float precio_pagado) {
        this.precio_pagado = precio_pagado;
    }

    public String getId_plaza_FK() {
        return this.id_plaza_FK;
    }

    public void setId_plaza_FK(String id_plaza_FK) {
        this.id_plaza_FK = id_plaza_FK;
    }

    public String getId_parking_FK() {
        return this.id_parking_FK;
    }

    public void setId_parking_FK(String id_parking_FK) {
        this.id_parking_FK = id_parking_FK;
    }

    public Reserva id_usuario(int id_usuario) {
        setId_usuario(id_usuario);
        return this;
    }

    public Reserva fecha_hora_inicio(String fecha_hora_inicio) {
        setFecha_hora_inicio(fecha_hora_inicio);
        return this;
    }

    public Reserva fecha_hora_fin(String fecha_hora_fin) {
        setFecha_hora_fin(fecha_hora_fin);
        return this;
    }

    public Reserva precio_pagado(float precio_pagado) {
        setPrecio_pagado(precio_pagado);
        return this;
    }

    public Reserva id_plaza_FK(String id_plaza_FK) {
        setId_plaza_FK(id_plaza_FK);
        return this;
    }

    public Reserva id_parking_FK(String id_parking_FK) {
        setId_parking_FK(id_parking_FK);
        return this;
    }  

}