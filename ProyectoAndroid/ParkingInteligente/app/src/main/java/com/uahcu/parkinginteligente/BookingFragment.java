package com.uahcu.parkinginteligente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
import com.uahcu.parkinginteligente.conexion.ConnectionHandler;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private MapView mapView;
    private GoogleMap map;
    private HashMap<Marker, Parking> parkingMarkers = new HashMap<>();

    public BookingFragment() {
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
        View v = inflater.inflate(R.layout.fragment_booking, container, false);

        mapView = v.findViewById(R.id.mapBooking);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        ConnectionHandler.parkingMapRequest();
        try {
            ConnectionHandler.waitForResponse();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ArrayList<Parking> parkingList = ConnectionHandler.getParkingList();
        LatLng currentLoc = new LatLng(40.51359575932808, -3.348685715796535);
        map.addMarker(new MarkerOptions().position(currentLoc).title("Tu posición"));

        if (parkingList != null) {
            for (Parking parking : parkingList) {
                LatLng loc = new LatLng(parking.getLatitud(), parking.getLongitud());
                parkingMarkers.put(map.addMarker(new MarkerOptions().position(loc).title(String.valueOf(parking.getId_parking()))),
                        parking);
            }
        }
        map.getUiSettings().setZoomControlsEnabled(true);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 15));

        map.setOnMarkerClickListener(this);

        mapView.onResume();
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
    public boolean onMarkerClick(Marker marker) {
        /*
        String fechaIni = (String) ((TextView) mapView.findViewById(R.id.editTextDate)).getText();
        String fechaFin = (String) ((TextView) mapView.findViewById(R.id.editTextDate2)).getText();
        String horaIni = (String) ((TextView) mapView.findViewById(R.id.editTextTime)).getText();
        String horaFin = (String) ((TextView) mapView.findViewById(R.id.editTextTime2)).getText();
        */

        Parking parking = parkingMarkers.get(marker);
        BookingInfo.parking = parking;
        //BookingInfo.fechaHoraIni = fechaIni + horaIni;
        //BookingInfo.fechaHoraFin = fechaFin + horaFin;

        if (parking != null) {
            NavHostFragment.findNavController(BookingFragment.this)
                    .navigate(R.id.action_bookingFragment_to_bookParkingSlotFragment);
            return true;
        } else
            return false;
    }
}