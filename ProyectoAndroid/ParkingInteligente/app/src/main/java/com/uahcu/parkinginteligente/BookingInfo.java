package com.uahcu.parkinginteligente;

public class BookingInfo {

    private static Parking parking;
    private static String idParking;
    private static String fechaHoraIni;
    private static String fechaHoraFin;

    public static void setParking(Parking parking) {
        BookingInfo.parking = parking;
    }

    public static void setFechaHoraIni(String fecha, String hora) {
        fecha = fecha.replace("/", "-");
        if (hora.length() < 6)
            hora += ":00";

        BookingInfo.fechaHoraIni = fecha + " " + hora;
    }

    public static void setFechaHoraFin(String fecha, String hora) {
        fecha = fecha.replace("/", "-");
        if (hora.length() < 6)
            hora += ":00";

        BookingInfo.fechaHoraFin = fecha + " " + hora;
    }

    public static void setIdParking(String idParking) {
        BookingInfo.idParking = idParking;
    }

    public static String getIdParking() {
        return idParking;
    }

    public static Parking getParking() {
        return parking;
    }

    public static String getFechaHoraIni() {
        return fechaHoraIni;
    }

    public static String getFechaHoraFin() {
        return fechaHoraFin;
    }
}
