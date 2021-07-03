package com.uahcu.parkinginteligente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uahcu.parkinginteligente.conexion.ConnectionHandler;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultadoReserva#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultadoReserva extends Fragment {

    public ResultadoReserva() {
        // Required empty public constructor
    }

    public static ResultadoReserva newInstance() {
        return new ResultadoReserva();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_resultado_reserva, container, false);

        TextView txtv = v.findViewById(R.id.resultadoReservaTxt);
        txtv.setText(ConnectionHandler.getSimpleResponse());

        // Inflate the layout for this fragment
        return v;
    }
}