package com.uahcu.parkinginteligente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        v.findViewById(R.id.buttonConfirmarReserva).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionHandler.bookParkingSlotRequest();
                try {
                    ConnectionHandler.waitForResponse();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // No deberia hacer esto pero no nos da tiempo para más.
                NavHostFragment.findNavController(BookParkingSlotFragment.this)
                        .navigate(R.id.action_bookParkingSlotFragment_to_exitoEnReserva);
            }
        });

        return v;
    }
}