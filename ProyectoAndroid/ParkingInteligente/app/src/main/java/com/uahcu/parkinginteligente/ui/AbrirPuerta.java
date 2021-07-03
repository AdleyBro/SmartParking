package com.uahcu.parkinginteligente.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.uahcu.parkinginteligente.R;
import com.uahcu.parkinginteligente.conexion.ConnectionHandler;

public class AbrirPuerta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrir_puerta);

        findViewById(R.id.botonPuerta).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ConnectionHandler.openParkingDoorRequest();
                try {
                    ConnectionHandler.waitForResponse();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Alakazam!");
            }
        });
    }
}