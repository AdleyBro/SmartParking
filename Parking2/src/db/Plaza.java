package db;

public class Plaza{

    private int id_parking;
    private int id_plaza;
    private boolean esta_ocupado;
    private boolean es_reservable;

    public Plaza() {
    }

    public Plaza(int id_parking, int id_plaza, boolean esta_ocupado, boolean es_reservable) {
        this.id_parking = id_parking;
        this.id_plaza = id_plaza;
        this.esta_ocupado = esta_ocupado;
        this.es_reservable = es_reservable;
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

    public boolean isEsta_ocupado() {
        return this.esta_ocupado;
    }

    public boolean getEsta_ocupado() {
        return this.esta_ocupado;
    }

    public void setEsta_ocupado(boolean esta_ocupado) {
        this.esta_ocupado = esta_ocupado;
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

    public Plaza id_parking(int id_parking) {
        setId_parking(id_parking);
        return this;
    }

    public Plaza id_plaza(int id_plaza) {
        setId_plaza(id_plaza);
        return this;
    }

    public Plaza esta_ocupado(boolean esta_ocupado) {
        setEsta_ocupado(esta_ocupado);
        return this;
    }

    public Plaza es_reservable(boolean es_reservable) {
        setEs_reservable(es_reservable);
        return this;
    }

}