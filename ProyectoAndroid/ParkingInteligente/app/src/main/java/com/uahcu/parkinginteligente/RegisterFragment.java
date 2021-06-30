package com.uahcu.parkinginteligente;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.uahcu.parkinginteligente.conexion.ConnectionHandler;

import java.util.ArrayList;

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

        final EditText ETnombre = view.findViewById(R.id.textNombre);
        final EditText ETnombreUsuario = view.findViewById(R.id.textNombreUsuario);
        final EditText ETtelefono = view.findViewById(R.id.textTelefono);
        final EditText ETemail = view.findViewById(R.id.textEmail);
        final EditText ETcontra1 = view.findViewById(R.id.textContrasena);
        final EditText ETcontra2 = view.findViewById(R.id.textContrasena2);

        final TextView textAviso = view.findViewById(R.id.textAviso);

        Button btnRegistro = view.findViewById(R.id.buttonCrearCuenta);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String nombre = ETnombre.getText().toString();
                    String nombreUsuario = ETnombreUsuario.getText().toString();
                    String telefono = ETtelefono.getText().toString();
                    String email = ETemail.getText().toString();
                    String contra1 = ETcontra1.getText().toString();
                    String contra2 = ETcontra2.getText().toString();

                    if (contra1.equals(contra2)) {
                        ConnectionHandler.registerRequest(nombre, nombreUsuario, telefono, email, contra1);
                        ConnectionHandler.waitForResponse();
                        String respuesta = ConnectionHandler.getSimpleResponse();
                        System.out.println("Respuesta: " + respuesta);
                        if (respuesta.contains("bien")) {
                            //ConnectionHandler.userDataRequest(nombreUsuario);
                            //ConnectionHandler.waitForResponse();
                            //ArrayList<String> userData = ConnectionHandler.getFullResponse();
                            //UserInfo.setDataFromArray(userData);

                            // Con el intent cambiamos a otra actividad que contiene el menú desplegable
                            Intent intent = new Intent(view.getContext(), MainMenuActivity.class);
                            startActivityForResult(intent, 0);

                            getActivity().finish(); // Cierra la actividad de inicio de sesión para no volver

                            //NavHostFragment.findNavController(RegisterFragment.this).navigate(R.id.action_RegisterFragment_to_LoginFragment);
                        }
                        else{textAviso.setText("Datos incorrectos, por favor, rellena el formulario de nuevo");}

                    } else {
                        textAviso.setText("¡Las contraseñas no coinciden!");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    textAviso.setText("Faltan datos o falló la conexión.");
                }

            }

        });
    }


}