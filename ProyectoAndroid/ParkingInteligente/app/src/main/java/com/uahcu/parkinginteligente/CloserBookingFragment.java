package com.uahcu.parkinginteligente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uahcu.parkinginteligente.ui.AbrirPuerta;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CloserBookingFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private MapView mapView;
    private GoogleMap map;
    private ArrayList<String> respuesta;

    public CloserBookingFragment() {}

    public CloserBookingFragment(ArrayList<String> respuesta) {
        this.respuesta = respuesta;
        BookingInfo.setIdParking(respuesta.get(4));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_closer_booking, container, false);

        TextView fechaHora = v.findViewById(R.id.textBookingTime);
        fechaHora.setText(String.format("Fecha y hora: %s", respuesta.get(1)));

        TextView localizacion = v.findViewById(R.id.text_localizacion);
        localizacion.setText("Localización: Nº Plaza " + respuesta.get(0));
        mapView = v.findViewById(R.id.mapCloserBooking);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng parking = new LatLng(Double.parseDouble(respuesta.get(5)), Double.parseDouble(respuesta.get(6)));
        map.addMarker(new MarkerOptions().position(parking).title("Plaza reservada"));
        map.getUiSettings().setZoomControlsEnabled(true);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(parking, 15));
        mapView.onResume();

        map.setOnMarkerClickListener(this);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        startActivity(new Intent(getActivity(), AbrirPuerta.class));
        return true;
    }
}