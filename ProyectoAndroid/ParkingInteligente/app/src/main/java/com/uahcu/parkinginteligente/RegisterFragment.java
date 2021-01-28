package com.uahcu.parkinginteligente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final EditText nombreUT = (EditText) view.findViewById(R.id.editTextTextPersonName);
        final EditText passT = (EditText) view.findViewById(R.id.editTextTextPassword2);
        final EditText emailT = (EditText) view.findViewById(R.id.editTextTextEmailAddress2);
        final EditText nombreT = (EditText) view.findViewById(R.id.rellenableNombre);
        final EditText telefonoT = (EditText) view.findViewById(R.id.rellenableDeTelefono);
        Button btnRegistro = (Button) view.findViewById(R.id.button_crear_cuenta_nueva);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Codigo para crear una cuenta habiendo metido los datos en las cajitas
                // Una vez creada, debería ir al menú principal logeado en su cuenta
                String nombreU = nombreUT.getText().toString();
                String email=emailT.getText().toString();
                String pass= passT.getText().toString();
                String nombre = nombreT.getText().toString();
                int telefono = Integer.parseInt(telefonoT.getText().toString());

                android.os.StrictMode.ThreadPolicy policy = new android.os.StrictMode.ThreadPolicy.Builder().permitAll().build();
                android.os.StrictMode.setThreadPolicy(policy);
                registro(nombreU,telefono,nombre,email,pass);


                //NavHostFragment.findNavController(RegisterFragment.this)
                 //       .navigate(R.id.action_RegisterFragment_to_LoginFragment);
            }

        });



    }
    public void registro(String nameUser,int phone,String name ,String email,String pass)
    {
        String response = "";
        String urlS= "http://192.168.1.108:8080/Parking2/Registro?nombreU="+nameUser+"&email="+email+"&pass="+pass+"&tlf="+phone+"&nombre="+name;
        try {
            URL url = new URL(urlS);
            HttpURLConnection urlConnection = null;
            urlConnection = (HttpURLConnection) url.openConnection();
            //Get the information from the url
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            response = convertStreamToString(in);
            System.out.println(response);
            //if response= login correcto ->seguir con la app normal
            //if reponse = Falta usuario->
        }
        catch (IOException e) {
            e.printStackTrace();
            //return null;
        }
        //return response;



    }
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
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
}