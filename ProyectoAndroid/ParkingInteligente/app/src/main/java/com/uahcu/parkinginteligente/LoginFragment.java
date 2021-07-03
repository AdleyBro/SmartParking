package com.uahcu.parkinginteligente;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.uahcu.parkinginteligente.conexion.ConnectionHandler;

import java.util.ArrayList;

public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText ETnombreUsuario = view.findViewById(R.id.textUsuarioLogin);
        final EditText ETpass = view.findViewById(R.id.textContraLogin);

        final TextView textAviso = view.findViewById(R.id.textAvisoLogin);

        view.findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String nombreUsuario = ETnombreUsuario.getText().toString();
                    String pass = ETpass.getText().toString();

                    ConnectionHandler.loginRequest(nombreUsuario, pass);
                    ConnectionHandler.waitForResponse();

                    String respuesta = ConnectionHandler.getSimpleResponse();
                    System.out.println("Respuesta: " + respuesta);
                    if (respuesta.contains("correcto")) {
                    //if (true) {
                        ConnectionHandler.userDataRequest(nombreUsuario);
                        ConnectionHandler.waitForResponse();
                        ArrayList<String> userData = ConnectionHandler.getFullResponse();
                        UserInfo.setDataFromArray(userData);

                        // Con el intent cambiamos a otra actividad que contiene el menú desplegable
                        Intent intent = new Intent(view.getContext(), MainMenuActivity.class);
                        startActivityForResult(intent, 0);

                        getActivity().finish(); // Cierra la actividad de inicio de sesión para no volver
                    } else {
                        textAviso.setText(respuesta);
                    }
                } catch (Exception e) {
                    textAviso.setText("Faltan datos o falló la conexión.");
                }
            }
        });

        view.findViewById(R.id.button_crear_cuenta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_LoginFragment_to_RegisterFragment);
            }
        });
    }
}