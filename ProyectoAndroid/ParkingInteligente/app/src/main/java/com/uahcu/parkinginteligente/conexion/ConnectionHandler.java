package com.uahcu.parkinginteligente.conexion;

import com.google.gson.Gson;
import com.uahcu.parkinginteligente.BookingInfo;
import com.uahcu.parkinginteligente.MainActivity;
import com.uahcu.parkinginteligente.Parking;
import com.uahcu.parkinginteligente.UserInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class ConnectionHandler {
    private static ArrayList<String> response = new ArrayList<>();
    private static ArrayList<Parking> parkingList;
    private static CountDownLatch latch = new CountDownLatch(1);
    private static String web = "http://192.168.1.129:8080";

    public static void registerRequest(final String name, final String username, final String phone,
                                       final String email, final String pass) {
        MainActivity.executorService.execute(new Runnable() {
            @Override
            public void run() {
                doRegisterRequest(name, username, phone, email, pass);
            }
        });
    }

    public static void loginRequest(final String username, final String pass) {
        MainActivity.executorService.execute(new Runnable() {
            @Override
            public void run() {
                doLoginRequest(username, pass);
            }
        });
    }

    public static void userDataRequest(final String username) {
        MainActivity.executorService.execute(new Runnable() {
            @Override
            public void run() {
                doUserDataRequest(username);
            }
        });
    }

    public static void closestBookingRequest(final String clientId) {
        MainActivity.executorService.execute(new Runnable() {
            @Override
            public void run() {
                doClosestBookingRequest(clientId);
            }
        });
    }

    public static void parkingMapRequest() {
        MainActivity.executorService.execute(new Runnable() {
            @Override
            public void run() {
                doParkingMapRequest();
            }
        });
    }

    public static void bookParkingSlotRequest() {
        MainActivity.executorService.execute(new Runnable() {
            @Override
            public void run() {
                doBookParkingSlotRequest();
            }
        });
    }

    private static void doRegisterRequest(String name, String username, String phone, String email, String pass) {
        try {
            String urlS= web + "/Parking Server/Registro?nombreU=" + username + "&email=" + email
                    + "&pass=" + pass + "&tlf=" + phone + "&nombre=" + name;
            URL url = new URL(urlS);
            HttpURLConnection connection = createConnection(url, "POST");
            connection.connect();
            InputStream in = connection.getInputStream();
            response.add(0, convertStreamToString(in));
            latch.countDown();
            connection.disconnect();
        }
        catch (IOException e) {
            e.printStackTrace();
            latch.countDown();
        }
    }

    private static void doLoginRequest(String username, String pass) {
        try {
            String urlS = web + "/Parking Server/InicioSesion?nombreU=" + username + "&pass=" + pass;
            URL url = new URL(urlS);
            HttpURLConnection connection = createConnection(url, "POST");
            connection.connect();
            InputStream in = connection.getInputStream();
            response.add(0, convertStreamToString(in));
            latch.countDown();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            latch.countDown();
        }
    }

    private static void doUserDataRequest(String username) {
        try {
            String urlS = web + "/Parking Server/GetDatosCliente?nombreU=" + username;
            URL url = new URL(urlS);
            HttpURLConnection connection = createConnection(url, "POST");
            connection.connect();
            InputStream in = connection.getInputStream();
            response = convertStreamToArrayList(in);
            latch.countDown();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            latch.countDown();
        }
    }

    private static void doClosestBookingRequest(String clientId) {
        try {
            String urlS = web + "/Parking Server/GetReservaProxima?idCliente=" + clientId;
            URL url = new URL(urlS);
            HttpURLConnection connection = createConnection(url, "POST");
            connection.connect();
            InputStream in = connection.getInputStream();
            response = convertStreamToArrayList(in);
            latch.countDown();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            latch.countDown();
        }
    }

    private static void doParkingMapRequest() {
        try {
            String urlS = web + "/Parking Server/GetMapaParking";
            URL url = new URL(urlS);
            HttpURLConnection connection = createConnection(url, "POST");
            connection.connect();
            InputStream in = connection.getInputStream();
            parkingList = convertToParkingMap(in);
            latch.countDown();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            latch.countDown();
        }
    }

    private static void doBookParkingSlotRequest() {
        try {
            String urlS = web + "/Parking Server/ReservarPlaza?idpark=" + BookingInfo.parking.getId_parking() +
                    "nombreU=" + UserInfo.getNombreUsuario() + "fechaI=" + BookingInfo.fechaHoraIni + "fechaF=" + BookingInfo.fechaHoraFin;
            URL url = new URL(urlS);
            HttpURLConnection connection = createConnection(url, "POST");
            connection.connect();
            InputStream in = connection.getInputStream();
            response = convertStreamToArrayList(in);
            latch.countDown();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            latch.countDown();
        }
    }

    private static HttpURLConnection createConnection(URL url, String method) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(3000);
        connection.setConnectTimeout(6000);
        connection.setRequestMethod(method);
        connection.setDoInput(true);
        connection.setDoOutput(true);
        return connection;
    }

    private static ArrayList<Parking> convertToParkingMap(InputStream is) {
        String input = convertStreamToString(is);
        String[] parkings = input.split("_");
        ArrayList<Parking> parkingListt = new ArrayList<>();
        for (String parking : parkings) {
            String[] atributos = parking.split("#");
            Parking parkingObj = new Parking(Integer.parseInt(atributos[0]), atributos[1], atributos[2],
                            Double.parseDouble(atributos[3]), Double.parseDouble(atributos[4]));
            parkingListt.add(parkingObj);
        }

        return parkingListt;
    }

    private static ArrayList<String> convertStreamToArrayList(InputStream is) {
        String input = convertStreamToString(is);
        String[] inputArray = input.split("#");
        return new ArrayList<>(Arrays.asList(inputArray));
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static ArrayList<String> getFullResponse() {
        return response;
    }

    public static String getSimpleResponse() {
        return response.get(0);
    }

    public static ArrayList<Parking> getParkingList() {
        return parkingList;
    }

    public static void waitForResponse() throws InterruptedException {
        latch.await();
        latch = new CountDownLatch(1);
    }
}
