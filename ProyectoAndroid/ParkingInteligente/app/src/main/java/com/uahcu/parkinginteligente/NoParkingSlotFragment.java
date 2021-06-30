package com.uahcu.parkinginteligente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoParkingSlotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoParkingSlotFragment extends Fragment {

    public NoParkingSlotFragment() {
        // Required empty public constructor
    }

    public static NoParkingSlotFragment newInstance() {
        return new NoParkingSlotFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_no_parking_slot, container, false);
    }
}