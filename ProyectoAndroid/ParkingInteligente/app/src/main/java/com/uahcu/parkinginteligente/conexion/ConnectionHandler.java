package com.uahcu.parkinginteligente.conexion;

import com.uahcu.parkinginteligente.MainActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class ConnectionHandler {
    public static String response;
    private static CountDownLatch latch = new CountDownLatch(1);

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

    private static void doRegisterRequest(String name, String username, String phone, String email, String pass) {
        try {
            String urlS= "http://192.168.1.131:8080/Parking Server/Registro?nombreU=" + username + "&email=" + email
                    + "&pass=" + pass + "&tlf=" + phone + "&nombre=" + name;
            URL url = new URL(urlS);
            HttpURLConnection connection = createConnection(url, "POST");
            connection.connect();
            InputStream in = connection.getInputStream();
            response = convertStreamToString(in);
            latch.countDown();
            connection.disconnect();
        }
        catch (IOException e) {
            //e.printStackTrace();
            latch.countDown();
        }
    }

    private static void doLoginRequest(String username, String pass) {
        try {
            String urlS = "http://192.168.1.131:8080/Parking Server/InicioSesion?nombreU=" + username + "&pass=" + pass;
            URL url = new URL(urlS);
            HttpURLConnection connection = createConnection(url, "POST");
            connection.connect();
            InputStream in = connection.getInputStream();
            response = convertStreamToString(in);
            latch.countDown();
            connection.disconnect();
        } catch (IOException e) {
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

    public static void waitForResponse() throws InterruptedException {
        latch.await();
        latch = new CountDownLatch(1);
    }
}
