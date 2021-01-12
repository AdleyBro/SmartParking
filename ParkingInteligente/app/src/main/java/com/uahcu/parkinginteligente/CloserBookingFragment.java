package com.uahcu.parkinginteligente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CloserBookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CloserBookingFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;

    public CloserBookingFragment() {
        // Required empty public constructor
    }

    public static CloserBookingFragment newInstance() {
        Log.d("BOOKING", "\n\nCLOSER BOOKING FRAGMENT NEW INSTANCE\n\n");
        return new CloserBookingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("BOOKING", "\n\nCLOSER BOOKING FRAGMENT ON CREATE\n\n");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_closer_booking, container, false);

        Log.d("BOOKING", "\n\nCLOSER BOOKING FRAGMENT\n\n");
        mapView = v.findViewById(R.id.mapCloserBooking);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MAPSSSS", "ON MAP READYYYYYYYYYYYY");
        map = googleMap;

        LatLng politecnica = new LatLng(40.51359575932808, -3.348685715796535);
        map.addMarker(new MarkerOptions().position(politecnica).title("Politécnica UAH"));
        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLng(politecnica));
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}