package com.uahcu.parkinginteligente;

import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainMenuFragment extends Fragment {

    public MainMenuFragment() {
        // Required empty public constructor
    }

    public static MainMenuFragment newInstance() {
        return new MainMenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.buttonMapaParkings).setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View v, MotionEvent event) {
                  ImageButton button = (ImageButton) v;

                  // TODO: Código para abrir fragment con google maps y parkings cercanos

                  if (event.getAction() == MotionEvent.ACTION_DOWN)
                      button.setColorFilter(getResources().getColor(R.color.colorPrimary, null), PorterDuff.Mode.SRC_ATOP);
                  else if (event.getAction() == MotionEvent.ACTION_UP)
                      button.setColorFilter(getResources().getColor(R.color.colorSecondary, null), PorterDuff.Mode.SRC_ATOP);
                  return true;
              }
          }
        );

        view.findViewById(R.id.buttonEstadoReserva).setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View v, MotionEvent event) {
                  ImageButton button = (ImageButton) v;

                  // TODO: Código para abrir fragment con de reserva de plaza

                  if (event.getAction() == MotionEvent.ACTION_DOWN)
                      button.setColorFilter(getResources().getColor(R.color.colorPrimary, null), PorterDuff.Mode.SRC_ATOP);
                  else if (event.getAction() == MotionEvent.ACTION_UP)
                      button.setColorFilter(getResources().getColor(R.color.colorSecondary, null), PorterDuff.Mode.SRC_ATOP);
                  return true;
              }
          }
        );
    }
}