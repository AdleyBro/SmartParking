package com.uahcu.parkinginteligente;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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

        view.findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Escribir código para iniciar sesión

                // Con el intent cambiamos a otra actividad que contiene el menú desplegable
                Intent intent = new Intent (view.getContext(), MainMenuActivity.class);
                startActivityForResult(intent, 0);

                getActivity().finish(); // Cierra la actividad de inicio de sesión para no volver
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