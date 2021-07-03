package com.uahcu.parkinginteligente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uahcu.parkinginteligente.conexion.ConnectionHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookParkingSlotFragment extends Fragment {

    public BookParkingSlotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_parking_slot, container, false);

        final TextView fIni = v.findViewById(R.id.editTextDate);
        final TextView fFin = v.findViewById(R.id.editTextDate2);
        final TextView hIni = v.findViewById(R.id.editTextTime);
        final TextView hFin = v.findViewById(R.id.editTextTime2);

        v.findViewById(R.id.buttonConfirmarReserva).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BookingInfo.setFechaHoraIni(fIni.getText().toString(), hIni.getText().toString());
                BookingInfo.setFechaHoraFin(fFin.getText().toString(), hFin.getText().toString());

                ConnectionHandler.bookParkingSlotRequest();
                try {
                    ConnectionHandler.waitForResponse();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                NavHostFragment.findNavController(BookParkingSlotFragment.this)
                        .navigate(R.id.action_bookParkingSlotFragment_to_resultadoReserva);
            }
        });

        return v;
    }
}